package visa.vttp.csf.smackBE.model.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

public class UserAlreadyExistsException extends AuthenticationServiceException {

  public UserAlreadyExistsException(final String msg) {
    super(msg);
  }

}
