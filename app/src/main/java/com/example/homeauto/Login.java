package com.example.homeauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String EMAIL = "email";
    public static final String STATUS = "status";
    private boolean status;


    TextView tv_forget_password;
    Button btn_login;
    TextView signup;
    EditText edt_email, edt_password;
    ProgressBar pb;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_login = (Button) findViewById(R.id.login);
        edt_email = (EditText) findViewById(R.id.id_email);
        edt_password = (EditText) findViewById(R.id.id_passsword);

        tv_forget_password = findViewById(R.id.fg);
        signup = findViewById(R.id.singuptv);

        pb = findViewById(R.id.pb);


        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        status = sharedPreferences.getBoolean(STATUS, false);
        if (status){
            finish();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                String emailInput = edt_email.getText().toString().trim();
                String inputPassword = edt_password.getText().toString().trim();

                if (TextUtils.isEmpty(emailInput)) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Please Enter an Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(inputPassword)) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inputPassword.length() < 6) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Password is too Short", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(emailInput, inputPassword)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    pb.setVisibility(View.GONE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(EMAIL, emailInput);
                                    editor.putBoolean(STATUS, true);
                                    editor.apply();
                                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);

                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                });

            }

        });
        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Forget.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

    }
}