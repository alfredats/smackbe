package visa.vttp.csf.smackBE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import visa.vttp.csf.smackBE.model.sql.User;
import visa.vttp.csf.smackBE.service.UserService;

@RestController
@RequestMapping(path="/users")
public class UserController {

  private final UserService uSvc;
  
  @Autowired
  UserController(UserService uSvc) {
    this.uSvc = uSvc;
  }

  @GetMapping(path="/name/{userEmail}")
  public ResponseEntity<String> getUserDisplayName(@PathVariable String userEmail) {
    User u = this.uSvc.getUserByEmail(userEmail);
    JsonObjectBuilder bld = Json.createObjectBuilder();
    if (u == null) {
      bld.add("code", HttpStatus.BAD_REQUEST.value()).add("message", "No such user");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bld.build().toString());
    }
    bld.add("code", HttpStatus.OK.value()).add("data", Json.createObjectBuilder().add("displayname",u.getDisplayName()));
    return ResponseEntity.ok(bld.build().toString());
  }
  
}
