package com.example.major;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderboardFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<LeaderBoard> leaders;
    LeaderboardRecyclerAdapter adapter;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaderboard,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvLeaders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        leaders = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("QUESTIONS");
    }

    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous  list
                leaders.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    LeaderBoard q = postSnapshot.getValue(LeaderBoard.class);
//                    double lat1 = q.latitude;
//                    double long1 = q.longitude;
//                    double currentLat = "";
//                    double currentLong = "";
//                    float[] result = new float[1];
//                    Location.distanceBetween(lat1,long1,currentLat,currentLong,result);
                    leaders.add(q);
                }

                //creating adapter
                adapter = new LeaderboardRecyclerAdapter(getContext(), leaders);
                //attaching adapter to the listview
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
