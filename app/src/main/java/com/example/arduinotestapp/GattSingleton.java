package com.example.arduinotestapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

public class GattSingleton {
    private static GattSingleton instance;
    private BluetoothGatt bluetoothGatt;
    private BluetoothDevice bluetoothDevice;

    private GattSingleton(){}

    public static synchronized GattSingleton getInstance() {
        if (instance == null) {
            instance = new GattSingleton();
        }
        return instance;
    }

    public void setBluetoothGatt(BluetoothGatt gatt) {
        this.bluetoothGatt = gatt;
    }

    public BluetoothGatt getBluetoothGatt() {
        return bluetoothGatt;
    }

    public void setBluetoothDevice(BluetoothDevice device) {
        this.bluetoothDevice = device;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }
}
