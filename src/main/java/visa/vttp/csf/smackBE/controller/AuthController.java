package visa.vttp.csf.smackBE.controller;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import visa.vttp.csf.smackBE.config.JWTAuthFilter;
import visa.vttp.csf.smackBE.model.sql.UserAuth;
import visa.vttp.csf.smackBE.service.AuthService;
import visa.vttp.csf.smackBE.service.SubService;
import visa.vttp.csf.smackBE.service.UserService;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
  private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  private AuthService aSvc;
  private SubService sSvc;
  private UserService uSvc;

  @Autowired
  AuthController(AuthService aSvc, UserService uSvc, SubService sSvc) {
    this.aSvc = aSvc;
    this.uSvc = uSvc;
    this.sSvc = sSvc;
  }

  @PostMapping(path = "/login")
  ResponseEntity<String> authenticateUser(@AuthenticationPrincipal UserDetails ua, @RequestBody String bdy) {
    LOGGER.info(">>> USER DETAILS: " + ua);
    JsonObject resp = Json.createObjectBuilder()
        .add("code", HttpStatus.ACCEPTED.value())
        .add("message", "Authentication Successful")
        .add("data", Json.createObjectBuilder().add("token", JWTAuthFilter.generateToken(ua)).build())
        .build();
    return ResponseEntity.accepted().body(resp.toString());
  }

  @PutMapping(path = "/create")
  ResponseEntity<String> createUser(@RequestBody String body) {
    JsonObject jsonBody = Json.createReader(new StringReader(body)).readObject();
    LOGGER.info(">>> SIGNUP DETAILS: "+ jsonBody.toString());
    JsonObjectBuilder respBuilder = Json.createObjectBuilder();
    try {
      this.userCreateResource(jsonBody);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      LOGGER.debug(e.getStackTrace().toString());
      e.printStackTrace();
      respBuilder.add("code", HttpStatus.CONFLICT.value())
          .add("message", e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(respBuilder.build().toString());
    }
    respBuilder.add("code", HttpStatus.CREATED.value())
        .add("message", "User Created");
    return ResponseEntity.status(HttpStatus.CREATED).body(respBuilder.build().toString());
  }

  @GetMapping(path = "/logout")
  void getLogout(@AuthenticationPrincipal UserAuth u) {
  }

  ResponseEntity<String> updatePassword(@AuthenticationPrincipal UserAuth ua, @RequestBody String body) {
    JsonObject jsonBody = Json.createReader(new StringReader(body)).readObject();
    JsonObjectBuilder bld = Json.createObjectBuilder();
    try {
      String uemail = ua.getUsername();
      if (!uemail.equalsIgnoreCase(jsonBody.getString("email"))) {
        throw new RuntimeException("Emails in request body & session do not match;");
      }
      this.aSvc.insertAuth(jsonBody);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      LOGGER.debug(e.getStackTrace().toString());
      bld.add("code", HttpStatus.CONFLICT.value())
          .add("message", e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(bld.build().toString());
    }
    bld.add("code", HttpStatus.CREATED.value()).add("message", "New credentials created");

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(bld.build().toString());
  }

  @Transactional
  public void userCreateResource(JsonObject userDetails) {
    this.uSvc.insertUser(userDetails);
    this.aSvc.insertAuth(userDetails);
    this.sSvc.subscribeOnUserCreate(userDetails.getString("email"));
  }

}
