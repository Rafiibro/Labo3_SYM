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

public class NFC_AFTER_LOGIN_ACTIVITY extends AppCompatActivity {

    private Button btn_max_priority = null;
    private Button btn_medium_priority = null;
    private Button btn_min_priority = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_after_login);


        /* Indique la policy des threads */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /* Initialisation des boutons */
        this.btn_max_priority = findViewById(R.id.bouton_after_login_1);
        this.btn_medium_priority = findViewById(R.id.bouton_after_login_2);
        this.btn_min_priority = findViewById(R.id.bouton_after_login_3);

        /* Lorsque l'on clique sur un des boutons, lance l'activité correspondante */

        btn_max_priority.setOnClickListener((v) -> {

        });

        btn_medium_priority.setOnClickListener((v) -> {
        });

        btn_min_priority.setOnClickListener((v) -> {

        });


    }
}
