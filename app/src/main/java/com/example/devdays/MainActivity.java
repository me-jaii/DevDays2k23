package com.example.devdays;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String title, email, mobileno, dateofbirth;
//    CollectionReference usersRef = db.collection("users");


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Object User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText titleText = findViewById(R.id.name);
        EditText descriptionText = findViewById(R.id.emailid);
        EditText mobile = findViewById(R.id.phnum);
        EditText dob = findViewById(R.id.dob);
        Button calander = findViewById(R.id.calander);
        Button addButton = findViewById(R.id.addNoteButton);




//        db.collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
        calander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                final Calendar cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener dl = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = dayOfMonth + " / " + (monthOfYear + 1) + " / " + year;
                        dob.setText(editTextDateParam);
                        Log.d("BIMI", editTextDateParam);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, dl, mYear, mMonth, mDay);

                datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button pButton = datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        pButton.setText("Set date");
                    }
                });

                datePickerDialog.show();
            }
        });


//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String title = titleText.getText().toString();
//                String description = descriptionText.getText().toString();
//
//                Map<String, Object> data = new HashMap<>();
//                data.put("title", title);
//                data.put("description", description);
//
//                db.collection("users").document()
//                        .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(MainActivity.this, "Firestore Updated", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                    //progressDialog.dismiss();
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Updation Failed", Toast.LENGTH_SHORT).show();
//                                    //progressDialog.dismiss();
//                                    Toast.makeText(getApplicationContext(), "Set", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
////                displaybtn.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Intent i = new Intent(MainActivity.this,display.class);
////                        startActivity(i);
////                    }
////                });
//
//            }
//        });
//    }
//    }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // getting data from edittext fields.
                title = titleText.getText().toString();
                email = descriptionText.getText().toString();
                mobileno = mobile.getText().toString();
                dateofbirth = dob.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    titleText.setError("Please enter Course Name");
                } else if (TextUtils.isEmpty(email)) {
                    descriptionText.setError("Please enter Course Description");
                } else if (TextUtils.isEmpty(mobileno)) {
                    mobile.setError("Please enter Course Duration");
                } else if (TextUtils.isEmpty(dateofbirth)) {
                    dob.setError("Please enter Course Duration");
                } else {
                    addDataToFirestore(title, email, mobileno, dateofbirth);
                }
            }
        });
    }
    private void addDataToFirestore(String title, String email, String mobileno, String dateofbirth) {
        CollectionReference dbusers = db.collection("users");
        User users = new User(title, email, mobileno, dateofbirth);
        dbusers.add(User).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MainActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}