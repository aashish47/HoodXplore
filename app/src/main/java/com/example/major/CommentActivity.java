package com.example.major;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseAuth mAuth;
    String picture;
    String uid;
    String name;
    String questionId;
    Uri photoUrl;
    Button btnComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
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




        CommentListAdapter commentListAdapter = new CommentListAdapter(getApplicationContext(),R.layout.layout_comment,comments);
        listView.setAdapter(commentListAdapter);

        mRef = FirebaseDatabase.getInstance().getReference("QUESTIONS");
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
}
