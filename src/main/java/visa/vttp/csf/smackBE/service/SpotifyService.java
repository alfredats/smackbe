package visa.vttp.csf.smackBE.service;

import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import visa.vttp.csf.smackBE.model.SpotifyResult;

@Service
public class SpotifyService {

  private RestTemplate rt;
  private static final String HOST = "https://api.spotify.com/v1/search";
  private String token;
  private long tokenExpiry;

  @Value("${spotify.client.id}")
  private String clientID;
  @Value("${spotify.client.secret}")
  private String clientSecret;

  @Autowired
  SpotifyService() {
    this.rt = new RestTemplate();
  }

  @PostConstruct
  private void onInit() {
    this.getAuth();
  }

  private void getAuth() {
    final String authEndpoint = "https://accounts.spotify.com/api/token";
    String foo = this.clientID + ":" + this.clientSecret;
    String authToken = "Basic " + Base64.getEncoder().encodeToString(foo.getBytes()).toString();

    RequestEntity<String> req = RequestEntity.post(authEndpoint)
        .header("Authorization", authToken)
        .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(URLEncoder.encode("grant_type", StandardCharsets.UTF_8) + "="
            + URLEncoder.encode("client_credentials", StandardCharsets.UTF_8));

    ResponseEntity<String> resp = rt.exchange(req, String.class);
    if (resp.getStatusCode() != HttpStatus.OK) {
      throw new RuntimeException("Failed to start SpotifyService: Auth error;");
    }

    JsonObject respBody = Json.createReader(new StringReader(resp.getBody())).readObject();
    System.out.println(respBody.toString());
    if (!respBody.getString("token_type").equalsIgnoreCase("bearer")) {
      throw new RuntimeException("Failed to start SpotifyService: Token error;");
    }

    this.token = "Bearer " + respBody.getString("access_token");
    this.tokenExpiry = System.currentTimeMillis() + (1000 * respBody.getInt("expires_in"));
  }

  public JsonArray search(String query) {
    System.out.println(">>> token expired: " + (this.tokenExpiry < System.currentTimeMillis()));
    if (this.tokenExpiry < System.currentTimeMillis()) {
      this.getAuth();
    }

    String uri = UriComponentsBuilder.fromUriString(HOST)
        .queryParam("q", query)
        .queryParam("type", "track")
        .queryParam("limit", 5)
        .queryParam("market","SG")
        .toUriString();
    System.out.println(uri);
    RequestEntity<Void> req = RequestEntity.get(uri).header("Authorization", this.token)
        .accept(MediaType.APPLICATION_JSON).build();

    ResponseEntity<String> resp = rt.exchange(req, String.class);
    if (resp.getStatusCode() != HttpStatus.OK) {
      throw new RuntimeException("Bad response from Spotify");
    }
    JsonObject respBody = Json.createReader(new StringReader(resp.getBody())).readObject();
    JsonArrayBuilder arr = Json.createArrayBuilder();
    respBody.getJsonObject("tracks")
        .getJsonArray("items")
        .stream()
        .forEach((v) -> {
          arr.add(SpotifyResult.reduceEntry(v.asJsonObject()));
        });
    return arr.build();
  }
}
