package com.document.document.repository;

import com.document.document.pojo.ProcessedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedDocumentRepository extends JpaRepository<ProcessedDocument, Long> {
}
