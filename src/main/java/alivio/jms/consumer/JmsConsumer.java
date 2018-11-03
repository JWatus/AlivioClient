package eu.sii.pl.alivio.jms.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sii.pl.alivio.model.Debtor;
import eu.sii.pl.alivio.model.PaymentPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;

@Component
public class JmsConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConsumer.class);

    private ObjectMapper objectMapper;

    public JmsConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

//    @JmsListener(destination = "${activemq.queue.alivio}")
    public void receiver(TextMessage textMessage) throws JMSException, IOException {
        String endpoint = textMessage.getStringProperty("endpoint");
        String json = textMessage.getText();

        if ("login".equals(endpoint)) {
            showJson(textMessage, json);
        } else if ("balance".equals(endpoint)) {
            showJson(textMessage, json);
            convertJsonToDebtor(json);
        } else if ("paymentplan".equals(endpoint)) {
            showJson(textMessage, json);
            convertJsonToPaymentPlan(json);
        } else if ("paymentsupdate".equals(endpoint)) {
            showJson(textMessage, json);
        }
    }

    private void showJson(TextMessage textMessage, String json) throws JMSException {
        LOGGER.info("received message from endpoint='{}'", textMessage.getStringProperty("endpoint"));
        LOGGER.info("received message text ='{}'", textMessage);
        LOGGER.info("received message json='{}'", json);
    }

    private void convertJsonToPaymentPlan(String json) throws IOException {
        PaymentPlan paymentPlan = objectMapper.readValue(json, PaymentPlan.class);
        LOGGER.info("received message='{}'", paymentPlan.getMessage());
        LOGGER.info("received message='{}'", paymentPlan.getPlannedPaymentList().size());
    }

    private void convertJsonToDebtor(String json) throws IOException {
        Debtor debtor = objectMapper.readValue(json, Debtor.class);
        LOGGER.info("received message='{}'", debtor.getFirstName());
        LOGGER.info("received message='{}'", debtor.getLastName());
        LOGGER.info("received message='{}'", debtor.getSsn());
        LOGGER.info("received message='{}'", debtor.getDebts().size());
    }
}