package com.document.document.service;

import com.document.document.message.DocumentMessage;
import com.document.document.pojo.FailedDocument;
import com.document.document.pojo.ProcessedDocument;
import com.document.document.repository.FailedDocumentRepository;
import com.document.document.repository.ProcessedDocumentRepository;
import com.document.document.util.CalculationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class DocumentProcessingService {
    private static final Logger log = LoggerFactory.getLogger(DocumentProcessingService.class);

    private final ProcessedDocumentRepository processedDocumentRepository;
    private final FailedDocumentRepository failedDocumentRepository;

    @Autowired
    public DocumentProcessingService(final ProcessedDocumentRepository processedDocumentRepository,
                                     final FailedDocumentRepository failedDocumentRepository) {
        this.processedDocumentRepository = processedDocumentRepository;
        this.failedDocumentRepository = failedDocumentRepository;
    }

    public ProcessedDocument processDocument(final DocumentMessage documentMessage) {
        return saveDocument(new ProcessedDocument(
                                        documentMessage.getId(),
                                        CalculationUtil.getNumberOfUniqueSubstrings(documentMessage.getContent())));
    }

    private ProcessedDocument saveDocument(final ProcessedDocument processedDocument) {
        try {
            return processedDocumentRepository.save(processedDocument);
        } catch (final Exception e) {
            log.error("Exception while saving processed document: ", e);
            failedDocumentRepository.save(new FailedDocument(processedDocument.getId(),
                                              System.currentTimeMillis(), e.getMessage()));
            throw e;
        }
    }
}
