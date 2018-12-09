package com.example.guanghuili.enghelper.Vocabulary;

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
import com.example.guanghuili.enghelper.Vocabulary.VocabRecyclerViewAdapter;
import com.example.guanghuili.enghelper.Vocabulary.Vocabulary;
import com.example.guanghuili.enghelper.Vocabulary.VocabularyManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VocabularyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VocabRecyclerViewAdapter VocabRecyclerViewAdapter;

    private boolean firstTimeDisplay = true;

    private Button btnChinese;
    private Button btnEnglish;

    private FirebaseDatabase database;
    private DatabaseReference refSignUpUsers;
    private DatabaseReference refVocabulary;
    private DatabaseReference refVocabularyCN;
    private DatabaseReference refVocabularyEN;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private VocabularyManager vocabularyManagerCN = new VocabularyManager();
    private VocabularyManager vocabularyManagerEN = new VocabularyManager();

    private AppUser appUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);


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
                Log.d("Clicked", "onclicked");
                initiateVocabRecyclerViewEN();
            }
        });
        //*****

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refSignUpUsers = database.getReference("Signed Up Users");
        refVocabulary = database.getReference("vocabulary");
        refVocabularyCN = refVocabulary.child("Chinese");
        refVocabularyEN = refVocabulary.child("English");

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


        refVocabularyCN.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vocabularyManagerCN.getVocabularyList().clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    vocabularyManagerCN.getVocabularyList().add(roomSnapshot.getValue(Vocabulary.class));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refVocabularyEN.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vocabularyManagerEN.getVocabularyList().clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    vocabularyManagerEN.getVocabularyList().add(roomSnapshot.getValue(Vocabulary.class));

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
        VocabRecyclerViewAdapter = new VocabRecyclerViewAdapter(VocabularyActivity.this, vocabularyManagerCN.getVocabularyList(), appUser, "Chinese");
        recyclerView.setAdapter(VocabRecyclerViewAdapter);
        VocabRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void initiateVocabRecyclerViewEN(){
        VocabRecyclerViewAdapter = new VocabRecyclerViewAdapter(VocabularyActivity.this, vocabularyManagerEN.getVocabularyList(), appUser, "English");
        recyclerView.setAdapter(VocabRecyclerViewAdapter);
        VocabRecyclerViewAdapter.notifyDataSetChanged();
    }

}
