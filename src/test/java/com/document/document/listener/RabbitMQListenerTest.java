package com.document.document.listener;

import com.document.document.mapper.JsonMapper;
import com.document.document.message.DocumentMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RabbitMQListenerTest {
    ObjectMapper objectMapper = new JsonMapper().getInstance();
    RabbitMQListener rabbitMQListener = new RabbitMQListener(objectMapper);

    final String validDocumentBody = "{\"id\":1,\"content\":\"abdcd\"}";
    final String invalidDocumentBody = "{\"id\":1,\"con:\"abdcd\"}";
    final byte[] invalidMessageBytes = new byte[] {123, 34, 105, 100, 34, 58, 49};
    final byte[] validMessageBytes = new byte[] {123, 34, 105, 100, 34, 58, 49, 44, 34, 99, 111, 110, 116, 101, 110,
                                                   116, 34, 58, 34, 97, 98, 100, 99, 100, 34, 125};


    @Test
    public void returnsDocumentMessageWhenMessageIsValid() throws JsonProcessingException {
        DocumentMessage documentMessage = rabbitMQListener.parseMessage(validDocumentBody);

        assertThat(documentMessage.getId()).isEqualTo(1);
        assertThat(documentMessage.getContent()).isEqualTo("abdcd");
    }

    @Test
    public void throwsJsonProcessingExceptionWhenMessageIsNotValid() throws JsonProcessingException {
        assertThrows(JsonProcessingException.class, () -> rabbitMQListener.parseMessage(invalidDocumentBody));
    }

    @Test
    public void onMessageThrowsRuntimeExceptionWhenInvalidDataIsProvided() {
        Message message = new Message(invalidMessageBytes);
        assertThrows(RuntimeException.class, () -> rabbitMQListener.onMessage(message));
    }

    @Test
    public void onMessageThrowsNoExceptionWithValidMessage() {
        Message message = new Message(validMessageBytes);
        assertThatNoException().isThrownBy(() -> rabbitMQListener.onMessage(message));
    }
}
