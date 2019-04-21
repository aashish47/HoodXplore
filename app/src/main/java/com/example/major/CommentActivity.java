package com.example.major;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    TextView question;
    EditText editTextComment;
    ArrayList<Comment> comments;
    ListView listView;
    ImageView imageView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    DatabaseReference mComment;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseAuth mAuth;
    String picture;
    String uid;
    String name;
    String questionId;
    Uri photoUrl;
    Button btnComment;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        question = findViewById(R.id.tvCommentsQuestion);
        imageView = findViewById(R.id.ivCommentsListImage);
        editTextComment = findViewById(R.id.etComments);
        btnComment = findViewById(R.id.btnAddComment);
        listView = findViewById(R.id.listView);
        comments = new ArrayList<>();
        Intent intent = getIntent();
        question.setText(intent.getStringExtra("Question"));
        String tempUri = intent.getStringExtra("Picture");
        questionId = intent.getStringExtra("QUESTIONID");
        Uri imageUri = Uri.parse(tempUri);
        Picasso.get().load(imageUri).into(imageView);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        mRef = FirebaseDatabase.getInstance().getReference("QUESTIONS");
        mComment = FirebaseDatabase.getInstance().getReference("QUESTIONS").child(questionId).child("Comments");
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                // Name, email address, and profile photo Url
                uid = profile.getUid();
                name = profile.getDisplayName();
                photoUrl = profile.getPhotoUrl();
                picture = photoUrl.toString();
            }
        }
//        Picasso.get().load(photoUrl).into(ivImage);

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    try {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
                editTextComment.setText("");
            }
        });
    }

    public void addComment(){
        String comment = editTextComment.getText().toString();
        if(!TextUtils.isEmpty(comment)){
            String id = mRef.push().getKey();
            Comment comment1 = new Comment(comment,picture);
            mRef.child(questionId).child("Comments").child(id).setValue(comment1);
            Toast.makeText(getApplicationContext(),"Added to database",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Enter value",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        mComment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous  list
                comments.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Comment q = postSnapshot.getValue(Comment.class);

                    comments.add(q);
                }
                CommentListAdapter commentListAdapter = new CommentListAdapter(getApplicationContext(),R.layout.layout_comment,comments);
                listView.setAdapter(commentListAdapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
