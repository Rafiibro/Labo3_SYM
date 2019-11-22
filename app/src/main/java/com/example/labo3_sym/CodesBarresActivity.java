package com.example.labo3_sym;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.text.method.ScrollingMovementMethod;
        import android.widget.Button;
        import android.widget.TextView;
        import android.view.View;


        import androidx.annotation.RequiresApi;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.content.ContextCompat;

        import com.example.labo3_sym.R;
        import com.google.zxing.integration.android.IntentIntegrator;

//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.listener.single.BasePermissionListener;

public class CodesBarresActivity extends AppCompatActivity {

    private Button btn_lecture = null;
    public static TextView result;
    private static final int MY_CAMERA_REQUEST_CODE = 100;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codesbarres);

        /* Indique la policy des threads */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /* Initialisation des boutons */
        this.btn_lecture = findViewById(R.id.btn_lecture);
        this.result = findViewById(R.id.text_codes_barres);


        btn_lecture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }
                Intent intent = new Intent(CodesBarresActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
    }
}
