package com.document.document.listener;

import com.document.document.message.DocumentMessage;
import com.document.document.pojo.ProcessedDocument;
import com.document.document.service.DocumentProcessingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitMQListenerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DocumentProcessingService documentProcessingService;

    @InjectMocks
    private RabbitMQListener rabbitMQListener;

    final String validDocumentBody = "{\"id\":1,\"content\":\"abdcd\"}";
    final String invalidDocumentBody = "{\"id\":1,\"con:\"abdcd\"}";

    final byte[] invalidMessageBytes = new byte[] {123, 34, 105, 100, 34, 58, 49};
    final byte[] validMessageBytes = new byte[] {123, 34, 105, 100, 34, 58, 49, 44, 34, 99, 111, 110, 116, 101, 110,
                                                   116, 34, 58, 34, 97, 98, 100, 99, 100, 34, 125};

    final DocumentMessage validDocumentMessage = new DocumentMessage(1L, "abdcd");

    @Test
    public void returnsDocumentMessageWhenMessageIsValid() throws JsonProcessingException {
        when(objectMapper.readValue(validDocumentBody, DocumentMessage.class)).thenReturn(validDocumentMessage);

        DocumentMessage documentMessage = rabbitMQListener.parseMessage(validDocumentBody);

        assertThat(documentMessage.getId()).isEqualTo(1L);
        assertThat(documentMessage.getContent()).isEqualTo("abdcd");
    }

    @Test
    public void throwsJsonProcessingExceptionWhenMessageIsNotValid() throws JsonProcessingException {
        when(objectMapper.readValue(invalidDocumentBody, DocumentMessage.class)).thenThrow(
                JsonProcessingException.class);
        assertThrows(JsonProcessingException.class, () -> rabbitMQListener.parseMessage(invalidDocumentBody));
    }

    @Test
    public void onMessageThrowsRuntimeExceptionWhenInvalidDataIsProvided() throws JsonProcessingException {
        Message message = new Message(invalidMessageBytes);

        when(objectMapper.readValue(invalidDocumentBody, DocumentMessage.class)).thenThrow(JsonProcessingException.class);

        assertThrows(RuntimeException.class, () -> rabbitMQListener.onMessage(message));
    }

    @Test
    public void onMessageThrowsNoExceptionWithValidMessage() throws JsonProcessingException {
        Message message = new Message(validMessageBytes);

        when(objectMapper.readValue(validDocumentBody, DocumentMessage.class)).thenReturn(validDocumentMessage);
        when(documentProcessingService.processDocument(any())).thenReturn(new ProcessedDocument());
        assertThatNoException().isThrownBy(() -> rabbitMQListener.onMessage(message));
    }
}
