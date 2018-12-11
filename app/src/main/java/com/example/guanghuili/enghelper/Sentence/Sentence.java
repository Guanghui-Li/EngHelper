package com.example.guanghuili.enghelper.Sentence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sentence {
    private String highlight;
    private String sentence;
    private String translation;
    private String dateCreated;


    private List<String> translationList = new ArrayList<>();

    public Sentence(String highlight, String sentence, String translation){
        this.highlight = highlight;
        this.sentence = sentence;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
        this.translation = translation;
        translationList.add(translation);
    }

    public void addTranslation(String translation){
        translationList.add(translation);
    }
}
