package visa.vttp.csf.smackBE.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import visa.vttp.csf.smackBE.model.sql.ChatSub;
import visa.vttp.csf.smackBE.repository.sql.SubscriptionRepository;

@Service
public class SubService {

    private final SubscriptionRepository sRepo;
    private final String[] chats = new String[] { "2fcb7618c37e474fbf998d0fe773806c",
            "61f5596105aa41b1a4edaaae61d4f8bd", "ac729983fcef41bfa69a3233ee1dcedd" };

    @Autowired
    SubService(SubscriptionRepository sRepo) {
        this.sRepo = sRepo;
    }

    public boolean userInChat(String userEmail, String chatID) {
        return sRepo.getSubscriptionByUserAndChat(userEmail, chatID) != null;
    }

    public void subscribe(String userEmail, String chatID) {
        sRepo.createSubscription(chatID, userEmail);
    }

    public void unsubscribe(String userEmail, String chatID) {
        sRepo.updateSubscription(chatID, userEmail);
    }

    public List<ChatSub> getSubscriptionsByUser(String userEmail) {
        return sRepo.getSubscriptionsByUser(userEmail);
    }

    public void subscribeOnUserCreate(String userEmail) {
        for (String chatID : this.chats) {
            this.subscribe(userEmail, chatID);
        }
    }
}
