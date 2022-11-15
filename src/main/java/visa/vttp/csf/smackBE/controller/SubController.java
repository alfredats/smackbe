package visa.vttp.csf.smackBE.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import visa.vttp.csf.smackBE.model.sql.ChatSub;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import visa.vttp.csf.smackBE.service.SubService;

@RestController
@RequestMapping(path = "/subscriptions")
public class SubController {

    private final SubService sSvc;

    @Autowired
    SubController(SubService sSvc) {
        this.sSvc = sSvc;
    }

    @GetMapping(path = "/subscriptions")
    ResponseEntity<String> getSubscriptions(HttpSession sess) {
        String email = (String) sess.getAttribute("email");
        List<ChatSub> ls = this.sSvc.getSubscriptionsByUser(email);
        JsonObjectBuilder bld = Json.createObjectBuilder();
        JsonArrayBuilder abld = Json.createArrayBuilder();
        ls.stream().forEach((ChatSub c) -> {
            abld.add(c.toJson());
        });
        bld.add("code", HttpStatus.OK.value())
                .add("data", abld);
        return ResponseEntity.ok(bld.build().toString());
    }

}
