package com.example.devdays;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseFirestore objectFirebaseFirestore;
    CollectionReference objaectCollectioinReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView objectTextView;
    DocumentReference dUser = db.collection("users").document("admins");

    SeekBar seekbar;
    TextView Text_message;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String selectedText = "";
    private String selectedItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        EditText itemName = findViewById(R.id.item_name);
        EditText itemID = findViewById(R.id.item_ID);
        EditText quantity = findViewById(R.id.quantity);
        EditText purchaseDate = findViewById(R.id.purchase_date);
        Spinner spinner = findViewById(R.id.my_spinner);
        Button calander = findViewById(R.id.calander);
        Button addButton = findViewById(R.id.addNoteButton);
        radioGroup = findViewById(R.id.radio_group);

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
                        purchaseDate.setText(editTextDateParam);
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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Item_name = itemName.getText().toString();
                String Item_ID = itemID.getText().toString();
                String Quantity = quantity.getText().toString();
                String PurchaseDate = purchaseDate.getText().toString();

                User user = new User(Item_ID , Item_name , Quantity , PurchaseDate );


                db.collection("users").document()
                        .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this, "Firestore Updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(MainActivity.this, "Updation Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
        Text_message = (TextView)findViewById(R.id.quantity);
        seekbar = (SeekBar)findViewById(R.id.seekBar);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
                Text_message.setText(String.valueOf(progress + 1));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        });
    }
}

