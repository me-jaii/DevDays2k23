package com.example.devdays;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String allData="";
    FirebaseFirestore mstore;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String userEmail = mUser.getEmail();
    CollectionReference mcollection;
    ListView listView;
    TextView textView;
    ArrayList<String> arraylist;
    ImageButton logout;
    FloatingActionButton fab;

    ArrayAdapter<String> adapter;

    private TextView quantityValueTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listviewm);
        textView=(TextView)findViewById(R.id.textView);
        logout = findViewById(R.id.logout);
        arraylist = new ArrayList<>();


        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, arraylist);
        listView.setAdapter(adapter);


        fab = findViewById(R.id.actionbtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mstore=FirebaseFirestore.getInstance();
        mcollection =mstore.collection("users").document(userEmail).collection("Items");
        mcollection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot objectDocumentSnapshot : queryDocumentSnapshots) {
                            User user = objectDocumentSnapshot.toObject(User.class);

                            arraylist.add(user.toString());

                        }

                        adapter.notifyDataSetChanged();

                    }
                });

    }
}
