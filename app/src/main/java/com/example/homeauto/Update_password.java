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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Update_password extends AppCompatActivity {
    FirebaseAuth firebaseAuth,fauth;
    Button btn_cp;
    FirebaseUser firebaseUser;
    TextView textView;
    String passget;
    EditText pass;
    DatabaseReference reference;

    String Userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);


        pass = (EditText) findViewById(R.id.id_pc);


        firebaseAuth = FirebaseAuth.getInstance();
        btn_cp= (Button) findViewById(R.id.cp);

        reference= FirebaseDatabase.getInstance().getReference("Student");


        fauth = FirebaseAuth.getInstance();
        Userid = fauth.getCurrentUser().getUid();
        FirebaseUser user=fauth.getCurrentUser();

        btn_cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passget=pass.getText().toString().trim();
                user.updatePassword(passget).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        reference.child(Userid).child("password").setValue(passget);

                        Toast.makeText(Update_password.this, "Password Change Successfully", Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(Update_password.this,MainActivity.class);
                        startActivity(in);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Update_password.this, "failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}