package com.example.labo3_sym;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.labo3_sym.R;

//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.listener.single.BasePermissionListener;

public class NFC_AFTER_LOGIN_ACTIVITY extends AppCompatActivity {

    private Button btn_max_priority = null;
    private Button btn_medium_priority = null;
    private Button btn_min_priority = null;

    private int prio_value = 0;

    private boolean max_prio = true;
    private boolean medium_prio = true;
    private boolean min_prio = true;

    private static final int MAX_PRIO = 40;
    private static final int MEDIUM_PRIO = 20;
    private static final int MIN_PRIO = 10;


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

        prio_value = 60;
        max_prio = true;
        medium_prio = true;
        min_prio = true;

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(prio_value <= MAX_PRIO){
                    max_prio = false;
                } else if(prio_value <= MEDIUM_PRIO){
                    medium_prio = false;
                } else if(prio_value <= MIN_PRIO){
                    min_prio = false;
                }
                prio_value--;
            }

            public void onFinish() {

            }
        }.start();


        /* Lorsque l'on clique sur un des boutons, lance l'activité correspondante */

        btn_max_priority.setOnClickListener((v) -> {
            if(max_prio){
                Toast.makeText(this, "You have the max priority", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You don't have the max priority", Toast.LENGTH_SHORT).show();
            }
        });

        btn_medium_priority.setOnClickListener((v) -> {
            if(medium_prio){
                Toast.makeText(this, "You have the medium priority", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You don't have the medium priority", Toast.LENGTH_SHORT).show();
            }
        });

        btn_min_priority.setOnClickListener((v) -> {
            if(min_prio){
                Toast.makeText(this, "You have the min priority", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You don't have min max priority", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
