package visa.vttp.csf.smackBE.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import visa.vttp.csf.smackBE.model.sql.UserAuth;
import visa.vttp.csf.smackBE.repository.sql.AuthRepository;

@Service
public class AuthService implements UserDetailsService {
  Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
  private AuthRepository aRepo;

  @Autowired
  AuthService(AuthRepository aRepo) {
    this.aRepo = aRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final UserAuth ua = aRepo.getAuthByEmail(email);
    return ua;
  }

  public void insertAuth(JsonObject obj) {
    aRepo.createAuth(obj);
  }

}
