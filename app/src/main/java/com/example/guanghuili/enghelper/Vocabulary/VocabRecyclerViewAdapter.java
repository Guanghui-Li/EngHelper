package com.example.guanghuili.enghelper.Vocabulary;

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
import com.example.guanghuili.enghelper.MainActivity;
import com.example.guanghuili.enghelper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VocabRecyclerViewAdapter extends RecyclerView.Adapter<VocabRecyclerViewAdapter.ViewHolder>{
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogBuilder;

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Vocabulary>vocabularyList;
    private Vocabulary vocabulary;
    private AppUser appUser;


    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference refSignUpAppUsers;
    private DatabaseReference refVocabulary;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //***Popup_vocab
    private TextView tvVocabulary;
    private TextView tvInputLabel;
    private TextView tvAnswerLabel;
    private TextView tvAnswer;
    private EditText etInput;
    private TextView tvInput;
    private Button btnCheck;
    private Button btnBack;
    private Button btnContinue;
    private TableRow trVocab;
    private TableRow trInput;
    private TableRow trInputTv;
    private TableRow trCheck;
    private TableRow trNavigate;
    private TableRow trAnswer;







    public VocabRecyclerViewAdapter(Context context, ArrayList<Vocabulary>vocabularyList){
        this.vocabularyList = vocabularyList;
        this.context = context;
        //clickSound = MediaAppUser.create(context, R.raw.click);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refSignUpAppUsers = database.getReference("Signed Up AppUsers");
        refVocabulary = database.getReference("vocabulary");

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();
            }
        });

        refSignUpAppUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = mAuth.getCurrentUser();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(dataSnapshot1.getValue(AppUser.class).getUsername().equals(user.getDisplayName())){
                        appUser = dataSnapshot1.getValue(AppUser.class);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public VocabRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_vocab, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final VocabRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final Vocabulary vocabulary = vocabularyList.get(position);
        if(vocabulary.getName() != null) {
            viewHolder.tvVocabDate.setText(vocabulary.getDateCreated());
            viewHolder.tvVocabName.setText(vocabulary.getName());
        }
    }

    @Override
    public int getItemCount() {
        return vocabularyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvVocabName;
        public TextView tvVocabDate;
        public CardView cardView;

        public ViewHolder(View view, Context ctx) {
            super(view);
            view.setOnClickListener(this);

            context = ctx;
            tvVocabName = view.findViewById(R.id.tvVocabNameID);
            tvVocabDate = view.findViewById(R.id.tvVocabDateID);
            cardView = view.findViewById(R.id.cardviewLayoutID);

        }

        @Override
        public void onClick(View view) {
            //clickSound.start();
            int position = getAdapterPosition();
            vocabulary = vocabularyList.get(position);
            createLoginDialog(vocabulary);
        }
    }

    private void createLoginDialog(final Vocabulary vocabulary){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogBuilder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.popup_vocab, null);

        tvVocabulary = view.findViewById(R.id.tvVocabID);
        etInput = view.findViewById(R.id.etDefinitionID);
        tvInputLabel = view.findViewById(R.id.tvInputLabelID);
        tvInput = view.findViewById(R.id.tvInputID);

        btnCheck = view.findViewById(R.id.btnCheckID);

        tvAnswerLabel = view.findViewById(R.id.tvAnswerLabelID);
        tvAnswer = view.findViewById(R.id.tvAnswerID);

        btnBack = view.findViewById(R.id.btnBackID);
        btnContinue = view.findViewById(R.id.btnContinueID);

        trVocab = view.findViewById(R.id.trVocabID);
        trInput = view.findViewById(R.id.trInputID);
        trInputTv = view.findViewById(R.id.trInputTvID);
        trAnswer = view.findViewById(R.id.trAnswerID);
        trCheck = view.findViewById(R.id.trCheckID);
        trNavigate = view.findViewById(R.id.trNavigateID);

        tvVocabulary.setText(vocabulary.getName());
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etInput.getText().toString().length() > 0){
                    trCheck.setVisibility(View.INVISIBLE);
                    trInput.setVisibility(View.INVISIBLE);
                    tvInput.setText(etInput.getText().toString());
                    trInputTv.setVisibility(View.VISIBLE);
                    tvAnswer.setText(vocabulary.getDefinitionList().get(0));
                    trAnswer.setVisibility(View.VISIBLE);
                    trNavigate.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(context, "Type in your definition", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}