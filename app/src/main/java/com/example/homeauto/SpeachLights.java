package com.example.homeauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SpeachLights extends AppCompatActivity {
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speach_lights);

        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        textView = findViewById(R.id.textView);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Toast.makeText(SpeachLights.this, ""+params, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onBeginningOfSpeech() {
            }
            @Override
            public void onRmsChanged(float rmsdB) {
            }
            @Override
            public void onBufferReceived(byte[] buffer) {
            }
            @Override
            public void onEndOfSpeech() {
            }
            @Override
            public void onError(int error) {
            }
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                String string = "";
                textView.setText("");
                if (matches != null){
                    string = matches.get(0);
                    textView.setText(string);
                    if(string.equals("light off")){
                        String ip = "192.168.137.186";
                        new RequestTask().execute("http://192.168.18.198/1/off");
                        textToSpeech.speak("bedroom light is of successfully on"+"Against Your System Ip"+ip, TextToSpeech.QUEUE_FLUSH, null, null);

                    }

                    if (string.equals("light on")){
                        String ip = "192.168.137.186";

                        new RequestTask().execute("http://192.168.18.198/1/on");
                        textToSpeech.speak("bedroom light is on successfully on Against Your System Ip"+ip, TextToSpeech.QUEUE_FLUSH, null, null);
//                        createMethod();
                    }
                }
            }
            @Override
            public void onPartialResults(Bundle partialResults) {
            }
            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        });
    }

    public void startButton(View view){
        textToSpeech.speak("Please tell me, how can I help you?", TextToSpeech.QUEUE_FLUSH, null, null);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        speechRecognizer.startListening(intent);
    }

    private void createMethod(){
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "PersonalAssistant.txt");
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append("My 1st Personal voice assistance App development");
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        textToSpeech.speak("The text file has been created. Thank you for using my service.", TextToSpeech.QUEUE_FLUSH, null, null);
    }
}