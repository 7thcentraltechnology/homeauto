package com.example.homeauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Manage_User extends AppCompatActivity {
    CardView new_user_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__user);

        new_user_c = findViewById(R.id.new_user);
        new_user_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cretae_new_user = new Intent(Manage_User.this,Create_User.class);
                startActivity(cretae_new_user);
            }
        });

    }
}