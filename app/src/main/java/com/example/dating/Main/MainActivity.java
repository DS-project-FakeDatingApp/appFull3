package com.example.dating.Main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dating.Profile.Profile_Activity;
import com.example.dating.Profile.UserInfo;
import com.example.dating.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.example.dating.Utils.PulsatorLayout;
import com.example.dating.Utils.TopNavigationViewHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 1;
    final private int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    List<Cards> rowItems;
    FrameLayout cardFrame, moreFrame;
    private Context mContext = MainActivity.this;
    private NotificationHelper mNotificationHelper;
    private PhotoAdapter arrayAdapter;
    private ArrayList<Cards> naksusun;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cardFrame = findViewById(R.id.card_frame);
        moreFrame = findViewById(R.id.more_frame);
        // start pulsator
        PulsatorLayout mPulsator = findViewById(R.id.pulsator);
        mPulsator.start();
        mNotificationHelper = new NotificationHelper(this);


        setupTopNavigationView();

        rowItems = new ArrayList<Cards>();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                UserInfo user = document.toObject(UserInfo.class);
                                Cards cards;
                                    cards = new Cards(user.getName(), 21,
                                            "https://images.freeimages.com/images/large-previews/b40/girl-on-phone-1401433.jpg",
                                            user.getBio(), user.getInterests().get(0), 200,
                                            0);

                                    similarinterest(cards);
                                    rowItems.add(cards);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Cards cards = new Cards("Anne", 20, "https://images.freeimages.com/images/large-previews/b40/girl-on-phone-1401433.jpg", "Hii", "Dancing", 800,0);
        similarinterest(cards);
        rowItems.add(cards);
        cards = new Cards( "Mira", 22, "https://images.freeimages.com/images/large-previews/e7f/girl-1431941.jpg", "Testing", "Singing", 400,0);
        similarinterest(cards);
        rowItems.add(cards);
        cards = new Cards( "Arisa", 19, "https://images.freeimages.com/images/large-previews/3da/girl-1311916.jpg", "Whats up", "Swimming", 1308,0);
        similarinterest(cards);
        rowItems.add(cards);
        cards = new Cards( "Alya", 20, "https://images.freeimages.com/images/large-previews/30f/girl-1438459.jpg", "Yo", "Drawing", 1200,0);
        similarinterest(cards);
        rowItems.add(cards);
        cards = new Cards( "Nina", 21, "https://images.freeimages.com/images/large-previews/7b6/girl-1438440.jpg", "Hi", "Sleeping", 1200,0);
        similarinterest(cards);
        rowItems.add(cards);
        cards = new Cards( "Minah", 19, "https://images.freeimages.com/images/large-previews/167/girl-in-the-garden-1431630.jpg", "Just trying", "Art", 5000,0);
        similarinterest(cards);
        rowItems.add(cards);

        Collections.sort(rowItems, Cards.dahsusun);

        arrayAdapter = new PhotoAdapter(this, R.layout.item, rowItems);

        checkRowItem();
        updateSwipeCard();
    }

    //Most similar interest will be displayed top
    private void similarinterest(Cards cards){
        ArrayList<String> userinterest = Profile_Activity.getInterest();
        int count=0;

        for(int i=0; i<userinterest.size(); i++) {

            if (cards.getInterest().equals(userinterest.get(i))) { //If other users interest = My interest, +1 to count.
                count++;
            }

        }

        cards.setSimilarinterest(count);
    }

    private void checkRowItem() {
        if (rowItems.isEmpty()) {
            moreFrame.setVisibility(View.VISIBLE);
            cardFrame.setVisibility(View.GONE);
        }
    }

    private void updateLocation() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        updateLocation();
                    } else {
                        Toast.makeText(MainActivity.this, "Location Permission Denied. You have to give permission inorder to know the user range ", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void updateSwipeCard() {
        final SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                checkRowItem();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;

                //check matches
                checkRowItem();

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here


            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
             //   view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
               // view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void sendNotification() {
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(mContext.getString(R.string.app_name), mContext.getString(R.string.match_notification));

        mNotificationHelper.getManager().notify(1, nb.build());
    }


    public void DislikeBtn(View v) {
        if (rowItems.size() != 0) {
            Cards card_item = rowItems.get(0);

            String userId = card_item.getUserId();

            rowItems.remove(0);
            arrayAdapter.notifyDataSetChanged();

        }
    }

    public void LikeBtn(View v) {
        if (rowItems.size() != 0) {
            Cards card_item = rowItems.get(0);

            String userId = card_item.getUserId();

            //check matches

            rowItems.remove(0);
            arrayAdapter.notifyDataSetChanged();


        }
    }


    /**
     * setup top tool bar
     */
    private void setupTopNavigationView() {
        Log.d(TAG, "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    @Override
    public void onBackPressed() {

    }


}
