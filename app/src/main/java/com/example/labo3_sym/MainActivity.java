package com.example.labo3_sym;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import com.example.labo3_sym.R;
import com.example.labo3_sym.CodesBarresActivity;
import com.example.labo3_sym.NFCActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_codes_barres = null;
    private Button btn_nfc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Active le layout de main activity */
        setContentView(R.layout.activity_main);

        /* Indique la policy des threads */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /* Initialisation des boutons */
        this.btn_codes_barres = findViewById(R.id.btn_codes_barres);
        this.btn_nfc = findViewById(R.id.button_nfc);

        /* Lorsque l'on clique sur un des boutons, lance l'activité correspondante */

        btn_codes_barres.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, CodesBarresActivity.class);
            startActivity(intent);
        });

        btn_nfc.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, NFCActivity.class);
            startActivity(intent);
        });
    }
}
