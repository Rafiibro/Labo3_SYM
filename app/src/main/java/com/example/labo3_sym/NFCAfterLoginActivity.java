package com.example.labo3_sym;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.listener.single.BasePermissionListener;

public class NFCAfterLoginActivity extends AppCompatActivity {

    private Button btn_max_priority = null;
    private Button btn_medium_priority = null;
    private Button btn_min_priority = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcafterlogin);


        /* Indique la policy des threads */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /* Initialisation des boutons */
        this.btn_max_priority = findViewById(R.id.btn_max_security);
        this.btn_medium_priority = findViewById(R.id.btn_medium_security);
        this.btn_min_priority = findViewById(R.id.btn_min_security);

        /* Lorsque l'on clique sur un des boutons, lance l'activité correspondante */

        btn_max_priority.setOnClickListener((v) -> {

        });

        btn_medium_priority.setOnClickListener((v) -> {
        });

        btn_min_priority.setOnClickListener((v) -> {

        });


    }
}
