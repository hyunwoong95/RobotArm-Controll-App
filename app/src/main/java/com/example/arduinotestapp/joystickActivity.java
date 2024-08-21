package com.example.arduinotestapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joystick.JoystickView;

public class joystickActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        JoystickView joystickView = findViewById(R.id.joystick);

    }

}
