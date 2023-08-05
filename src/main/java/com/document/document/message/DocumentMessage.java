package com.document.document.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DocumentMessage implements Serializable {
    @JsonProperty("id")
    Long id;

    @JsonProperty("content")
    String content;

    DocumentMessage() {}

    public DocumentMessage(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "DocumentMessage{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
