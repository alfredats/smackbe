package visa.vttp.csf.smackBE.websocket;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import visa.vttp.csf.smackBE.service.MsgService;
import visa.vttp.csf.smackBE.service.SubService;

@Component
@Scope("singleton")
public class RabbitMQListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQListener.class);

    private static ConnectionFactory factory;
    private static Map<String, Pair<String, Channel>> exchanges = new HashMap<>();

    private final SubService sSvc;
    private final MsgService mSvc;
    private final String EXCHANGE_NAME = "smack";

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Autowired
    RabbitMQListener(SubService sSvc, MsgService mSvc) {
        this.sSvc = sSvc;
        this.mSvc = mSvc;
    }

    @PostConstruct
    public void init() throws Exception {
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);

        Connection conn = factory.newConnection();
        Channel ch = conn.createChannel();

        ch.exchangeDeclare(this.EXCHANGE_NAME, "fanout");

        final String QUEUE_NAME = ch.queueDeclare().getQueue();
        ch.queueBind(QUEUE_NAME, this.EXCHANGE_NAME, "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String raw = new String(delivery.getBody(), "UTF-8");
            LOGGER.info("[x] Received '" + raw + "'");
            try {
                JsonObject body = Json.createReader(new StringReader(raw)).readObject();
                this.saveToMongo(body);
            } finally {
                LOGGER.info(" [x] Processed delivery of " + raw);
            }
        };
        ch.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    private void saveToMongo(JsonObject jb) {
        String chatID = jb.getString("chatId");
        String userEmail = jb.getString("userEmail");
        if (!sSvc.userInChat(userEmail, chatID)) {
            throw new RuntimeException(
                    String.format("User %s is not authorized to publish messages to chat %s", userEmail, chatID));
        }
        mSvc.saveMessage(jb);
    }
}
