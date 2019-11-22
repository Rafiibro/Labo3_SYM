package com.example.labo3_sym;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.listener.single.BasePermissionListener;

public class NFCActivity extends AppCompatActivity {


    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "test";

    private String password = "1234";
    private String email = "bob@a.ch";

    private  CountDownTimer cdt = null;

    private NfcAdapter mNfcAdapter;
    private Button btn_login = null;
    private EditText et_password = null;
    private EditText et_email = null;
    private boolean connectionValid = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        this.btn_login = findViewById(R.id.btn_login);
        this.et_password = findViewById(R.id.et_password);
        this.et_email = findViewById(R.id.et_email);

        btn_login.setOnClickListener((v) -> {

            if(connectionValid) {

                if(!(et_email.getText().toString().equals("") || et_password.getText().toString().equals(""))) {
                    if(email.equals(et_email.getText().toString()) && password.equals(et_password.getText().toString())) {

                        Intent intent = new Intent(NFCActivity.this, NFC_AFTER_LOGIN_ACTIVITY.class);
                        startActivity(intent);
                    }else{

                        Toast.makeText(NFCActivity.this, "mauvais mot de passe ou login", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(NFCActivity.this, "des champs ne sont pas remplis", Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(NFCActivity.this, "Erreur pas de nfc valide", Toast.LENGTH_LONG).show();
            }
        });

        /* Lorsque l'on clique sur un des boutons, lance l'activité correspondante */

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
            Log.i("NFC","NFC is disabled.");
        } else {
            Log.i("NFC","NFC is enabled.");
        }

        handleIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        stopForegroundDispatch(this, mNfcAdapter);

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        handleIntent(intent);
    }

    public void countDown(){


        if(cdt != null){

            cdt.cancel();
            cdt = null;

        }
        Toast.makeText(NFCActivity.this, "NFC détecté", Toast.LENGTH_LONG).show();
        cdt = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                connectionValid = true;
            }

            public void onFinish() {
                connectionValid = false;
            }
        }.start();

    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask(this).execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask(this).execute(tag);
                    break;
                }
            }
        }
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }


    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);


    }

}
