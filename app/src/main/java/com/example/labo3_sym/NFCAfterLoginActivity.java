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
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NFCAfterLoginActivity extends AppCompatActivity {

    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "test";

    private  CountDownTimer cdt = null;

    private NfcAdapter mNfcAdapter;

    private Button btn_max_priority = null;
    private Button btn_medium_priority = null;
    private Button btn_min_priority = null;

    private int prio_value = 0;

    private boolean max_prio = true;
    private boolean medium_prio = true;
    private boolean min_prio = true;

    private static final int MAX_PRIO = 50;
    private static final int MEDIUM_PRIO = 40;
    private static final int MIN_PRIO = 20;


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

        prio_value = 60;
        max_prio = true;
        medium_prio = true;
        min_prio = true;

        countDown();


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

        prio_value = 60;
        max_prio = true;
        medium_prio = true;
        min_prio = true;
        if(cdt != null){

            cdt.cancel();
            cdt = null;
        }
        Toast.makeText(NFCAfterLoginActivity.this, "NFC détecté", Toast.LENGTH_LONG).show();
        cdt = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(prio_value < MAX_PRIO){
                    max_prio = false;
                }
                if(prio_value < MEDIUM_PRIO){
                    medium_prio = false;
                }
                if(prio_value < MIN_PRIO){
                    min_prio = false;
                }
                prio_value--;
            }

            public void onFinish() {

            }
        }.start();

    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask(
                        new NFCListener() {
                            public void handleNfcResponse() {
                                countDown();
                            }
                        }
                ).execute(tag);

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
                    new NdefReaderTask(
                            new NFCListener() {
                                public void handleNfcResponse() {
                                    countDown();
                                }
                            }
                    ).execute(tag);
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
