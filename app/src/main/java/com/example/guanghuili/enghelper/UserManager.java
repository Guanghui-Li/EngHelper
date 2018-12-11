package com.example.guanghuili.enghelper;

import java.util.ArrayList;

public class UserManager {
    private Boolean duplicateUsername = true;
    private ArrayList <AppUser> appUserList = new ArrayList<>();
    private AppUser signedInAppUser = new AppUser();

    private ArrayList <String> usernameList = new ArrayList<>();

    public UserManager(){

    }
    public AppUser createUser(String username, String email){
        AppUser appUser = new AppUser(username, email);
        appUserList.add(appUser);
        usernameList.add(username);
        return appUser;
    }

    public ArrayList<AppUser> getuserList(){
        return appUserList;
    }



   public AppUser getSignedInUser(String email){
       for(int i = 0; i < appUserList.size(); i++){
           if(appUserList.get(i).getEmail().equals(email)){
               signedInAppUser.setEmail(appUserList.get(i).getEmail());
               signedInAppUser.setUsername(appUserList.get(i).getUsername());
               break;
           }
       }
       return signedInAppUser;
   }

   public boolean getDuplicateUsername(String username){
       if(usernameList.size() == 0){
           duplicateUsername = false;
       }
       else {
           for (int i = 0; i < usernameList.size(); i++) {
               if (usernameList.get(i).equals(username)) {
                   duplicateUsername = true;
                   break;
               } else {
                   duplicateUsername = false;
               }
           }
       }
        return duplicateUsername;
   }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(ArrayList<String> usernameList) {
        this.usernameList = usernameList;
    }

}
