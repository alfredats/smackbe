package visa.vttp.csf.smackBE.repository.mongo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import visa.vttp.csf.smackBE.model.mongo.ChatMessage;

@Repository
public interface MessageRepository extends MongoRepository<ChatMessage, String> {

    @Query("{chatId: ?0}")
    List<ChatMessage> findByChatID(String chatID, Pageable pageable);

}
