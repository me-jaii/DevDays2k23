package com.example.devdays;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Member;
import java.time.Duration;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String userEmail = mUser.getEmail();
    DocumentReference dUser = db.collection("users").document(userEmail).collection("Items").document();
    SeekBar seekbar;
    TextView Text_message;
    RadioButton normal, cold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser.getEmail();

        EditText itemName = findViewById(R.id.item_name);
        TextView quantity = findViewById(R.id.quantity);
        Button addButton = findViewById(R.id.addNoteButton);
        normal = findViewById(R.id.normalst);
        cold = findViewById(R.id.coldst);
        Spinner mySpinner = findViewById(R.id.my_spinner);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Item_name = itemName.getText().toString();
                String Quantity = quantity.getText().toString();
                String Duration = mySpinner.getSelectedItem().toString();
                User user = new User(Item_name, Quantity, Duration);
                dUser.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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