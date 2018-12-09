package com.example.guanghuili.enghelper;

import com.example.guanghuili.enghelper.Vocabulary.Guess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void putGuessMap(String vocabulary, Guess guess){
        guessMap.put(vocabulary,guess);
    }

}
