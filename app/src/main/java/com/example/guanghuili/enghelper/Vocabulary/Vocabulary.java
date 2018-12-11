package com.example.guanghuili.enghelper.Vocabulary;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vocabulary implements Serializable {
    private String name;
    private String definition;
    private String dateCreated;
    private Guess guess;
    private String type;
    private List<Guess> guessList = new ArrayList<>();


    private List<String> definitionList = new ArrayList<>();

    public Vocabulary(){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
    }

    public Vocabulary(String name, String definition, String type){
        this.name = name;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
        this.type = type;
        this.definition = definition;
        definitionList.add(definition);
    }

    public void addDefinition(String definition){
        definitionList.add(definition);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public List<String> getDefinitionList() {
        return definitionList;
    }

    public Guess getGuess() {
        return guess;
    }

    public void setGuess(Guess guess) {
        this.guess = guess;
    }

    public List<Guess> getGuessList() {
        return guessList;
    }

    public void setGuessList(List<Guess> guessList) {
        this.guessList = guessList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
