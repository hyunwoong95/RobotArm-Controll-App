package com.example.blebluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;


public class BleBluetooth {

    private static final BleBluetooth bleBluetooth = new BleBluetooth();


    private BluetoothLeScanner bluetoothLeScanner;
    private Handler handler = new Handler();

    private BleBluetooth(){

    }

    public static BleBluetooth getInstance(){
        return bleBluetooth;
    }

    public void scanStart(long SCAN_PERIOD){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //bluetoothLeScanner.stopScan(leScanCallback);
            }
        },SCAN_PERIOD);

        // 스캔 시작
        //bluetoothLeScanner.startScan(leScanCallback);

    }

    /*
     * LE블루투스 스캔 Callback
     * */
//    ScanCallback leScanCallback = new ScanCallback() {  // BLE 검색 Callback
//        @Override
//        public void onScanResult(int callbackType, ScanResult result) {
//            super.onScanResult(callbackType, result);
//            // 스캔된 BLE 기기 처리
//            device = result.getDevice();
//            String deviceName = device.getName();
//            String deviceAddress = device.getAddress();
//
//            if(!deviceList.contains(deviceName)){   // 중복된 장치 정보 제외
//                sDeviceList.add(device);
//                deviceList.add(deviceName);
//                deviceListAdapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void onBatchScanResults(List<ScanResult> results) {
//            super.onBatchScanResults(results);
//            for(ScanResult result : results){
//                device = result.getDevice();
//                String deviceName = device.getName();
//                String deviceAddress = device.getAddress();
//
//                if(!deviceList.contains(deviceName)){   // 중복된 장치 정보 제외
//                    deviceList.add(deviceName);
//                    deviceListAdapter.notifyDataSetChanged();
//                }
//            }
//        }
//
//        @Override
//        public void onScanFailed(int errorCode) {
//            super.onScanFailed(errorCode);
//            Toast.makeText(bluetoothDialog.this ,"검색에 실패 하였습니다.",Toast.LENGTH_LONG).show();
//        }
//    };



}
