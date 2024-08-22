package com.example.arduinotestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joystick.JoystickView;

public class joystickActivity extends AppCompatActivity {


    /*
    * 로봇의 하단 servo부터 s1 ~ s6
    * */
    boolean gripSw = false; // grip 스위치 [s6 grip servo]

    // 왼쪽 조이스틱
    int joystick01_X = 0;  // s4   wrist_rotation servo
    int joystick01_Y = 0;  // s5   wrist servo

    // 오른쪽 조이스틱
    int joystick02_X = 0;  // s3   elbow servo
    int joystick02_Y = 0;  // s2  shoulder servo


    JoystickView joystickView01;
    JoystickView joystickView02;
    Button leftTurn;
    Button rigthTurn;
    Button grip;
    Button teaching;

    TextView joystick01_Angle;
    TextView joystick01_Strength;
    TextView joystick02_Angle;
    TextView joystick02_Strength;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        joystickView01 = findViewById(R.id.joystick01);
        joystickView02 = findViewById(R.id.joystick02);

        leftTurn = (Button)findViewById(R.id.LeftTurn);
        rigthTurn = (Button)findViewById(R.id.RightTurn);
        grip = (Button)findViewById(R.id.Grip);
        teaching = (Button)findViewById(R.id.TeachingMode);

        joystick01_Angle = (TextView)findViewById(R.id.textView01_angle);
        joystick01_Strength = (TextView)findViewById(R.id.textView01_strength);
        joystick02_Angle = (TextView)findViewById(R.id.textView02_angle);
        joystick02_Strength = (TextView)findViewById(R.id.textView02_strength);

        joystickView01.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                joystick01_Angle.setText("angle: " + angle);
                joystick01_Strength.setText("strength: " + strength);

                // x, y 값 블루투스 통해 송신

            }
        },100);
        joystickView02.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                joystick02_Angle.setText("angle: " + angle);
                joystick02_Strength.setText("strength: " + strength);

                // x, y 값 블루투스 통해 송신

            }
        },100);

        leftTurn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        rigthTurn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        grip.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(gripSw){ // 잡기 상태

                }else{  // 놓기 상태

                }
            }
        });

        teaching.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });




    }

}
