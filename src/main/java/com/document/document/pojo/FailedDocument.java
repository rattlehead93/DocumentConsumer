package com.document.document.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FailedDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long documentId;

    @Column
    private Long lastFailure;

    @Column
    private String failureReason;

    public FailedDocument(Long documentId, Long lastFailure, String failureReason) {
        this.documentId = documentId;
        this.lastFailure = lastFailure;
        this.failureReason = failureReason;
    }

    public FailedDocument() {}

    public Long getId() {
        return id;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public Long getLastFailure() {
        return lastFailure;
    }

    public String getFailureReason() {
        return failureReason;
    }

}
