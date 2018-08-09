package com.wgu.setcard.ump.service.spec;

import java.util.Map;

public interface ITokenService {

  /* METHODS DECLARATIONS *****************************************/

  String permanent(Map<String, String> attributes);

  String expiring(Map<String, String> attributes);

  Map<String, String> untrusted(String token);

  Map<String, String> verify(String token);
}
