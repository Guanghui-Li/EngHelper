package com.example.guanghuili.enghelper.Vocabulary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vocabulary {
    private String name;
    private String definition;
    private String dateCreated;


    private List<String> definitionList = new ArrayList<>();

    public Vocabulary(){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
    }

    public Vocabulary(String name, String definition){
        this.name = name;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
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

    public void setdateCreated(String dateCreated) {
        dateCreated = dateCreated;
    }

    public List<String> getDefinitionList() {
        return definitionList;
    }
}
