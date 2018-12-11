package com.example.guanghuili.enghelper.Vocabulary;

import java.util.ArrayList;

public class VocabularyManager {
    private ArrayList <Vocabulary> vocabularyList = new ArrayList<>();

    public VocabularyManager(){

    }

    public ArrayList<Vocabulary> getVocabularyList() {
        return vocabularyList;
    }

    public void setVocabularyList(ArrayList<Vocabulary> vocabularyList) {
        this.vocabularyList = vocabularyList;
    }
}
