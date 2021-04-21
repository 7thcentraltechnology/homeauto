package com.example.homeauto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Lights extends AppCompatActivity {
    Switch bedroom_bulb_btn;
    ImageView bed_bulb;

    private static String MY_PREF = "switch_prefs";
    private static String Light_Status = "light_on";
    private static String Switch_Status = "switch_state";

    boolean switch_status;
    boolean light_status;

    SharedPreferences myref;
    SharedPreferences.Editor myeditor;

    FirebaseAuth firebaseAuth,fauth;
    DatabaseReference reference;

    String Userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);

        firebaseAuth = FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("Lights");

        fauth = FirebaseAuth.getInstance();
        Userid = fauth.getCurrentUser().getUid();
        FirebaseUser user=fauth.getCurrentUser();

        bedroom_bulb_btn = findViewById(R.id.bedroom_bulb);
        bed_bulb = findViewById(R.id.bed_bulb);

        myref = getSharedPreferences(MY_PREF,MODE_PRIVATE);
        myeditor = getSharedPreferences(MY_PREF,MODE_PRIVATE).edit();

        switch_status = myref.getBoolean(Switch_Status,false);
        light_status = myref.getBoolean(Light_Status, false);

        bedroom_bulb_btn.setChecked(switch_status);

        if (light_status){
            bed_bulb.setImageResource(R.drawable.onbulb);
            reference.child(Userid).child("Bedroom").setValue("ON");
        }else{
            bed_bulb.setImageResource(R.drawable.offbulb);
            reference.child(Userid).child("Bedroom").setValue("OFF");

        }

        bedroom_bulb_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    reference.child(Userid).child("Bedroom").setValue("ON");
                    bed_bulb.setImageResource(R.drawable.onbulb);
                    myeditor.putBoolean(Switch_Status,true);
                    myeditor.putBoolean(Light_Status,true);
                    myeditor.apply();
                    bedroom_bulb_btn.setChecked(true);
                }else{
                    reference.child(Userid).child("Bedroom").setValue("OFF");
                    bed_bulb.setImageResource(R.drawable.offbulb);
                    myeditor.putBoolean(Switch_Status,false);
                    myeditor.putBoolean(Light_Status,false);
                    myeditor.apply();
                    bedroom_bulb_btn.setChecked(false);
                }
            }
        });
    }
}