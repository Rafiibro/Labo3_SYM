package com.example.labo3_sym;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.labo3_sym.R;

//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.listener.single.BasePermissionListener;

public class NFCActivity extends AppCompatActivity {

    private Button btn_login = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        this.btn_login = findViewById(R.id.btn_login);

        /* Lorsque l'on clique sur un des boutons, lance l'activité correspondante */

        btn_login.setOnClickListener((v) -> {
            Intent intent = new Intent(NFCActivity.this, NFC_AFTER_LOGIN_ACTIVITY.class);
            startActivity(intent);
        });
    }
}
