package alivio.jms.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class JmsProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsProducer.class);

    private ObjectMapper objectMapper;

    public JmsProducer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${activemq.queue}")
    String queue;

    public <T> void send(String endPoint, T product, String clientName) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(product);
        jmsTemplate.send(queue + endPoint, (Session session) -> {
            TextMessage textMessage = new ActiveMQTextMessage();
            textMessage.setText(json);
            textMessage.setStringProperty("client", clientName);
            LOGGER.info("sending message='{}' to destination='{}'", textMessage, queue + endPoint);
            return textMessage;
        });
    }
}