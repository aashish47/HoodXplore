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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.LeaderboardViewHolder> {

    public LeaderboardRecyclerAdapter(Context context, ArrayList<LeaderBoard> leaders) {
        this.context = context;
        this.leaders = leaders;
    }

    Context context;
    ArrayList<LeaderBoard> leaders;
    String tempUri = null;
    Uri imageUri = null;


    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LeaderboardViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_leaderboard,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder leaderboardViewHolder, int i) {
        leaderboardViewHolder.tvLeaderName.setText(leaders.get(i).getName());
        tempUri = leaders.get(i).getPicture();
        imageUri = Uri.parse(tempUri);
        Picasso.get().load(imageUri).into(leaderboardViewHolder.ivLeaderImage);
    }

    @Override
    public int getItemCount() {
        return leaders.size();
    }

    class LeaderboardViewHolder extends RecyclerView.ViewHolder{
        TextView tvLeaderName;
        ImageView ivLeaderImage;
        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLeaderName = itemView.findViewById(R.id.tvLeaderName);
            ivLeaderImage = itemView.findViewById(R.id.ivLeaderImage);
        }
    }
}
