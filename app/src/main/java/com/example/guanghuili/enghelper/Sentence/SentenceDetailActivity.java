package com.example.guanghuili.enghelper.Sentence;

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

public class SentenceDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SentenceCommentRecyclerViewAdapter SentenceCommentRecyclerViewAdapter;

    private Sentence sentence;
    private List<String>definitionList;
    private TextView tvSentence;
    private TextView tvDefinition;
    private FirebaseDatabase db;
    private DatabaseReference refVocab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_detail);

        tvSentence = findViewById(R.id.tvSentenceID);
        tvDefinition = findViewById(R.id.tvDefinitionID);

        recyclerView = findViewById(R.id.rvCommentID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sentence = (Sentence) getIntent().getSerializableExtra("sentence");
        db = FirebaseDatabase.getInstance();
        refVocab = db.getReference().child("sentence").child(sentence.getType()).child(sentence.getHighlight());

        refVocab.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sentence = dataSnapshot.getValue(Sentence.class);
                definitionList = sentence.getTranslationList();
                tvSentence.setText(sentence.getSentence());
                for(int i = 0; i < sentence.getTranslationList().size(); i++){
                    tvDefinition.append(i+1 + ": " + definitionList.get(i));
                }
                SentenceCommentRecyclerViewAdapter = new SentenceCommentRecyclerViewAdapter(SentenceDetailActivity.this, sentence.getGuessList());
                recyclerView.setAdapter(SentenceCommentRecyclerViewAdapter);
                SentenceCommentRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
