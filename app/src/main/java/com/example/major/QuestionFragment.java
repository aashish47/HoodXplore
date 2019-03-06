package com.example.major;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class QuestionFragment extends Fragment implements LocationListener {

    TextView locationText;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    Uri photoUrl;
    ImageView ivImage;
    String name;
    Button btnAsk;
    LocationManager locationManager;
    EditText etQues;
    DatabaseReference mDatabase;
    String locationLocal;
    Double latitude;
    Double longitude;
    String picture;



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAsk = view.findViewById(R.id.btnAsk);
        etQues = view.findViewById(R.id.etQues);
        ivImage = view.findViewById(R.id.ivImage);
        mDatabase = FirebaseDatabase.getInstance().getReference("QUESTIONS");
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                photoUrl = profile.getPhotoUrl();
                picture = photoUrl.toString();
            }
        }
        Picasso.get().load(photoUrl).into(ivImage);

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    try {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        locationText = (TextView)view.findViewById(R.id.locationText);
        locationText.setText(name);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        getLocation();

        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQues();
                etQues.setText("");
            }
        });

    }


    public void addQues(){
        String ques = etQues.getText().toString();
        if(!TextUtils.isEmpty(ques)){
            String id = mDatabase.push().getKey();
            Question question = new Question(id,ques,locationLocal,latitude,longitude,name,picture);
            mDatabase.child(id).setValue(question);
            Toast.makeText(getContext(),"Added to database",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),"Enter value",Toast.LENGTH_LONG).show();
        }
    }



    void getLocation() {
        try {
            locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    public void onLocationChanged(Location location) {
//        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address obj = addresses.get(0);
            locationText.setText(name);
            locationText.setText(locationText.getText() + "\n" + obj.getSubLocality());
            locationLocal = obj.getSubLocality();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }catch(Exception e)
        {

        }

    }

    public void onProviderDisabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String provider) {

    }
}
