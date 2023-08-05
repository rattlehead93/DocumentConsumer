package com.document.document.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DocumentMessage implements Serializable {
    @JsonProperty("id")
    String id;

    @JsonProperty("content")
    String content;

    public DocumentMessage(String id, String content) {
        this.id = id;
        this.content = content;
    }
}
