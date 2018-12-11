package com.example.guanghuili.enghelper.Vocabulary;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.guanghuili.enghelper.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VocabularyDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private vocabCommentRecyclerViewAdapter vocabCommentRecyclerViewAdapter;

    private Vocabulary vocabulary;
    private List<String>definitionList;
    private TextView tvVocabulary;
    private TextView tvDefinition;
    private FirebaseDatabase db;
    private DatabaseReference refVocab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_detail);

        tvVocabulary = findViewById(R.id.tvVocabularyID);
        tvDefinition = findViewById(R.id.tvDefinitionID);

        recyclerView = findViewById(R.id.rvCommentID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vocabulary = (Vocabulary) getIntent().getSerializableExtra("vocabulary");
        db = FirebaseDatabase.getInstance();
        refVocab = db.getReference().child("vocabulary").child(vocabulary.getType()).child(vocabulary.getName());

        refVocab.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vocabulary = dataSnapshot.getValue(Vocabulary.class);
                definitionList = vocabulary.getDefinitionList();
                tvVocabulary.setText(vocabulary.getName());
                for(int i = 0; i < vocabulary.getDefinitionList().size(); i++){
                    tvDefinition.append(i+1 + ": " + definitionList.get(i));
                }
                vocabCommentRecyclerViewAdapter = new vocabCommentRecyclerViewAdapter(VocabularyDetailActivity.this, vocabulary.getGuessList());
                recyclerView.setAdapter(vocabCommentRecyclerViewAdapter);
                vocabCommentRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
