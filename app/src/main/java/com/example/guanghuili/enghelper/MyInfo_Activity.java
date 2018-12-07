package com.example.guanghuili.enghelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class MyInfo_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VocabRecyclerViewAdapter VocabRecyclerViewAdapter;

    private FirebaseDatabase database;
    private DatabaseReference refSignUpPlayers;
    private DatabaseReference refVocabulary;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private VocabularyManager vocabularyManager = new VocabularyManager();
    private AppUser appUser;

    private BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Vocabulary:
                    initiateVocabRecyclerView();
                    return true;

                    /*
                case R.id.navigation_sentence:

                    return true;
                case R.id.navigation_paragraph:

                    return true;
                    */

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //**********RecyclerView***********
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //**********************************

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refSignUpPlayers = database.getReference("Signed Up Players");
        refVocabulary = database.getReference("vocabulary");

        navigation = findViewById(R.id.navigation);
        View view = navigation.findViewById(navigation.getSelectedItemId());
        view.performClick();



        refVocabulary.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vocabularyManager.getVocabularyList().clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren()){
                    vocabularyManager.getVocabularyList().add(roomSnapshot.getValue(Vocabulary.class));

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();
            }
        });


        refSignUpPlayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = mAuth.getCurrentUser();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(user != null) {//(double check)when user sign up in the mainActivity, this listener will be called, but the activity has not been called, so there is no user
                        if (dataSnapshot1.getValue(AppUser.class).getUsername().equals(user.getDisplayName())) {
                            appUser = dataSnapshot1.getValue(AppUser.class);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void initiateVocabRecyclerView(){

        VocabRecyclerViewAdapter = new VocabRecyclerViewAdapter(MyInfo_Activity.this, vocabularyManager.getVocabularyList());
        recyclerView.setAdapter(VocabRecyclerViewAdapter);
        VocabRecyclerViewAdapter.notifyDataSetChanged();

    }

}
