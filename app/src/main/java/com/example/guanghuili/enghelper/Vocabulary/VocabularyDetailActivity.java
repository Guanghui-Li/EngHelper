package com.example.guanghuili.enghelper.Vocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.guanghuili.enghelper.R;

import java.util.ArrayList;
import java.util.List;

public class VocabularyDetailActivity extends AppCompatActivity {
    private Vocabulary vocabulary;
    private List<String>definitionList;
    private TextView tvVocabulary;
    private TextView tvDefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_detail);
        vocabulary = (Vocabulary) getIntent().getSerializableExtra("vocabulary");
        definitionList = vocabulary.getDefinitionList();

        tvVocabulary = findViewById(R.id.tvVocabularyID);
        tvDefinition = findViewById(R.id.tvDefinitionID);
        tvVocabulary.setText(vocabulary.getName());
        for (int i = 1; i <= definitionList.size(); i++){
            tvDefinition.append("\t" + i + ": " + definitionList.get(i-1) + "\n");
        }


    }
}
