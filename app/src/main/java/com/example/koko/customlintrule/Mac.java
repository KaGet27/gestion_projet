package com.example.koko.customlintrule;

import android.app.Activity;

//import android.bluetooth.BluetoothAssignedNumbers;
import android.bluetooth.le.AdvertiseCallback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// Classe exemple, dont le nom fait 3 caractères de long, pour déclencher la règle lint associée
// this is a sample class, whose name is 3 character long, to trigger the associated custom lin rule
public class Mac extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
