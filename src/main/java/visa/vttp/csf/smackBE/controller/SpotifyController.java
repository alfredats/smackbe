package visa.vttp.csf.smackBE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import visa.vttp.csf.smackBE.service.SpotifyService;

@RestController
@RequestMapping(path = "/spotify")
public class SpotifyController {

  private SpotifyService spSvc;

  @Autowired
  SpotifyController(SpotifyService spSvc) {
    this.spSvc = spSvc;
  }

  @GetMapping()
  ResponseEntity<String> searchResource(@RequestParam String q) {
    System.out.println(">>> q: " + q);
    JsonArray results = spSvc.search(q);
    JsonObject obj = Json.createObjectBuilder().add("code", HttpStatus.OK.value()).add("data", results).build();
    return ResponseEntity.ok(obj.toString());
  }

}
