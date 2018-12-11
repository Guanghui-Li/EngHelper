package com.example.guanghuili.enghelper.Paragraph;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paragraph {
    private String title;
    private String paragraph;
    private String translation;
    private String dateCreated;

    private List<String> translationList = new ArrayList<>();

    public Paragraph(String title, String paragraph, String translation){
        this.title = title;
        this.paragraph = paragraph;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
        this.translation = translation;
        translationList.add(translation);
    }
}
