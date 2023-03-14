package com.example.devdays;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button addButton = findViewById(R.id.addNoteButton);
        EditText titleText = findViewById(R.id.titleEditText);
        EditText  descriptionText=findViewById(R.id.descriptionEditText);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleText.getText().toString();
                String description = descriptionText.getText().toString();

                Map<String, Object> data = new HashMap<>();
                data.put("title", title);
                data.put("description", description);

                db.collection("users").document()
                        .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Firestore Updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //progressDialog.dismiss();
                                } else {
                                    Toast.makeText(MainActivity.this, "Updation Failed", Toast.LENGTH_SHORT).show();
                                    //progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Set", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}