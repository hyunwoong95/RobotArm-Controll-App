package com.example.arduinotestapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView BluetoothDeviceName;
    ImageButton mSetting;
    ImageButton mBtnSearch;
    Button mPlay;
    Button mTeachingData;

    Intent dIntent;

    BluetoothGatt bluetoothGatt = GattSingleton.getInstance().getBluetoothGatt();
    BluetoothDevice connectedDevice = GattSingleton.getInstance().getBluetoothDevice();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        BluetoothDeviceName = (TextView) findViewById(R.id.BluetoothDeviceName);
        BluetoothDeviceName.setText("");
        //BluetoothDeviceName.append("No connected Device");

        if(connectedDevice != null){
            String deviceName = connectedDevice.getName();
            if(deviceName != null){
                BluetoothDeviceName.append(deviceName);
            }else{
                BluetoothDeviceName.append("Unknown Device Name");
            }
        }else{
            BluetoothDeviceName.append("No connected Device");
        }

        mBtnSearch = (ImageButton) findViewById(R.id.bluetoothScan);
        mPlay = (Button) findViewById(R.id.playControl);
        mTeachingData = (Button) findViewById(R.id.teachingData);

        Log.d("Main_TAG","MainActivity.");


//        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this,"andrid.permission.ACCESS_FINE_LOCATION");
//        int BLUETOOTH = ContextCompat.checkSelfPermission(this,"android.permission.BLUETOOTH");
//
//        if(ACCESS_FINE_LOCATION == PackageManager.PERMISSION_DENIED && BLUETOOTH == PackageManager.PERMISSION_DENIED){
//            ActivityCompat.requestPermissions(this,[ACCESS_FINE_LOCATION,BLUETOO);
//        }



        mBtnSearch.setOnClickListener(new Button.OnClickListener() {   // 블루투스  스캔
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BLEBluetoothDialog.class);
                startActivity(intent);

                //finish();
            }
        });

        mPlay.setOnClickListener(new Button.OnClickListener() {  // 로봇암 조이스틱 엑티비티
            @Override
            public void onClick(View view) {
                if(bluetoothGatt != null){
//                    int connectionState = bluetoothGatt.getConnectionState();
//                    if (connectionState == BluetoothProfile.STATE_CONNECTED) {
//                        // GATT 객체가 연결된 상태
//                        Toast.makeText(MainActivity.this, "Device is connected", Toast.LENGTH_SHORT).show();
//                        // 여기서 GATT 통신 작업 수행 가능
//                    } else if (connectionState == BluetoothProfile.STATE_DISCONNECTED) {
//                        // GATT 객체가 연결 해제된 상태
//                        Toast.makeText(MainActivity.this, "Device is disconnected", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 기타 상태 처리
//                        Toast.makeText(MainActivity.this, "Device state unknown", Toast.LENGTH_SHORT).show();
//                    }
                    Intent intent = new Intent(MainActivity.this, joystickActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"연결된 장치가 없습니다. 연결후 시도 해주세요.",Toast.LENGTH_LONG).show();
                }
                //finish();
            }
        });
        mTeachingData.setOnClickListener(new Button.OnClickListener() {   // 티칭데이터 엑티비티
            @Override
            public void onClick(View view) {
                if(bluetoothGatt != null){
                    Intent intent = new Intent(MainActivity.this, teachingDataActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"연결된 장치가 없습니다. 연결후 시도 해주세요.",Toast.LENGTH_LONG).show();
                }

                //finish();
            }
        });
    }


}