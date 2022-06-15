package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainLogin extends AppCompatActivity {

    private Button btn_login_signup, btn_login;
    private EditText txt_email, txt_password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        btn_login_signup = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login_page);
        txt_email= findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainLogin.this, MainSignup.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String adminEmail = txt_email.getText().toString();
                String adminPassword = txt_password.getText().toString();

                if (adminEmail.isEmpty() && adminPassword.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Incorrect Admin Credentials", Toast.LENGTH_SHORT).show();
                } else if (adminEmail.equals("admin") && adminPassword.equals("admin")) {

                    Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainLogin.this, AdminMainActivity.class));
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(adminEmail, adminPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login Sucessful!", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(MainLogin.this, MemberMain.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed To Load", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}