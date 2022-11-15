package visa.vttp.csf.smackBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import visa.vttp.csf.smackBE.model.exceptions.UserAlreadyExistsException;
import visa.vttp.csf.smackBE.model.sql.User;
import visa.vttp.csf.smackBE.repository.sql.UserRepository;

@Service
public class UserService {

  private UserRepository uRepo;

  @Autowired
  UserService(UserRepository uRepo) {
    this.uRepo = uRepo;
  }

  public User getUserByEmail(String email) {
    return this.uRepo.getUserByEmail(email);
  }

  public void insertUser(JsonObject userDetails) {
    try {
      this.getUserByEmail(userDetails.getString("email"));
      throw new UserAlreadyExistsException(
          String.format("User with email %s already exists", userDetails.getString("email")));
    } catch (DataAccessException e) {
      this.uRepo.createUser(userDetails);
    }
}

}
