package com.example.homeauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    TextView Already_a_member;

    Button btn_signup;
    EditText edt_email, edt_password, et_name;
    String name,email,password;
    ProgressBar pb;


    FirebaseAuth firebaseAuth;

    FirebaseDatabase rootNode;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_signup = (Button)findViewById(R.id.signup);
        Already_a_member = (TextView)findViewById(R.id.tv_alreadyAMember);

        edt_email = (EditText) findViewById(R.id.id_email);
        et_name = (EditText) findViewById(R.id.id_name);
        edt_password = (EditText) findViewById(R.id.id_passsword);
        pb = findViewById(R.id.pb);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                name = et_name.getText().toString().trim();
                email = edt_email.getText().toString().trim();
                password = edt_password.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(Signup.this, "Please Enter an name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(Signup.this, "Please Enter a email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(Signup.this, "Password is too password", Toast.LENGTH_SHORT).show();
                }


                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    pb.setVisibility(View.GONE);
                                    UserHelper userHelper=new UserHelper(name,email,password);
                                    reference.child(firebaseAuth.getCurrentUser().getUid()).setValue(userHelper).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                pb.setVisibility(View.GONE);
                                                startActivity(new Intent(getApplicationContext(), Login.class));
                                                Toast.makeText(Signup.this, "Record Save", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Signup.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            pb.setVisibility(View.GONE);
                                        }
                                    });

                                }

                                // ...
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                });

            }
        });

    }

}