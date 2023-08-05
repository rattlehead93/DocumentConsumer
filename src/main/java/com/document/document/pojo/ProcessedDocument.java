package com.document.document.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProcessedDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long documentId;

    @Column
    private Integer uniqueNumber;

    public ProcessedDocument(Long documentId, Integer uniqueNumber) {
        this.documentId = documentId;
        this.uniqueNumber = uniqueNumber;
    }

    public ProcessedDocument() {}

    public Long getId() {
        return id;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public Integer getUniqueNumber() {
        return uniqueNumber;
    }

}
