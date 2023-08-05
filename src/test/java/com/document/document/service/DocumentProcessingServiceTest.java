package com.document.document.service;

import com.document.document.message.DocumentMessage;
import com.document.document.pojo.FailedDocument;
import com.document.document.pojo.ProcessedDocument;
import com.document.document.repository.FailedDocumentRepository;
import com.document.document.repository.ProcessedDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentProcessingServiceTest {
    @Mock
    ProcessedDocumentRepository processedDocumentRepository;

    @Mock
    FailedDocumentRepository failedDocumentRepository;

    @InjectMocks
    DocumentProcessingService documentProcessingService;

    private ProcessedDocument processedDocument = new ProcessedDocument(1L, 6);

    @Test
    public void successfullySavesProcessedDocumentWhenNoErrorsInSaving() {
        when(processedDocumentRepository.save(processedDocument)).thenReturn(processedDocument);

        ProcessedDocument processed = documentProcessingService
                .processDocument(new DocumentMessage(1L, "abc"));
        assertThat(processed.getDocumentId()).isEqualTo(1);
        assertThat(processed.getUniqueNumber()).isEqualTo(6);
    }

    @Test
    public void throwsExceptionIfProcessedDocumentSavingProblem() {
        when(processedDocumentRepository.save(any())).thenThrow(RuntimeException.class);
        when(failedDocumentRepository.save(any())).thenReturn(new FailedDocument());

        assertThrows(Exception.class, ()->
                documentProcessingService.processDocument(new DocumentMessage(1L, "abc")));
        verify(failedDocumentRepository, atLeast(1)).save(any());
    }
}
