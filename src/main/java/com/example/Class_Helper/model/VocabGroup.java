package com.example.Class_Helper.model;

import jakarta.persistence.*;
import java.util.List;
@Entity
public class VocabGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vocabGroupName;

    @OneToMany(mappedBy = "vocabGroup", cascade = CascadeType.ALL)
    private List<Vocab> vocabs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVocabGroupName() {
        return vocabGroupName;
    }

    public void setVocabGroupName(String vocabGroupName) {
        this.vocabGroupName = vocabGroupName;
    }

    public List<Vocab> getVocabs() {
        return vocabs;
    }

    public void setVocabs(List<Vocab> vocabs) {
        this.vocabs = vocabs;
    }
}
