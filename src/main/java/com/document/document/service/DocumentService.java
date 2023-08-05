package com.document.document.service;

import com.document.document.pojo.Document;
import com.document.document.repository.DocumentRepository;
import com.document.document.request.DocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(final DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void saveDocument(final Document document) {
        documentRepository.save(document);
    }

    public Document createDocumentFromRequest(final DocumentRequest documentRequest) {
        final Document document = new Document();
        document.setFileContent(documentRequest.getFileContent());
        document.setExtension(documentRequest.getExtension());
        document.setFileName(documentRequest.getFileName());
        return document;
    }
}
