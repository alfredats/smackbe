package visa.vttp.csf.smackBE.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthProps {
  @Value("${jwt.auth.secret}")
  public String AUTH_SECRET;
  @Value("${jwt.auth.validity}")
  public long AUTH_DURATION_VALID;

}
