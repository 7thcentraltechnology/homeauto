package com.example.homeauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    TextView Already_a_member;

    Button btn_signup;
    EditText edt_email, edt_password;
    String email,password;


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
        edt_password = (EditText) findViewById(R.id.id_passsword);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               email = edt_email.getText().toString().trim();
               password = edt_password.getText().toString().trim();


//               rootNode = FirebaseDatabase.getInstance();
//               reference = rootNode.getReference("User");
//               UserHelper userHelper = new UserHelper(email,password);
//               reference.child(email).setValue(userHelper);

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    UserHelper userHelper=new UserHelper(email,password);
                                    reference.child(firebaseAuth.getCurrentUser().getUid()).setValue(userHelper).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Signup.this, "Record Save", Toast.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(Signup.this, "not record", Toast.LENGTH_SHORT).show();                                   Toast.makeText(Signup.this, "Registeration Successfull", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                } else {
                                    Toast.makeText(Signup.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });

    }

}