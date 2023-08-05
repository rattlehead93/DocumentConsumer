package com.document.document.mapper;


import com.document.document.message.DocumentMessage;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonMapperTest {
    ObjectMapper objectMapper = new JsonMapper().getInstance();

    final String validDocumentBody = "{\"id\":1,\"content\":\"abdcd\"}";
    final String invalidDocumentBody = "{\"id\":1,\"con:\"abdcd\"}";

    @Test
    public void canCreateObjectWhenProvidedValidJson() throws JsonProcessingException {
        final DocumentMessage documentMessage = objectMapper.readValue(validDocumentBody, DocumentMessage.class);
        assertThat(documentMessage.getId()).isEqualTo(1L);
        assertThat(documentMessage.getContent()).isEqualTo("abdcd");
    }

    @Test
    public void throwsExceptionWhenProvidedInvalidJson() throws JsonProcessingException {
        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(invalidDocumentBody, DocumentMessage.class));
    }
}
