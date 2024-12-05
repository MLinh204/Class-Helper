package com.example.Class_Helper.model;

import jakarta.persistence.*;

@Entity
public class Vocab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private String wordType;

    @ManyToOne
    @JoinColumn(name = "vocab_group_id")
    private VocabGroup vocabGroup;

    @ManyToOne
    @JoinColumn(name = "creator_student_id")
    private Student creatorStudent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public VocabGroup getVocabGroup() {
        return vocabGroup;
    }

    public void setVocabGroup(VocabGroup vocabGroup) {
        this.vocabGroup = vocabGroup;
    }

    public Student getCreatorStudent() {
        return creatorStudent;
    }

    public void setCreatorStudent(Student creatorStudent) {
        this.creatorStudent = creatorStudent;
    }
}
