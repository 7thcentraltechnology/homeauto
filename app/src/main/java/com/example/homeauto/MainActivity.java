package com.example.homeauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String EMAIL = "email";

    FirebaseAuth firebaseAuth,fauth;
    Button btn_cp;
    FirebaseUser firebaseUser;
    TextView textView,name_tv;
    String passget;
    EditText  pass;

    String Userid;

    CardView fans_activity,water_activity,manage_user,logout;

    LinearLayout Light_control;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.uemailtv);
        name_tv = findViewById(R.id.nametv);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        textView.setText(firebaseUser.getEmail());
        name_tv.setText(firebaseUser.getDisplayName());


        Light_control = findViewById(R.id.light_control);
        fans_activity = findViewById(R.id.fans_activity);
        water_activity = findViewById(R.id.water_activity);
        manage_user = findViewById(R.id.manage_user);
        logout = findViewById(R.id.logout);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        fauth = FirebaseAuth.getInstance();
        Userid = fauth.getCurrentUser().getUid();
        FirebaseUser user=fauth.getCurrentUser();

        Light_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Lights.class);
                startActivity(intent);
            }
        });
        fans_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(MainActivity.this,Fans.class);
                startActivity(a);
            }
        });
        water_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(MainActivity.this,WaterTank.class);
                startActivity(c);
            }
        });
        manage_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d=new Intent(MainActivity.this,Manage_User.class);
                startActivity(d);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Student").child(Userid).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    name_tv.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:

                return true;
            case R.id.changepassword:
                Intent in=new Intent(MainActivity.this,Update_password.class);
                startActivity(in);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}