package com.example.devdays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "Display";
    String allData="";
    FirebaseFirestore mstore;
    CollectionReference mcollection;
    ListView listView;
    TextView textView;
    String[] arraylist;

    private TextView quantityValueTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        listView=(ListView)findViewById(R.id.listviewm);
        textView=(TextView)findViewById(R.id.textView);
        arraylist = getResources().getStringArray(R.array.array_technology);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, arraylist);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.actionbtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mstore=FirebaseFirestore.getInstance();
        mcollection =mstore.collection("users");
        mcollection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot objectDocumentSnapshot : queryDocumentSnapshots) {
                            User user = objectDocumentSnapshot.toObject(User.class);
//                            String userName = objectDocumentSnapshot.getString("name");
//                            String userEmail = objectDocumentSnapshot.getString("email_id");
//                            String userMobile = objectDocumentSnapshot.getString("mobile_no");
//                            String userDOB = objectDocumentSnapshot.getString("date_of_birth");
                            System.out.println(user);

//                            allData = "Name: " + userName + "\n" + "Email: " + userEmail + "\n" + "Mobile: " + userMobile +"\n" + "DOB: " +  userDOB + "\n";
                        }

//                        objectTextView.setText(allData);

                    }
                });

    }
}
