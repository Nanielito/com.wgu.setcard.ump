package com.wgu.setcard.ump.service.impl;

import static java.util.Objects.requireNonNull;

import static io.jsonwebtoken.impl.TextCodec.BASE64;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

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
import com.wgu.setcard.ump.util.DateHelper;

/**
 * Defines the class implementation for {@link ITokenService} interface.
 *
 * @author danielramirez (https://github.com/nanielito)
 */
@Service
public final class TokenServiceImpl implements Clock, ITokenService {

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

  private DateHelper dateHelper;
  private String     issuer;
  private String     secretKey;
  private int        expirationSeconds;
  private int        clockSkewSeconds;

  /* CLASS CONSTRUCTORS *******************************************/

  /**
   * Initializes an instance of the class.
   *
   * @param dateHelper
   * @param issuer
   * @param expirationSeconds
   * @param clockSkewSeconds
   * @param secretKey
   */
  public TokenServiceImpl(
                                 final DateHelper dateHelper,
      @Value(JWT_ISSUER)         final String     issuer,
      @Value(JWT_EXPIRATION_SEC) final int        expirationSeconds,
      @Value(JWT_SKEW_SEC)       final int        clockSkewSeconds,
      @Value(JWT_SECRET)         final String     secretKey) {
    super();

    this.dateHelper        = requireNonNull(dateHelper);
    this.issuer            = requireNonNull(issuer);
    this.expirationSeconds = requireNonNull(expirationSeconds);
    this.clockSkewSeconds  = requireNonNull(clockSkewSeconds);
    this.secretKey         = BASE64.encode(requireNonNull(secretKey));
  }

  /* METHODS IMPLEMENTATIONS **************************************/

  /**
   * Generates a <code>JWT</code> session token.
   *
   * @param attributes A <code>Map</code> which contains the information related to a user.
   * @param expiresInSeconds A <code>Integer</code> which represents the session token expiration time.
   *
   * @return The <code>JWT</code> session token.
   */
  private String generateToken(final Map<String, String> attributes, final int expiresInSeconds) {
    final DateTime now    = dateHelper.now();
    final Claims   claims = Jwts.claims().setIssuer(issuer).setIssuedAt(now.toDate());

    if (expiresInSeconds > 0) {
      final DateTime expiresAt = now.plusSeconds(expiresInSeconds);

      claims.setExpiration(expiresAt.toDate());
    }

    claims.putAll(attributes);

    return Jwts.builder().setClaims(claims).signWith(HS256, secretKey).compressWith(COMPRESSION_CODEC).compact();
  }

  /**
   * Parses the <code>JWT</code> session token which had been created.
   *
   * @param toClaims The encode information related to <code>JWT</code> session token which had been created.
   *
   * @return The <code>Map</code> which contains the <code>JWT</code> session token created.
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public String permanent(final Map<String, String> attributes) {
    return generateToken(attributes, 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String expiring(final Map<String, String> attributes) {
    return generateToken(attributes, expirationSeconds);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public Date now() {
    final DateTime now = dateHelper.now();

    return now.toDate();
  }
}
