package com.example.guanghuili.enghelper.Sentence;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.guanghuili.enghelper.Guess;
import com.example.guanghuili.enghelper.R;

import java.util.List;

public class SentenceCommentRecyclerViewAdapter extends RecyclerView.Adapter<SentenceCommentRecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<Guess>guessList;


    public SentenceCommentRecyclerViewAdapter(Context context, List<Guess> guessList){
        this.guessList = guessList;
        this.context = context;
    }

    @NonNull
    @Override
    public SentenceCommentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_sentence_comment, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SentenceCommentRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Guess guess = guessList.get(position);
        if(guess != null) {
            viewHolder.tvUserDefinition.setText(guess.getGuess());
            viewHolder.tvUsername.setText(guess.getAppUser().getUsername());
            viewHolder.tvDate.setText(guess.getDateCreated());

        }
    }

    @Override
    public int getItemCount() {
        return guessList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvUserDefinition;
        public TextView tvUsername;
        public TextView tvDate;

        public ViewHolder(View view) {
            super(view);
            tvUserDefinition = view.findViewById(R.id.tvUserDefinitionID);
            tvUsername = view.findViewById(R.id.tvUsernameID);
            tvDate = view.findViewById(R.id.tvDateID);

        }
    }
}