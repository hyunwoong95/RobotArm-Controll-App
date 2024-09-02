package com.example.arduinotestapp;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class bluetoothDialog extends AppCompatActivity {

    ListView pairedlistView;
    ListView scanlistView;

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;

    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<String> btArrayAdapter;
    ArrayList<String> deviceAddressArray;
    Set<BluetoothDevice> scanDevices;
    ArrayAdapter<String> scArrayAdapter;
    ArrayList<String> scDeviceAddressArray;

    private final static int REQUEST_ENABLE_BT = 1;
    BluetoothSocket btSocket = null;
    ConnectedThread connectedThread;
    String TAG = "bluetoothDialog";
    UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    List<BluetoothDevice> deviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_dialog);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        //  ListView 초기화
        pairedlistView = (ListView) findViewById(R.id.pairedDeviceList);
        scanlistView = (ListView) findViewById(R.id.scanDeviceList);

        try{
            if (bluetoothAdapter != null ) {   // 블루투스를 지원하지 않는 기기가 아니라면
                if (bluetoothAdapter.isEnabled()) {   // 블루투스가 활성화 되어있다면

                    btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
                    deviceAddressArray = new ArrayList<>();
                    pairedlistView.setAdapter(btArrayAdapter);

                    scArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
                    scDeviceAddressArray = new ArrayList<>();
                    scanlistView.setAdapter(scArrayAdapter);

                    pairedDevices();    // 페어링된 기기목록 찾기
                    scanDevices(); // 주변 기기 검색

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
        * 페어링된 기기의 listView 항목 클릭 이벤트 설정
        * */
        pairedlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                final String name = btArrayAdapter.getItem(position);
                final String address = deviceAddressArray.get(position);

                Toast.makeText(getApplicationContext(),"select to " + name,Toast.LENGTH_SHORT).show();

                boolean flag = true;

                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

                // create & connect socket
                try{
                    btSocket = createBluetoothSocket(device);
                    btSocket.connect();
                }catch(IOException e){
                    flag = false;
                    //textStatus.setText("connect failed!");
                    e.printStackTrace();
                }

                if(flag){
                    //textStatus.setText("connected to " + name);
                    Toast.makeText(getApplicationContext(),"connected to " + name,Toast.LENGTH_SHORT).show();
                    connectedThread = new ConnectedThread(btSocket);
                    connectedThread.start();
                }

            }
        });

        /*
         * 주변 기기 검색 listView 항목 클릭 이벤트 설정
         * */
        scanlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                final String name = scArrayAdapter.getItem(position);
                final String address = scDeviceAddressArray.get(position);

                Toast.makeText(getApplicationContext(),"select to " + name,Toast.LENGTH_SHORT).show();

                boolean flag = true;

                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

                // create & connect socket
                try{
                    btSocket = createBluetoothSocket(device);
                    btSocket.connect();
                }catch(IOException e){
                    flag = false;
                    //textStatus.setText("connect failed!");s
                    e.printStackTrace();
                }

                if(flag){
                    //textStatus.setText("connected to " + name);
                    Toast.makeText(getApplicationContext(),"connected to " + name,Toast.LENGTH_SHORT).show();
                    connectedThread = new ConnectedThread(btSocket);
                    connectedThread.start();
                }
            }
        });
    }

    /*
    * paired Device list
    * */
    public void pairedDevices(){
        btArrayAdapter.clear();
        if(deviceAddressArray != null && !deviceAddressArray.isEmpty()){
            deviceAddressArray.clear();
        }

        pairedDevices = bluetoothAdapter.getBondedDevices();    // 기존에 페어링 된 기기 목록가져오기

        if(pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                String deviceName = device.getName() != null ? device.getName() : "알수없는 장치";
                String deviceHardwareAddress = device.getAddress();

                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
            }
        }
    }

    /*
    * scan Device list
    * */
    public void scanDevices(){
        try{
            if(bluetoothAdapter.isDiscovering()){
                bluetoothAdapter.cancelDiscovery();
            }else{
                if(bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.startDiscovery();  // 주변 기기 검색
                    scArrayAdapter.clear();
                    if(scDeviceAddressArray != null &&  !scDeviceAddressArray.isEmpty()){
                        scDeviceAddressArray.clear();
                    }
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(receiver, filter);
                }else{
                    Toast.makeText(getApplicationContext(), "bluetooth not on",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }


    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String deviceName = device.getName() != null ? device.getName() : "알수없는 장치";
                String deviceHardwareAddress = device.getAddress();
                scArrayAdapter.add(deviceName);
                scDeviceAddressArray.add(deviceHardwareAddress);
                scArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();

        // ACTION_FOUND 수신자 등록 취소
        unregisterReceiver(receiver);
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }

}
