package com.example.guanghuili.enghelper;

import java.io.Serializable;
import java.util.HashMap;

public class AppUser implements Serializable {
    private String username;
    private String email;
    private HashMap<String,Guess> guessMap = new HashMap<>();

    public AppUser(){
    }

    public AppUser(String username, String email) {
        this.username = username;
        this.email = email;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, Guess> getGuessMap() {
        return guessMap;
    }

    public Guess makeGuess(String vocabulary, String g){
        Guess guess = new Guess(g, new AppUser());
        this.guessMap.put(vocabulary, guess);
        Guess guess1 = new Guess(g, this);

        return guess1;
    }

}
