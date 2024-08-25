package com.example.arduinotestapp;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
//import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView BluetoothDeviceName;
    ImageButton mSetting;
    ImageButton mBtnSearch;
    Button mPlay;
    Button mTeachingData;

    Intent dIntent;

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
        BluetoothDeviceName.append("No connected Device");

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
                Intent intent = new Intent(MainActivity.this,bluetoothDialog.class);
                startActivity(intent);
                //finish();
            }
        });

        mPlay.setOnClickListener(new Button.OnClickListener() {  // 로봇암 조이스틱 엑티비티
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, joystickActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        mTeachingData.setOnClickListener(new Button.OnClickListener() {   // 티칭데이터 엑티비티
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, teachingDataActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }


}