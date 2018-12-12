package com.example.guanghuili.enghelper.Sentence;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanghuili.enghelper.AppUser;
import com.example.guanghuili.enghelper.Guess;
import com.example.guanghuili.enghelper.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SentenceCheckActivity extends AppCompatActivity {
    private TextView tvSentence;
    private TextView tvInputLabel;
    private TextView tvAnswerLabel;
    private TextView tvAnswer;
    private EditText etInput;
    private TextView tvInput;
    private Button btnCheck;
    private Button btnBack;
    private Button btnContinue;
    private TableRow trSentence;
    private TableRow trInput;
    private TableRow trInputTv;
    private TableRow trCheck;
    private TableRow trNavigate;
    private TableRow trAnswer;

    private Sentence sentence;
    private AppUser appUser;
    private String ref;

    private FirebaseDatabase database;
    private DatabaseReference refSignInAppUsers;
    private DatabaseReference refSentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_check);

        sentence = (Sentence) getIntent().getSerializableExtra("sentence");
        appUser = (AppUser) getIntent().getSerializableExtra("appUser");
        ref = getIntent().getStringExtra("ref");
        database = FirebaseDatabase.getInstance();
        refSentence = database.getReference("sentence").child(ref);
        tvSentence = findViewById(R.id.tvSentenceID);
        etInput = findViewById(R.id.etDefinitionID);
        tvInputLabel = findViewById(R.id.tvInputLabelID);
        tvInput = findViewById(R.id.tvInputID);

        btnCheck = findViewById(R.id.btnCheckID);

        tvAnswerLabel = findViewById(R.id.tvAnswerLabelID);
        tvAnswer = findViewById(R.id.tvAnswerID);

        btnBack = findViewById(R.id.btnBackID);
        btnContinue = findViewById(R.id.btnContinueID);

        trSentence = findViewById(R.id.trSentenceID);
        trInput = findViewById(R.id.trInputID);
        trInputTv = findViewById(R.id.trInputTvID);
        trAnswer = findViewById(R.id.trAnswerID);
        trCheck = findViewById(R.id.trCheckID);
        trNavigate = findViewById(R.id.trNavigateID);

        tvSentence.setText(sentence.getSentence());

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etInput.getText().toString().length() > 0){

                    //*****************************
                    //add to the user's guess list
                    Guess guess = appUser.makeGuess(sentence.getHighlight(),etInput.getText().toString());

                    //update in database
                    refSignInAppUsers = database.getReference("Signed Up Users").child(appUser.getUsername());
                    refSignInAppUsers.setValue(appUser);

                    sentence.getGuessList().add(guess);
                    refSentence.child(sentence.getHighlight()).setValue(sentence);

                    //*****************************
                    trCheck.setVisibility(View.INVISIBLE);
                    trInput.setVisibility(View.INVISIBLE);
                    tvInput.setText(etInput.getText().toString());
                    trInputTv.setVisibility(View.VISIBLE);
                    tvAnswer.setText(sentence.getTranslationList().get(0));
                    trAnswer.setVisibility(View.VISIBLE);
                    trNavigate.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(SentenceCheckActivity.this, "Type in your definition", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SentenceCheckActivity.this, SentenceActivity.class);
                startActivity(intent);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SentenceCheckActivity.this, SentenceDetailActivity.class);
                intent.putExtra("sentence", sentence);
                startActivity(intent);
            }
        });
    }
}
