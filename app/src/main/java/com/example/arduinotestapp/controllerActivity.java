package com.example.arduinotestapp;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class controllerActivity extends AppCompatActivity {
    String TAG = "controllerActivity";

    // UUID
    UUID MY_CUSTOM_SERVICE_UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    UUID MY_CUSTOM_CHARACTERISTIC_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    private BluetoothGatt bluetoothGatt = GattSingleton.getInstance().getBluetoothGatt();
    private BluetoothGattService service = bluetoothGatt.getService(MY_CUSTOM_SERVICE_UUID);
    private BluetoothGattCharacteristic characteristic = service.getCharacteristic(MY_CUSTOM_CHARACTERISTIC_UUID);

    SeekBar grip;
    SeekBar wristPitch;
    SeekBar wristRoll;
    SeekBar elbow;
    SeekBar shoulder;
    SeekBar waist;
    SeekBar speed;

    String command = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        grip = (SeekBar) findViewById(R.id.grip);
        wristPitch = (SeekBar) findViewById(R.id.wristPitch);
        wristRoll = (SeekBar) findViewById(R.id.wristRoll);
        elbow = (SeekBar) findViewById(R.id.elbow);
        shoulder = (SeekBar) findViewById(R.id.shoulder);
        waist = (SeekBar) findViewById(R.id.waist);
        speed = (SeekBar) findViewById(R.id.speed);

        grip.setProgress((180/100)*80);
        wristPitch.setProgress((180/100)*0);
        wristRoll.setProgress((180/100)*90);
        elbow.setProgress((180/100)*170);
        shoulder.setProgress((180/100)*170);
        waist.setProgress((180/100)*90);


        grip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 드레그 중 발생
                Log.d(TAG,"grip action: onProgressChanged");
                Log.d(TAG, String.format("onProgressChanged 값 변경 중 : progress [%d] fromUser [%b]", progress, fromUser));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 드레그 시작시 발생
                Log.d(TAG,"grip action: onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 드레그 멈추면 발생
                Log.d(TAG,"grip action: onStopTrackingTouch");

                command = null;
                command = "s6-" + seekBar.getProgress();
                byte[] data = command.getBytes();
                Send_BT(data);
            }
        });

        wristPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 드레그 멈추면 발생
                Log.d(TAG,"wristPitch action: onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 드레그 시작시 발생
                Log.d(TAG,"wristPitch action: onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 드레그 중 발생
                Log.d(TAG,"wristPitch action: onStopTrackingTouch");

                command = null;
                command = "s5-" + seekBar.getProgress();
                byte[] data = command.getBytes();
                Send_BT(data);
            }
        });

        wristRoll.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 드레그 멈추면 발생
                Log.d(TAG,"wristRoll action: onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 드레그 시작시 발생
                Log.d(TAG,"wristRoll action: onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 드레그 중 발생
                Log.d(TAG,"wristRoll action: onStopTrackingTouch");

                command = null;
                command = "s4-" + seekBar.getProgress();
                byte[] data = command.getBytes();
                Send_BT(data);
            }
        });

        elbow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 드레그 멈추면 발생
                Log.d(TAG,"elbow action: onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 드레그 시작시 발생
                Log.d(TAG,"elbow action: onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 드레그 중 발생
                Log.d(TAG,"elbow action: onStopTrackingTouch");

                command = null;
                command = "s3-" + seekBar.getProgress();
                byte[] data = command.getBytes();
                Send_BT(data);
            }
        });

        shoulder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 드레그 멈추면 발생
                Log.d(TAG,"shoulder action: onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 드레그 시작시 발생
                Log.d(TAG,"shoulder action: onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 드레그 중 발생
                Log.d(TAG,"shoulder action: onStopTrackingTouch");

                command = null;
                command = "s2-" + seekBar.getProgress();
                byte[] data = command.getBytes();
                Send_BT(data);
            }
        });

        waist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 드레그 멈추면 발생
                Log.d(TAG,"waist action: onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 드레그 시작시 발생
                Log.d(TAG,"waist action: onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 드레그 중 발생
                Log.d(TAG,"waist action: onStopTrackingTouch");

                command = null;
                command = "s1-" + seekBar.getProgress();
                byte[] data = command.getBytes();
                Send_BT(data);
            }
        });

        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 드레그 멈추면 발생
                Log.d(TAG,"speed action: onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 드레그 시작시 발생
                Log.d(TAG,"speed action: onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 드레그 중 발생
                Log.d(TAG,"speed action: onStopTrackingTouch");

                command = null;
                command = "sp-" + seekBar.getProgress();
                byte[] data = command.getBytes();
                Send_BT(data);
            }
        });
    }

    public void Send_BT(byte[] command){
        String result = "";
        if(characteristic != null){
            characteristic.setValue(command);
            boolean success = bluetoothGatt.writeCharacteristic(characteristic);
            //result = bluetoothGatt.readCharacteristic();
            if (!success) {
                Log.e(TAG, "Failed to write characteristic");
            }
        } else {
            Log.e(TAG, "Characteristic is null, cannot send command");
        }
        //return result;
    }
}
