package com.example.guanghuili.enghelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanghuili.enghelper.Vocabulary.Guess;
import com.example.guanghuili.enghelper.Vocabulary.Vocabulary;
import com.example.guanghuili.enghelper.Vocabulary.VocabularyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private UserManager userManager = new UserManager();
    private AppUser appUser;
    //***MainActivity***
    private Button btnSignup;
    private Button btnLogin;
    private Button btnSignOut;
    private TextView tvGreeting;
    private Button btnVocabulary;
    private Button btnSentence;
    private Button btnParagraph;
    //*******************

    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogBuilder;

    //***Login Page***
    private EditText etEmailLogin;
    private EditText etPasswordLogin;
    private Button btnLoginXML;
    //**************

    //***Sign Up Page***
    private EditText etNameSignUp;
    private EditText etEmailSignUp;
    private EditText etPasswordSignUp;
    private Button btnSignUpXML;
    //**************

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference refSignUpUsers;
    private DatabaseReference refUsername;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        refSignUpUsers = database.getReference("Signed Up Users");
        refUsername = database.getReference("username");

        btnSignup = findViewById(R.id.btnSignUpID);
        btnLogin = findViewById(R.id.btnLoginID);
        btnSignOut = findViewById(R.id.btnSignOutID);
        btnVocabulary = findViewById(R.id.btnVocabID);
        btnSentence = findViewById(R.id.btnSentencesID);
        btnParagraph = findViewById(R.id.btnParagraphsID);
        tvGreeting = findViewById(R.id.tvGreetingID);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clickSound.start();
                createSignUpDialog();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clickSound.start();
                mAuth.signOut();
                Toast.makeText(MainActivity.this,"Signed Out",Toast.LENGTH_LONG).show();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clickSound.start();
                createLoginDialog();
            }
        });


        btnVocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VocabularyActivity.class);
                startActivity(intent);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = mAuth.getCurrentUser();
                if(user != null){
                    if(user.getDisplayName()!= null) {
                        //user is signed in
                        btnLogin.setVisibility(View.GONE);
                        btnSignOut.setVisibility(View.VISIBLE);
                        btnSignup.setVisibility(View.GONE);
                        tvGreeting.setText("Hello " + user.getDisplayName());
                        tvGreeting.setVisibility(View.VISIBLE);
                        btnVocabulary.setEnabled(true);
                        btnSentence.setEnabled(true);
                        btnParagraph.setEnabled(true);

                    }
                    else{
                        //User is signed out
                        btnLogin.setVisibility(View.VISIBLE);
                        btnSignOut.setVisibility(View.GONE);
                        btnSignup.setVisibility(View.VISIBLE);
                        tvGreeting.setVisibility(View.GONE);
                        btnVocabulary.setEnabled(false);
                        btnSentence.setEnabled(false);
                        btnParagraph.setEnabled(false);
                    }

                }
                else{
                    //User is signed out
                    btnLogin.setVisibility(View.VISIBLE);
                    btnSignOut.setVisibility(View.GONE);
                    btnSignup.setVisibility(View.VISIBLE);
                    tvGreeting.setVisibility(View.GONE);
                    btnVocabulary.setEnabled(false);
                    btnSentence.setEnabled(false);
                    btnParagraph.setEnabled(false);
                }
            }
        };

        refSignUpUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appUser = dataSnapshot.child(user.getDisplayName()).getValue(AppUser.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot username : dataSnapshot.getChildren()){
                    userManager.getUsernameList().add(username.getValue(String.class));
                }
                /*
                Log.d("checking",String.valueOf(userManager.getuserList().size()));
                user = mAuth.getCurrentUser();

                DatabaseReference ref = database.getReference("vocabulary").child("English");
                Vocabulary vocab = new Vocabulary("big","大");
                ref.child(vocab.getName()).setValue(vocab);
                Guess guess = new Guess("大");
                appUser.putGuessMap(vocab.getName(),guess);
                DatabaseReference ref1 = database.getReference("Signed Up Users").child(appUser.getUsername());
                ref1.setValue(appUser);
                guess.setAppUser(appUser);

                vocab.getGuessList().add(guess);
                */

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //******test******

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createLoginDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.login,null);
        etEmailLogin = (EditText)view.findViewById(R.id.etEmailLoginID);
        etPasswordLogin = (EditText) view.findViewById(R.id.etPasswordLoginID);
        btnLoginXML = (Button) view.findViewById(R.id.btnLoginXMLID);
        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        btnLoginXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clickSound.start();
                final String email = etEmailLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();

                if (!email.equals("") && !password.equals("")) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Failed Sign in", Toast.LENGTH_LONG).show();
                            } else {
                                user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Signed In!", Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }

    private void createSignUpDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.signup,null);
        etNameSignUp = (EditText)view.findViewById(R.id.etNameSignupID);
        etEmailSignUp = (EditText)view.findViewById(R.id.etEmailSignupID);
        etPasswordSignUp = (EditText) view.findViewById(R.id.etPasswordSignupID);
        btnSignUpXML = (Button) view.findViewById(R.id.btnSignupXMLID);
        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        btnSignUpXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clickSound.start();
                final String username = etNameSignUp.getText().toString();
                final String email = etEmailSignUp.getText().toString();
                final String password = etPasswordSignUp.getText().toString();
                boolean duplicateCopy = userManager.getDuplicateUsername(username);
                if(duplicateCopy == true) {
                    Toast.makeText(MainActivity.this, "Username Taken", Toast.LENGTH_LONG).show();
                }
                if (!email.equals("") && !password.equals("") && !username.equals("") && duplicateCopy == false){
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Failed Sign Up", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Signed Up!", Toast.LENGTH_LONG).show();
                                //***store the sign up user info to the database***//
                                appUser = userManager.createUser(username,email);
                                refSignUpUsers.child(username).setValue(appUser);

                                refUsername.child(username).setValue(username);

                                user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username).build();

                                user.updateProfile(profileUpdates);
                                mAuth.signOut();
                                alertDialog.dismiss();

                                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Failed Sign in", Toast.LENGTH_LONG).show();
                                        } else {
                                            user = mAuth.getCurrentUser();
                                            Toast.makeText(MainActivity.this, "Signed In!", Toast.LENGTH_LONG).show();
                                            alertDialog.dismiss();
                                            Log.d("Step","Step3");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }


}
