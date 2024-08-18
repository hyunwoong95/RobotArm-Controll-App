package com.example.arduinotestapp;

import android.app.Dialog;
import android.content.Context;
import android.widget.ListView;

import androidx.annotation.NonNull;

public class bluetoothDialog extends Dialog {
    private ListView deviceData;

    public bluetoothDialog(@NonNull Context context, String contents){
        super(context);
        setContentView(R.layout.bluetooth_dialog);




    }
}
