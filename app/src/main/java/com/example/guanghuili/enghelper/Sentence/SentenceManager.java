package com.example.guanghuili.enghelper.Sentence;

import java.util.ArrayList;

public class SentenceManager {
    private ArrayList <Sentence> sentenceList = new ArrayList<>();

    public SentenceManager(){

    }

    public ArrayList<Sentence> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(ArrayList<Sentence> sentenceList) {
        this.sentenceList = sentenceList;
    }
}
