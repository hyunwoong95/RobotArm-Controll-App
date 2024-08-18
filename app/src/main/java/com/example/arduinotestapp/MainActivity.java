package com.example.arduinotestapp;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView mTvBluetoothStatus;
    TextView mTvReceiveData;
    TextView mTvSendData;
    ImageButton mSetting;
    ImageButton mBtnSearch;
    Button mPlay;
    Button mTeachingData;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    List<Map<String,String>> dataDevice;    //list - Device목록 저장
    Handler mBluetoothHandler;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;

    //final static UUID BT_UUID = UUID.fromString("8CE255C0-200A-11E0-AC64-0800200C9A66");    // 핸드폰대 핸드폰일경우
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");    // 아두이노 블루투스 연결일경우

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


        mTvBluetoothStatus = (TextView) findViewById(R.id.tvBluetoothStatus);
        mBtnSearch = (ImageButton) findViewById(R.id.bluetoothScan);
        mPlay = (Button) findViewById(R.id.playControl);
        mTeachingData = (Button) findViewById(R.id.teachingData);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();   // getDefaultAdapter()메서드는 해당 디바이스가 블루투스를 지원하는지 여부를 반납한다.


        mBtnSearch.setOnClickListener(new Button.OnClickListener() {   // 블루투스  스캔
            @Override
            public void onClick(View view) {
                bluetoothScan();
            }
        });
        mPlay.setOnClickListener(new Button.OnClickListener() {  // 로봇암 조이스틱 엑티비티
            @Override
            public void onClick(View view) {

            }
        });
        mTeachingData.setOnClickListener(new Button.OnClickListener() {   // 티칭데이터 엑티비티
            @Override
            public void onClick(View view) {

            }
        });
    }
    void bluetoothScan(){
        if(mBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(),"블루투스를 지원하지 않는 기기입니다.",Toast.LENGTH_LONG).show();
        }else{
            if(mBluetoothAdapter.isEnabled()){
                Toast.makeText(getApplicationContext(),"블루투스 검색을 시작합니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivityForResult(intent,0);
            }else{
                Toast.makeText(getApplicationContext(),"블루투스 활성화후에 시도해주세요.", Toast.LENGTH_LONG).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
            }
        }
    }
}