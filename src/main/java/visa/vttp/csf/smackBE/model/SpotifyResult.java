package visa.vttp.csf.smackBE.model;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class SpotifyResult {
  private String trackname;
  private String album;
  private List<String> artists;
  private String play_url;
  private String img_url;

  public static JsonObject reduceEntry(JsonObject obj) {
    JsonObjectBuilder rs = Json.createObjectBuilder();
    rs.add("trackname", obj.getString("name"));
    rs.add("album", obj.getJsonObject("album").getString("name"));
    rs.add("img_url", obj.getJsonObject("album").getJsonArray("images").get(0).asJsonObject().getString("url"));
    rs.add("play_url", obj.getJsonObject("external_urls").getString("spotify"));
    JsonArrayBuilder arr = Json.createArrayBuilder();
    obj.getJsonObject("album").getJsonArray("artists").forEach((v) -> {
      arr.add(v.asJsonObject().getString("name"));
    });
    rs.add("artists", arr);
    return rs.build();
  }

  public String getImg_url() {
    return img_url;
  }

  public void setImg_url(String img_url) {
    this.img_url = img_url;
  }

  public void addArtist(String artist) {
    this.artists.add(artist);
  }

  public List<String> getArtists() {
    return artists;
  }

  public void setArtists(List<String> artists) {
    this.artists = artists;
  }

  public String getTrackname() {
    return trackname;
  }

  public void setTrackname(String trackname) {
    this.trackname = trackname;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getPlay_url() {
    return play_url;
  }

  public void setPlay_url(String play_url) {
    this.play_url = play_url;
  }

}
