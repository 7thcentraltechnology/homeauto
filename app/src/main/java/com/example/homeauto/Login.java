package com.example.homeauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {
    TextView tv_forget_password;
    Button btn_login;
    TextView tv_dont_have_acc;
    EditText edt_email, edt_password;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_login = (Button) findViewById(R.id.login);
        edt_email = (EditText) findViewById(R.id.id_email);
        edt_password = (EditText) findViewById(R.id.id_passsword);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailInput = edt_email.getText().toString().trim();
                String inputPassword = edt_password.getText().toString().trim();

                if (TextUtils.isEmpty(emailInput)) {
                    Toast.makeText(Login.this, "Please Enter an Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(inputPassword)) {
                    Toast.makeText(Login.this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inputPassword.length() < 6) {
                    Toast.makeText(Login.this, "Password is too Short", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(emailInput, inputPassword)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Login.this, "Login Failed OR User is not Registered", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

            }

        });


    }
}