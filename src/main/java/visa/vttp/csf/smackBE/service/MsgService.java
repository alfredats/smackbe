package visa.vttp.csf.smackBE.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import visa.vttp.csf.smackBE.model.mongo.ChatMessage;
import visa.vttp.csf.smackBE.repository.mongo.MessageRepository;

@Service
public class MsgService {

    private MessageRepository mRepo;
    private static final Sort byDate = Sort.by("creationDatetime").ascending();
    static final Integer ITEMS_PER_PAGE = 50;

    @Autowired
    MsgService(MessageRepository mRepo) {
        this.mRepo = mRepo;
    }

    public void saveMessage(JsonObject obj) {
        ChatMessage cm = ChatMessage.create(obj);
        mRepo.save(cm);
    }

    public List<ChatMessage> getMessagesByChatIdOrdByDatetime(String chatID, Integer pageNumber) {
        Pageable pg = PageRequest.of(pageNumber, ITEMS_PER_PAGE, byDate);
        return mRepo.findByChatID(chatID, pg);
    }

}
