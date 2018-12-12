package com.example.guanghuili.enghelper.Sentence;

import com.example.guanghuili.enghelper.Guess;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sentence implements Serializable {
    private String highlight;
    private String sentence;
    private String translation;
    private String dateCreated;
    private Guess guess;
    private String type;
    private List<Guess> guessList = new ArrayList<>();
    private List<String> translationList = new ArrayList<>();

    public Sentence(){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
    }

    public Sentence(String highlight, String sentence, String translation, String type){
        this.highlight = highlight;
        this.sentence = sentence;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
        this.translation = translation;
        this.type = type;
        translationList.add(translation);
    }

    public void addTranslation(String translation){
        translationList.add(translation);
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getSentence() {
        return sentence;
    }


    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Guess getGuess() {
        return guess;
    }

    public void setGuess(Guess guess) {
        this.guess = guess;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Guess> getGuessList() {
        return guessList;
    }

    public void setGuessList(List<Guess> guessList) {
        this.guessList = guessList;
    }

    public List<String> getTranslationList() {
        return translationList;
    }
}
