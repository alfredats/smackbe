package visa.vttp.csf.smackBE.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import visa.vttp.csf.smackBE.model.mongo.ChatMessage;
import visa.vttp.csf.smackBE.service.MsgService;

@RestController
@RequestMapping(path = "/messages")
public class MsgController {

    @Autowired
    private MsgService mSvc;

    @GetMapping(path = { "/{chatID}/{page}", "/{chatID}" })
    public ResponseEntity<String> getMessages(@PathVariable String chatID,
            @PathVariable(name = "page") Optional<Integer> optpage) {
        JsonObjectBuilder bld = Json.createObjectBuilder();
        JsonArrayBuilder arr = Json.createArrayBuilder();
        List<ChatMessage> lcm = mSvc.getMessagesByChatIdOrdByDatetime(chatID, optpage.orElse(0));
        for (ChatMessage cm : lcm) {
            arr.add(cm.toJson());
        }
        bld.add("code", HttpStatus.OK.value()).add("data", arr);
        return ResponseEntity.ok(bld.build().toString());
    }

}
