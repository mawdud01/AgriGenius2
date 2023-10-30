package com.example.agrigenius;


import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agrigenius.settings.setting;

import java.util.Locale;

public class tutorial extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private TextView textToSpeak;
    private Button startButton;
    private Button stopButton;
    private Locale selectedLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        textToSpeak = findViewById(R.id.textToSpeak);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        // Initialize TextToSpeech with the default language (English)
        selectedLocale = Locale.US;
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(selectedLocale);

                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language not supported or missing data.
                } else {
                    startButton.setEnabled(true);
                }
            }
        });

        startButton.setOnClickListener(view -> {
            String text = textToSpeak.getText().toString();
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            stopButton.setVisibility(View.VISIBLE);
            startButton.setEnabled(false);
        });

        stopButton.setOnClickListener(view -> {
            if (textToSpeech.isSpeaking()) {
                textToSpeech.stop();
            }
            stopButton.setVisibility(View.GONE);
            startButton.setEnabled(true);
        });
    }

    // Method to change the language to Bangla
    private void setBanglaLanguage() {
        selectedLocale = new Locale("bn_BD"); // Bangla locale
        textToSpeech.setLanguage(selectedLocale);
    }

    // Method to change the language to English
    private void setEnglishLanguage() {
        selectedLocale = Locale.US; // English locale
        textToSpeech.setLanguage(selectedLocale);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(tutorial.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}


