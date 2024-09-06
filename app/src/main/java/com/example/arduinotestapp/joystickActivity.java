package com.example.arduinotestapp;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joystick.JoystickView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class joystickActivity extends AppCompatActivity {

    String TAG = "JoystickActivity";


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



    // UUID
    UUID MY_CUSTOM_SERVICE_UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    UUID MY_CUSTOM_CHARACTERISTIC_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    private BluetoothGatt bluetoothGatt = GattSingleton.getInstance().getBluetoothGatt();
    private BluetoothGattService service = bluetoothGatt.getService(MY_CUSTOM_SERVICE_UUID);
    private BluetoothGattCharacteristic characteristic = service.getCharacteristic(MY_CUSTOM_CHARACTERISTIC_UUID);


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
                String header = "";

                if(angle != 350){
                    header = "s4-";
                    header += Integer.toString(angle);
                    byte[] data = header.getBytes();


                    sendCommand(data);
                }

                if(strength != 350){
                    header = "s5-";
                    header += Integer.toString(strength);
                    byte[] data = header.getBytes();

                    sendCommand(data);
                }


            }
        },100);
        joystickView02.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                joystick02_Angle.setText("angle: " + angle);
                joystick02_Strength.setText("strength: " + strength);

                // x, y 값 블루투스 통해 송신

                String header = "";

                if(angle != 350){
                    header = "s3-";
                    header += Integer.toString(angle);
                    byte[] data = header.getBytes();

                    sendCommand(data);
                }

                if(strength != 350){
                    header = "s2-";
                    header += Integer.toString(strength);
                    byte[] data = header.getBytes();

                    sendCommand(data);
                }

            }
        },500);

        leftTurn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String header = "s1-";
                header += "450";
                byte[] data = header.getBytes();

                sendCommand(data);
            }
        });

        rigthTurn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String header = "s1-";
                header += "250";
                byte[] data = header.getBytes();

                sendCommand(data);
            }
        });

        grip.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String header = "s6-";
                if(gripSw){ // 잡기 상태
                    header += "1";
                }else{  // 놓기 상태
                    header += "0";
                }

                byte[] data = header.getBytes();
                sendCommand(data);
            }
        });

        teaching.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });




    }

    public void sendCommand(byte[] command){
        Log.d(TAG,"sendCommand");
        if (characteristic != null) {
            characteristic.setValue(command);
            boolean success = bluetoothGatt.writeCharacteristic(characteristic);
            if (!success) {
                Log.e(TAG, "Failed to write characteristic");
            }
        } else {
            Log.e(TAG, "Characteristic is null, cannot send command");
        }
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d("JoystickActivity", "Write successful");
            } else {
                Log.e("JoystickActivity", "Write failed with status: " + status);
            }
        }
    };


}
