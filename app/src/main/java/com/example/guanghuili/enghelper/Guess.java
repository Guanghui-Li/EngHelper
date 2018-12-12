package com.example.guanghuili.enghelper;

import com.example.guanghuili.enghelper.AppUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Guess implements Serializable {
    private String guess;
    private String dateCreated;
    private AppUser appUser;

    public Guess(){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
    }

    public Guess(String guess, AppUser appUser){
        this.guess = guess;
        this.appUser = appUser;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        this.dateCreated = df.format(new Date());
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
