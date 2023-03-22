package com.example.devdays;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseFirestore objectFirebaseFirestore;
    CollectionReference objaectCollectioinReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView objectTextView;
    DocumentReference dUser = db.collection("users").document("admins");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        EditText titleText = findViewById(R.id.name);
        EditText descriptionText = findViewById(R.id.emailid);
        EditText phonenum = findViewById(R.id.phnum);
        EditText dateofbirth = findViewById(R.id.dob);
        Button calander = findViewById(R.id.calander);
        Button addButton = findViewById(R.id.addNoteButton);
        Button displaybtn = findViewById(R.id.Displaycollection);

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
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
                        dateofbirth.setText(editTextDateParam);
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

        displaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Display.class);
                startActivity(intent);
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleText.getText().toString();
                String description = descriptionText.getText().toString();
                String phnum = phonenum.getText().toString();
                String dob = dateofbirth.getText().toString();


                Map<String, Object> data = new HashMap<>();
                data.put("name", title);
                data.put("email_id", description);
                data.put("mobile_no", phnum);
                data.put("date_of_birth", dob);

                db.collection("users").document()
                        .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Firestore Updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Updation Failed", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Set", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

