package com.example.guanghuili.enghelper.Sentence;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guanghuili.enghelper.AppUser;
import com.example.guanghuili.enghelper.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SentenceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SentenceRecyclerViewAdapter SentenceRecyclerViewAdapter;

    private boolean firstTimeDisplay = true;

    private Button btnChinese;
    private Button btnEnglish;

    private FirebaseDatabase database;
    private DatabaseReference refSignUpUsers;
    private DatabaseReference refSentence;
    private DatabaseReference refSentenceCN;
    private DatabaseReference refSentenceEN;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private SentenceManager sentenceManagerCN = new SentenceManager();
    private SentenceManager sentenceManagerEN = new SentenceManager();

    private AppUser appUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence);


        //**********RecyclerView***********
        recyclerView = (RecyclerView) findViewById(R.id.rvVocabID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //**********************************

        //*****
        btnChinese = findViewById(R.id.btnChineseID);
        btnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateVocabRecyclerViewCN();
            }
        });
        btnEnglish = findViewById(R.id.btnEnglishID);
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateVocabRecyclerViewEN();
            }
        });
        //*****

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refSignUpUsers = database.getReference("Signed Up Users");
        refSentence = database.getReference("sentence");
        refSentenceCN = refSentence.child("Chinese");
        refSentenceEN = refSentence.child("English");

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();
            }
        });


        refSignUpUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = mAuth.getCurrentUser();
                Log.d("checking","checking");

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(user != null) {//(double check)when user sign up in the mainActivity, this listener will be called, but the activity has not been called, so there is no user
                        if (dataSnapshot1.getValue(AppUser.class).getUsername().equals(user.getDisplayName())) {
                            appUser = dataSnapshot1.getValue(AppUser.class);
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        refSentenceCN.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sentenceManagerCN.getSentenceList().clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    Sentence sentence = roomSnapshot.getValue(Sentence.class);
                    if(processSentence(sentence)){
                        sentenceManagerCN.getSentenceList().add(sentence);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refSentenceEN.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sentenceManagerEN.getSentenceList().clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    sentenceManagerEN.getSentenceList().add(roomSnapshot.getValue(Sentence.class));

                }
                if(firstTimeDisplay){
                    initiateVocabRecyclerViewEN();
                    firstTimeDisplay = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void initiateVocabRecyclerViewCN(){
        SentenceRecyclerViewAdapter = new SentenceRecyclerViewAdapter(SentenceActivity.this, sentenceManagerCN.getSentenceList(), appUser, "Chinese");
        recyclerView.setAdapter(SentenceRecyclerViewAdapter);
        SentenceRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void initiateVocabRecyclerViewEN(){
        SentenceRecyclerViewAdapter = new SentenceRecyclerViewAdapter(SentenceActivity.this, sentenceManagerEN.getSentenceList(), appUser, "English");
        recyclerView.setAdapter(SentenceRecyclerViewAdapter);
        SentenceRecyclerViewAdapter.notifyDataSetChanged();
    }

    public boolean processSentence(Sentence vocab){
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String date = df.format(new Date());
        String [] dateArray = date.split("-");
        int monthNow = Integer.parseInt(dateArray[0]);
        int dayNow = Integer.parseInt(dateArray[1]);
        int yearNow = Integer.parseInt(dateArray[2]);

        String [] createdDateArray = vocab.getDateCreated().split("-");
        int monthCreated = Integer.parseInt(createdDateArray[0]);
        int dayCreated = Integer.parseInt(createdDateArray[1]);
        int yearCreated = Integer.parseInt(createdDateArray[2]);

        if(yearCreated <= yearNow){
            if(yearCreated < yearNow){
                return true;
            }
            else{
                if(monthCreated <= monthNow){
                    if(monthCreated < monthNow){
                        return true;
                    }
                    else{
                        if(dayCreated <= dayNow){
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                }
                else{
                    return false;
                }
            }
        }
        else{
            return false;
        }
    }

}
