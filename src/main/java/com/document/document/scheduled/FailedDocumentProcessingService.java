package com.document.document.scheduled;

import com.document.document.pojo.Document;
import com.document.document.pojo.FailedDocument;
import com.document.document.pojo.ProcessedDocument;
import com.document.document.repository.DocumentRepository;
import com.document.document.repository.FailedDocumentRepository;
import com.document.document.repository.ProcessedDocumentRepository;
import com.document.document.util.CalculationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FailedDocumentProcessingService {
    @Value(value = "${scheduled.failed.document.processor.cron}")
    private Long delay;

    private FailedDocumentRepository failedDocumentRepository;
    private DocumentRepository documentRepository;
    private ProcessedDocumentRepository processedDocumentRepository;

    @Autowired
    public FailedDocumentProcessingService(final FailedDocumentRepository failedDocumentRepository,
                                           final DocumentRepository documentRepository,
                                           final ProcessedDocumentRepository processedDocumentRepository) {
        this.failedDocumentRepository = failedDocumentRepository;
        this.documentRepository = documentRepository;
        this.processedDocumentRepository = processedDocumentRepository;
    }

    @Scheduled(fixedDelayString = "${scheduled.failed.document.processor.cron}")
    public void processFailedDocuments() {
        List<FailedDocument> failedDocumentList = failedDocumentRepository
                .findByLastFailureGreaterThanEqual(System.currentTimeMillis() - delay);

        /*
            Could have been avoided by adding a mapping in failed documents.
            Avoided it because this wasn't a requirement.
         */
        List<Document> documents = documentRepository.findByIdIn(
                        failedDocumentList.stream()
                        .map(FailedDocument::getDocumentId)
                        .collect(Collectors.toList()));

        List<ProcessedDocument> processedDocuments = documents.stream()
                                                    .map(document -> new ProcessedDocument(
                                                            document.getId(),
                                                            CalculationUtil.getNumberOfUniqueSubstrings(
                                                                    document.getFileContent()
                                                            ))
                                                    ).collect(Collectors.toList());

        List<Long> processedDocumentIdsToDelete =
                        processedDocumentRepository.saveAll(processedDocuments)
                        .stream().map(ProcessedDocument::getId).collect(Collectors.toList());

        failedDocumentRepository.deleteByIdIn(processedDocumentIdsToDelete);
    }
}
