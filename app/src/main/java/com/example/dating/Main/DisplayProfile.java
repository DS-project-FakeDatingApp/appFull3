package com.example.dating.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dating.R;



public class DisplayProfile extends AppCompatActivity {

    private static String name;

    private Context mContext;
    String profileImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayprofile);

        mContext = DisplayProfile.this;

        TextView profileName = findViewById(R.id.name_main);
        ImageView profileImage = findViewById(R.id.profileImage);
        final TextView profileDistance = findViewById(R.id.distance_main);
        TextView profileBio = findViewById(R.id.bio_beforematch);
        TextView profileInterest = findViewById(R.id.HerInterest);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        String bio = intent.getStringExtra("bio");
        String interest = intent.getStringExtra("interest");
        int distance = intent.getIntExtra("distance", 1);
        String append = (distance == 1) ? "mile away" : "miles away";


        profileDistance.setText(distance + " " + append);
        profileName.setText(name);
        profileBio.setText(bio);
        profileInterest.setText(interest);

        profileImageUrl = intent.getStringExtra("photo");
        switch (profileImageUrl) {
            case "defaultFemale":
                Glide.with(mContext).load(R.drawable.default_woman).into(profileImage);
                break;
            case "defaultMale":
                Glide.with(mContext).load(R.drawable.default_man).into(profileImage);
                break;
            default:
                Glide.with(mContext).load(profileImageUrl).into(profileImage);
                break;
        }
    }




}
