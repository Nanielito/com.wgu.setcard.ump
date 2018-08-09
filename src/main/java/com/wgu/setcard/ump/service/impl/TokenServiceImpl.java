package com.wgu.setcard.ump.service.impl;

import static java.util.Objects.requireNonNull;

import static io.jsonwebtoken.impl.TextCodec.BASE64;
import static io.jsonwebtoken.SignatureAlgorithm.RS256;

import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;

import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wgu.setcard.ump.service.spec.ITokenService;
import com.wgu.setcard.ump.util.spec.IDateHelper;

@Service
final class TokenServiceImpl implements Clock, ITokenService {

  /* DEFINITIONS **************************************************/

  private static final int                  EXPIRATION_TIME    = 86400;
  private static final int                  CLOCK_SKEW_TIME    = 300;
  private static final String               ISSUER             = "setcard";
  private static final String               SECRET             = "0PjNRut7cqmQTZ8uv5Pc";
  private static final String               JWT_ISSUER         = "${jwt.issuer:" + ISSUER + "}";
  private static final String               JWT_EXPIRATION_SEC = "${jwt.expiration-sec:" + EXPIRATION_TIME + "}";
  private static final String               JWT_SKEW_SEC       = "${jwt.clock-skew-sec:" + CLOCK_SKEW_TIME + "}";
  private static final String               JWT_SECRET         = "${jwt.secret:" + SECRET + "}";
  private static final String               DOT                = ".";
  private static final GzipCompressionCodec COMPRESSION_CODEC  = new GzipCompressionCodec();

  /* MEMBERS DECLARATIONS *****************************************/

  private IDateHelper dateHelper;
  private String      issuer;
  private String      secretKey;
  private int         expirationSeconds;
  private int         clockSkewSeconds;

  /* CLASS CONSTRUCTORS *******************************************/

  public TokenServiceImpl(
                                 final IDateHelper dateHelper,
      @Value(JWT_ISSUER)         final String      issuer,
      @Value(JWT_EXPIRATION_SEC) final int         expirationSeconds,
      @Value(JWT_SKEW_SEC)       final int         clockSkewSeconds,
      @Value(JWT_SECRET)         final String      secretKey) {
    super();

    this.dateHelper        = requireNonNull(dateHelper);
    this.issuer            = requireNonNull(issuer);
    this.expirationSeconds = requireNonNull(expirationSeconds);
    this.clockSkewSeconds  = requireNonNull(clockSkewSeconds);
    this.secretKey         = BASE64.encode(requireNonNull(secretKey));
  }

  /* METHODS IMPLEMENTATIONS **************************************/

  private String generateToken(final Map<String, String> attributes, final int expiresInSeconds) {
    final DateTime now    = dateHelper.now();
    final Claims   claims = Jwts.claims().setIssuer(issuer).setIssuedAt(now.toDate());

    if (expiresInSeconds > 0) {
      final DateTime expiresAt = now.plusSeconds(expiresInSeconds);

      claims.setExpiration(expiresAt.toDate());
    }

    claims.putAll(attributes);

    return Jwts.builder().setClaims(claims).signWith(RS256, secretKey).compressWith(COMPRESSION_CODEC).compact();
  }

  private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
    try {
      final Claims claims = toClaims.get();
      final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

      for (final Map.Entry<String, Object> entry: claims.entrySet()) {
        builder.put(entry.getKey(), String.valueOf(entry.getValue()));
      }

      return builder.build();
    }
    catch (final IllegalArgumentException | JwtException e) {
      return ImmutableMap.of();
    }
  }

  @Override
  public String permanent(final Map<String, String> attributes) {
    return generateToken(attributes, 0);
  }

  @Override
  public String expiring(final Map<String, String> attributes) {
    return generateToken(attributes, expirationSeconds);
  }

  @Override
  public Map<String, String> verify(final String token) {
    final JwtParser parser = Jwts
      .parser()
      .requireIssuer(issuer)
      .setClock(this)
      .setAllowedClockSkewSeconds(clockSkewSeconds)
      .setSigningKey(secretKey);

    return parseClaims(() -> parser.parseClaimsJws(token).getBody());
  }

  @Override
  public Map<String, String> untrusted(final String token) {
    final JwtParser parser = Jwts
      .parser()
      .requireIssuer(issuer)
      .setClock(this)
      .setAllowedClockSkewSeconds(clockSkewSeconds);
    final String withoutSignature = String.format("%s%s", substringBeforeLast(token, DOT), DOT);

    return parseClaims(() -> parser.parseClaimsJws(withoutSignature).getBody());
  }

  @Override
  public Date now() {
    final DateTime now = dateHelper.now();

    return now.toDate();
  }
}
