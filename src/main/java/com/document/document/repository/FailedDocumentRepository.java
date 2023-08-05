package com.document.document.repository;

import com.document.document.pojo.FailedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailedDocumentRepository extends JpaRepository<FailedDocument, Long> {
    List<FailedDocument> findByLastFailureGreaterThanEqual(Long lastFailure);

    @Modifying
    void deleteByIdIn(List<Long> ids);
}
