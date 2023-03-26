package com.example.devdays;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class InputActivity extends AppCompatActivity {
    private static final String TAG = "InputActivity";
    SeekBar seekbar;
    TextView Text_message;
    EditText itemName;
    TextView quant;
    Button addButton;
    RadioGroup group;
    RadioButton normal, cold , sel;
    String nametxt, quanttitystr, userEmail;
    User user;
    Spinner mySpinner;
    DocumentReference dUser;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userEmail = mUser.getEmail();
        dUser = db.collection("users").document(userEmail).collection("Items").document();

        user = new User();
        itemName = findViewById(R.id.item_name);
        quant = findViewById(R.id.quantity);
        addButton = findViewById(R.id.addNoteButton);
        normal = findViewById(R.id.normalst);
        cold = findViewById(R.id.coldst);
        mySpinner = findViewById(R.id.my_spinner);
        group = findViewById(R.id.radio_group);
        normal = findViewById(R.id.normalst);
        cold = findViewById(R.id.coldst);
        normal = findViewById(R.id.normalst);
        cold = findViewById(R.id.coldst);
        normal.setSelected(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setItem_Name(itemName.getText().toString());
                user.setQuantity(quant.getText().toString());
                user.setDuration(mySpinner.getSelectedItem().toString());
                int selectedId = group.getCheckedRadioButtonId();
                sel = (RadioButton) findViewById(selectedId);



                nametxt = itemName.getText().toString();
                quanttitystr = quant.getText().toString();

                if(TextUtils.isEmpty(nametxt)){
                    itemName.setError("Item name is Required !!");
                    return;
                }

                if(TextUtils.isEmpty(quanttitystr)) {
                    quant.setError("Quantity is Required !!");
                    return;
                }

                if(selectedId==-1){
                    Toast.makeText(InputActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    user.setStorType(sel.getText().toString());
                }
                setData();
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

    private void setData(){

        dUser.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(InputActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(InputActivity.this, "Updation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}