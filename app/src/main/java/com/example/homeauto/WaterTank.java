package com.example.homeauto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class WaterTank extends AppCompatActivity {
    TextView tv_timer;
    Switch btn_start;

    public int counter;
    Button button;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tank);

        tv_timer = findViewById(R.id.tv_timer);
        btn_start = findViewById(R.id.tank_btn);

        button= (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new CountDownTimer(a, 1000){
                    public void onTick(long millisUntilFinished){
                        tv_timer.setText(String.valueOf(counter));
                        counter++;
                    }
                    public  void onFinish(){
                        tv_timer.setText("FINISH!!");
                    }
                }.start();
            }
        });

    }
}