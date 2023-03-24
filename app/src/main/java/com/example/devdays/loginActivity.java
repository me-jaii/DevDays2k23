package com.example.devdays;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginBtn;
    private TextView loginQn;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        loginQn = findViewById(R.id.loginQn);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        loginQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();


                if(TextUtils.isEmpty(emailString)){
                    email.setError("Email is Required !!");
                }
                if(TextUtils.isEmpty(passwordString)) {
                    password.setError("Password is Required !!");
                }
                else{
                    progressDialog.setMessage("LogIn in Progress");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(loginActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(loginActivity.this,DisplayActivity.class);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                            }
                            else {
                                Toast.makeText(loginActivity.this,"Username or Password incorrect", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }

            }
        });


    }
}
