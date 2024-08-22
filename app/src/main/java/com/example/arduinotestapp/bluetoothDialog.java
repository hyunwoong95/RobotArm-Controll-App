package com.example.arduinotestapp;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class bluetoothDialog extends AppCompatActivity {

    ListView listView;

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothLeScanner bluetoothLeScanner;

    private BluetoothDevice device;
    private ArrayAdapter<String> deviceListAdapter;
    private ArrayList<String> deviceList;
    private ArrayList<BluetoothDevice> sDeviceList;
    private static final long SCAN_PERIOD = 10000; //스캔시간 10초

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_dialog);

        //  ListView 초기화
        listView = (ListView) findViewById(R.id.deviceList);
        deviceList = new ArrayList<>();
        deviceListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList);
        listView.setAdapter(deviceListAdapter);

        sDeviceList = new ArrayList<>();

        try{
            if (bluetoothAdapter != null ) {   // 블루투스를 지원하지 않는 기기가 아니라면
                if (bluetoothAdapter.isEnabled()) {   // 블루투스가 활성화 되어있다면
                    bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
                    startBleScan();
                } else {  // 활성화가 되어 있지 않다면
                    Toast.makeText(bluetoothDialog.this, "블루투스를 활성화 해주세요.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 1);
                }
            }else{
                Toast.makeText(bluetoothDialog.this, "블루투스를 지원 하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }
        
        /*
        * listView의 항목 클릭 이벤트 설정
        * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                // 선택한 디바이스 가져오기
                BluetoothDevice selectedDevice = sDeviceList.get(position);
                // 선택된 디바이스와 연결 시도
                connectToDevice(selectedDevice);
                
            }
        });
    }


    /*
    * LE블루투스 검색
    * */
    public void startBleScan()  {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //bluetoothLeScanner.stopScan(leScanCallback);
                Toast.makeText(bluetoothDialog.this,"Scan complete", Toast.LENGTH_LONG).show();
            }
        },SCAN_PERIOD);

        // 스캔 시작
        bluetoothLeScanner.startScan(leScanCallback);
        Toast.makeText(this,"Scanning for BLE devices...",Toast.LENGTH_LONG).show();

    }

    /*
    * LE블루투스 스캔 Callback
    * */
    ScanCallback leScanCallback = new ScanCallback() {  // BLE 검색 Callback
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            // 스캔된 BLE 기기 처리
            device = result.getDevice();
            String deviceName = device.getName();
            String deviceAddress = device.getAddress();

            if(!deviceList.contains(deviceName)){   // 중복된 장치 정보 제외
                deviceList.add(deviceName);
                deviceListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            for(ScanResult result : results){
                device = result.getDevice();
                String deviceName = device.getName();
                String deviceAddress = device.getAddress();

                if(!deviceList.contains(deviceName)){   // 중복된 장치 정보 제외
                    sDeviceList.add(device);
                    deviceList.add(deviceName);
                    deviceListAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Toast.makeText(bluetoothDialog.this ,"검색에 실패 하였습니다.",Toast.LENGTH_LONG).show();
        }
    };


    /*
    * GATT 연결 콜백 정의
    * */
    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if(newState == BluetoothProfile.STATE_CONNECTED){
                gatt.discoverServices();
            }else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                // 연결 해제됨
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }
    };

    /*
    * 블루투스 연결
    * */
    private void connectToDevice(BluetoothDevice device){
        BluetoothGatt bluetoothGatt = device.connectGatt(this, false, gattCallback);
    }

}
