package com.example.dating.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dating.Main.LoginActivity;
import com.example.dating.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.Document;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.example.dating.Utils.TopNavigationViewHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Profile_Activity extends AppCompatActivity {
    private static final String TAG = "Profile_Activity";
    private static final int ACTIVITY_NUM = 0;
    static boolean active = false;

    private Context mContext = Profile_Activity.this;

    //get instance of firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");
    DatabaseReference usersRef = ref.child("users");
    GoogleSignInClient mGoogleSignInClient;

    String currentUser;

    ListView listview;

    //private ArrayAdapter<String> adapter;
    private EditText edittxt;

    Button add1, add2, add3, add4, add5, save, logout;
    Spinner gender;
    EditText getabout, displayName;

    private static ArrayList<String> interestslist = new ArrayList<String>();
    ArrayAdapter myAdapter;
    private EditText interest;

    String[] listitems;
    String[] listitems2;
    String[] listitems3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: create the page");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        setupTopNavigationView();

        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        add5 = findViewById(R.id.add5);
        save = findViewById(R.id.saveinfo);
        logout = findViewById(R.id.logout);

        getabout = findViewById(R.id.editabout);
        displayName = findViewById(R.id.displayName);
        listview = findViewById(R.id.listview);
        interest = findViewById(R.id.interests);
        gender = findViewById(R.id.gender);

        String[] items = new String[]{"Male", "Female","Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, items);
        gender.setAdapter(adapter);

        add1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                one();
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                two();
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                three();
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                saveInfo();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {logout();}
        });

        myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, interestslist);
        listview.setAdapter(myAdapter);

        add5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String stringval = interest.getText().toString();
                interestslist.add(stringval);
                myAdapter.notifyDataSetChanged();

                interest.setText("");
            }
        });
    }

    public static ArrayList<String> getInterest() {
        return interestslist;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resume to the page");

        //TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener(){}

    }

//    public boolean onEditorAction(TextView interests, int actionId, KeyEvent event) {
//        if (actionId == EditorInfo.IME_NULL
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            example_confirm();//match this behavior to your 'Send' (or Confirm) button
//        }
//        return true;
//    }

    public void one(){
        listitems = new String[]{"Men", "Women", "Both"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile_Activity.this);
        mBuilder.setTitle("Choose one");
        mBuilder.setIcon(R.drawable.ic_launcher);

        mBuilder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                add1.setText(listitems[i]);
                dialogInterface.dismiss();

            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();


    }

    public void two(){
        listitems2 = new String[]{"Single", "In a relationship", "Married", "Divorced",
                "It's Complicated", "Not Telling"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile_Activity.this);
        mBuilder.setTitle("Choose one");
        mBuilder.setIcon(R.drawable.ic_launcher);

        mBuilder.setSingleChoiceItems(listitems2, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                add2.setText(listitems2[i]);
                dialogInterface.dismiss();

            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public void three(){
        listitems3 = new String[]{"Friendship", "Partner", "Marriage", "Just For Fun"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile_Activity.this);
        mBuilder.setTitle("Choose one");
        mBuilder.setIcon(R.drawable.ic_launcher);

        mBuilder.setSingleChoiceItems(listitems3, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                add3.setText(listitems3[i]);
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    private void setupTopNavigationView() {
        Log.d(TAG, "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    //saves user info into the firestore. Document being added is referenced using user uid
    private void saveInfo(){
        UserInfo userinfo = new UserInfo(displayName.getText().toString(),gender.getSelectedItem()
                .toString(),getabout.getText()
                .toString(), add2.getText().toString(), add1.getText().toString(), add3.getText()
                .toString(),interestslist);
        db.collection("users")
                .document(currentUser)
                .set(userinfo)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        Toast.makeText(Profile_Activity.this, "Error Updating " +
                                        "Information", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
        Toast.makeText(Profile_Activity.this, "Successfully Updated",
                Toast.LENGTH_SHORT).show();
    }

    private void logout(){
            FirebaseAuth.getInstance().signOut();
            mGoogleSignInClient.signOut();
            Intent intent = new Intent(Profile_Activity.this, LoginActivity.class);
            startActivity(intent);
    }

}
