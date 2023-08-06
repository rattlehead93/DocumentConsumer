package com.document.document.listener;

import com.document.document.message.DocumentMessage;
import com.document.document.service.DocumentProcessingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class RabbitMQListener implements MessageListener {

    private final ObjectMapper jsonMapper;

    private final DocumentProcessingService documentProcessingService;

    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    public RabbitMQListener(final ObjectMapper objectMapper,
                            final DocumentProcessingService documentProcessingService) {
        this.jsonMapper = objectMapper;
        this.documentProcessingService = documentProcessingService;
    }

    public DocumentMessage parseMessage(final String messageBody) throws JsonProcessingException {
        log.info("Trying to make object from received string: " + messageBody);
        return jsonMapper.readValue(messageBody, DocumentMessage.class);
    }

    public DocumentMessage processReceivedMessage(final String messageBody) {
        try {
            final DocumentMessage documentMessage = parseMessage(messageBody);

            log.info("Received document message : " + documentMessage);

            return documentMessage;
        } catch (final JsonProcessingException ex) {
            log.error("Invalid json format received from queue.");
            log.error("Received invalid message: " + messageBody);

            throw new RuntimeException("Invalid message received from queue.");
        }
    }


    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void onMessage(final Message message) {
        log.info(Arrays.toString(message.getBody()));
        documentProcessingService.processDocument(processReceivedMessage(new String(message.getBody())));
    }
}

