package com.example.devdays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Display extends AppCompatActivity {
    private static final String TAG = "Display";

    FirebaseFirestore objectFirebaseFirestore;
    CollectionReference objectCollectionReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView objectTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        objectTextView=findViewById(R.id.getValues);
        try {
            objectFirebaseFirestore=FirebaseFirestore.getInstance();
            objectCollectionReference =objectFirebaseFirestore.collection("users");
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    String allData="";
    public void getvalues(View view)
    {
        try {
            objectCollectionReference.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot objectDocumentSnapshot : queryDocumentSnapshots) {
                                String userName = objectDocumentSnapshot.getString("name");
                                String userEmail = objectDocumentSnapshot.getString("email_id");
                                String userMobile = objectDocumentSnapshot.getString("mobile_no");
                                String userDOB = objectDocumentSnapshot.getString("date_of_birth");

                                allData = "Name:" + userName + "\n" + "Email:" + userEmail + "\n" + "Mobile:" + userMobile + "DOB" + "\n" + userDOB + "\n";
                            }

                            objectTextView.setText(allData);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Display.this, "Fails to retrive data", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
