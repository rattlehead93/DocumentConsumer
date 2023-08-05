package com.document.document.listener;

import com.document.document.message.DocumentMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RabbitMQListener implements MessageListener {
    private final ObjectMapper jsonMapper;

    @Autowired
    public RabbitMQListener(final ObjectMapper objectMapper) {
        this.jsonMapper = objectMapper;
    }

    public DocumentMessage parseMessage(final String messageBody) throws JsonProcessingException {
        System.out.println("Trying to make object from received string: " + messageBody);
        return jsonMapper.readValue(messageBody, DocumentMessage.class);
    }

    public void onMessage(final Message message) {
        System.out.println(Arrays.toString(message.getBody()));
        processReceivedMessage(new String(message.getBody()));
    }

    public DocumentMessage processReceivedMessage(final String messageBody) {
        try {
            final DocumentMessage documentMessage = parseMessage(messageBody);
            System.out.println("Received document message : " + documentMessage);
            return documentMessage;
        } catch (final JsonProcessingException ex) {
            System.out.println("Invalid json format received from queue.");
            System.err.println("Received invalid message: " + messageBody);
            throw new RuntimeException("Invalid message received from queue.");
        }
    }
}

