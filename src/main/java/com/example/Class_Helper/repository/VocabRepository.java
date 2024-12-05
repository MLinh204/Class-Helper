package com.example.Class_Helper.repository;

import com.example.Class_Helper.model.Vocab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocabRepository extends JpaRepository<Vocab, Long> {
    List<Vocab> findByVocabGroupIdAndCreatorStudentIdNot(Long vocabGroupId, Long creatorStudentId);

    List<Vocab> findByVocabGroupId(Long vocabGroupId);
    Vocab findByWordAndWordType(String word, String wordType);
}
