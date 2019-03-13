package com.example.major;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.QuestionViewHolder> {


    public QuestionRecyclerViewAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    Context context;
    ArrayList<Question> questions;
    String tempUri = null;
    Uri imageUri = null;


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_card,viewGroup,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Added to database",Toast.LENGTH_LONG).show();
            }
        });
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        questionViewHolder.tvCardQues.setText(questions.get(i).getQuestion());
        questionViewHolder.tvCardLocation.setText(questions.get(i).getName() + "\n" + questions.get(i).getLocation());

        tempUri = questions.get(i).getPicture();
        imageUri = Uri.parse(tempUri);


        Picasso.get().load(imageUri).into(questionViewHolder.ivCardImage);


    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder{
        TextView tvCardQues,tvCardLocation;
        ImageView ivCardImage;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardQues = itemView.findViewById(R.id.tvcardQues);
            tvCardLocation = itemView.findViewById(R.id.tvCardLocation);
            ivCardImage = itemView.findViewById(R.id.ivCardImage);
        }
    }
}
