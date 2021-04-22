package com.example.homeauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_User extends AppCompatActivity {
    EditText et_name,et_email,et_phone,et_password;
    String get_name,get_email,get_phone,get_password;
    Button btn_reg;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__user);

        et_name = findViewById(R.id.user_name_et);
        et_email = findViewById(R.id.user_email_et);
        et_phone = findViewById(R.id.user_phone_et);
        et_password = findViewById(R.id.user_password_et);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_reg = findViewById(R.id.user_reg_btn);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_name = et_name.getText().toString().trim();
                get_email = et_email.getText().toString().trim();
                get_phone = et_phone.getText().toString().trim();
                get_password = et_password.getText().toString().trim();
                SubUserHelper subUserHelper = new SubUserHelper(get_name,get_email,get_phone,get_password);
                reference.child(firebaseAuth.getCurrentUser().getUid()).child("sub_user").push().setValue(subUserHelper)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Create_User.this, "Sub user Create succefully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Create_User.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }
}