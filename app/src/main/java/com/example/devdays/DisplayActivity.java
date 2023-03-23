package com.example.devdays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "Display";
    String allData="";
    FirebaseFirestore mstore;
    CollectionReference mcollection;
    TextView objectTextView;

    private TextView quantityValueTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

//        objectTextView=findViewById(androidx.core.R.id.text);
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

