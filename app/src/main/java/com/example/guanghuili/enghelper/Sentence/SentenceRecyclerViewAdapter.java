package com.example.guanghuili.enghelper. Sentence;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.Serializable;
import java.util.ArrayList;

public class SentenceRecyclerViewAdapter extends RecyclerView.Adapter<SentenceRecyclerViewAdapter.ViewHolder>{
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogBuilder;

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Sentence>sentenceList;
    private Sentence sentence;
    private AppUser appUser;
    private String ref;


    private FirebaseDatabase database;
    private DatabaseReference refSignInAppUsers;
    private DatabaseReference refSentence;
    private DatabaseReference databaseReference;

    //***Popup_sentence
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



    public SentenceRecyclerViewAdapter(Context context, ArrayList<Sentence>sentenceList, AppUser appUser, String ref){
        this.sentenceList = sentenceList;
        this.context = context;
        this.appUser = appUser;
        database = FirebaseDatabase.getInstance();
        this.refSentence = database.getReference("sentence").child(ref);
        this.ref = ref;
    }

    @NonNull
    @Override
    public SentenceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_sentence, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final SentenceRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final Sentence sentence = sentenceList.get(position);
        if(sentence.getHighlight() != null) {
            viewHolder.tvSentenceDate.setText(sentence.getDateCreated());
            viewHolder.tvSentenceName.setText(sentence.getHighlight());
        }
    }

    @Override
    public int getItemCount() {
        return sentenceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvSentenceName;
        public TextView tvSentenceDate;
        public CardView cardView;

        public ViewHolder(View view, Context ctx) {
            super(view);
            view.setOnClickListener(this);

            context = ctx;
            tvSentenceName = view.findViewById(R.id.tvSentenceNameID);
            tvSentenceDate = view.findViewById(R.id.tvSentenceDateID);
            cardView = view.findViewById(R.id.cardviewLayoutID);

        }

        @Override
        public void onClick(View view) {
            //clickSound.start();
            int position = getAdapterPosition();
            sentence = sentenceList.get(position);
            if(checkBypass(sentence.getHighlight())){
                bypass(sentence);
            }
            else{
                Intent intent = new Intent(context,SentenceCheckActivity.class);
                intent.putExtra("sentence", sentence);
                intent.putExtra("appUser", appUser);
                intent.putExtra("ref",ref);
                context.startActivity(intent);
            }
        }
    }

    public boolean checkBypass(String sentenceName){
        for(String key : appUser.getGuessMap().keySet()){
            if(key.equals(sentenceName)){
                return true;
            }
        }
        return false;
    }

    public void bypass(Sentence sentence){
        Intent intent = new Intent(context, SentenceDetailActivity.class);
        intent.putExtra("sentence",sentence);
        context.startActivity(intent);
    }
}