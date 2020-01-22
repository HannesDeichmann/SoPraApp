package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static de.uni_stuttgart.informatik.sopra.sopraapp.ProtocolActivity.list;

public class PatrolActivity extends AppCompatActivity {
    private TextView tvCountdownRef;
    private TextView tvTimeRef;
    private TextView tvNextWaypointNameRef;
    private TextView tvRouteNameRef;
    private TextView tvScanFeedbackRef;
    private TextView tvNoteRef;
    private Date date;
    private String protocolString;
    private Guard guard;
    private String protocolStringTimes = "";
    private Button btnStartCountdownRef;
    private Button btnFinishRouteRef;
    private Button btnCancelActiveRouteRef;
    private Button btnShowMapRef;
    public int nextWaypointCounter;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 0;
    private boolean timerRunning;
    private ListView lvCompleteRouteRef;
    private Route route;
    private GuardRoute selectedRoute;
    private Guard loggedInGuard;
    DatabaseGuard databaseGuard;


    /****************************NFC-Tag*******************************************/
    public static final String ERROR_DETECTED = "No NFC tag detected!";
    public static final String WRITE_SUCCESS = "Text written to the NFC tag successfully!";
    public static final String WRITE_ERROR = "No NFC Tag discovered. " +
            "Hold the NFC Tag to the device and wait for the vibration";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol);
        context = this;

        GuardRoute selectedRoute = (GuardRoute) getIntent().getExtras().get("selectedRoute");
        this.setSelectedRoute(selectedRoute);
        this.setRoute(selectedRoute.getRoute());
        this.setLoggedInGuard((Guard) getIntent().getExtras().get("loggedInGuard"));

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
        }
        readFromIntent(getIntent());

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};

        nextWaypointCounter = 0;
        tvTimeRef = findViewById(R.id.tvTime);
        tvCountdownRef = findViewById(R.id.tvCountdown);
        tvRouteNameRef = findViewById(R.id.tvRouteName);
        tvNoteRef = findViewById(R.id.tvNote);
        tvNextWaypointNameRef = findViewById(R.id.tvNextWaypointName);
        tvScanFeedbackRef = findViewById(R.id.tvScanFeedback);
        btnStartCountdownRef = findViewById(R.id.btnStartCountdown);
        btnFinishRouteRef = findViewById(R.id.btnFinishRoute);
        btnCancelActiveRouteRef = findViewById(R.id.btnCancelActiveRoute);
        btnShowMapRef = findViewById(R.id.btnShowMap);
        lvCompleteRouteRef = findViewById(R.id.lvCompleteRoute);

        btnFinishRouteRef.setVisibility(View.INVISIBLE);

        /**
         * Creating the listView for the whole route
         */
        ArrayList<RouteWaypoint> waypointList = route.getWaypoints();

        ArrayList<String> waypointsStringList = new ArrayList<>();


        for (RouteWaypoint routeWaypoint : waypointList) {
            int durationInt = (int) routeWaypoint.getDuration().toMinutes();
            String duration = Integer.toString(durationInt);
            waypointsStringList.add(routeWaypoint.getWaypoint().getWaypointName()
                    + " " + duration + "min");

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                waypointsStringList);

        lvCompleteRouteRef.setAdapter(dataAdapter);
        lvCompleteRouteRef.setDividerHeight(5);

        btnShowMapRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("route", route);
                startActivity(intent);
            }
        });

        btnStartCountdownRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
        btnCancelActiveRouteRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                /**
                 * TODO Log Entry Cancelled Route
                 */
            }

        });
        setupInformation();

        btnFinishRouteRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                date = new Date();
                protocolString += sdf.format(date) + "; " + "Route completed" +
                        ";" + protocolStringTimes;
                list.add(protocolString);
                finish();
            }

        });

    }


    public Route getRoute() {
        return this.route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setSelectedRoute(GuardRoute selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public GuardRoute getSelectedRoute() {
        return this.selectedRoute;
    }

    public void setLoggedInGuard(Guard guard) {
        this.loggedInGuard = guard;
    }

    public Guard getLoggedInGuard() {
        return this.loggedInGuard;
    }

    /******************************************************************************
     **********************************Read From NFC Tag***************************
     ******************************************************************************/
    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }

    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";
        // String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            // Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }


        /********
         * Use text as variable to get the message
         *******/
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        route = this.getRoute();
        //check if timer is running or the scanned waypoint was the last one
        if (nextWaypointCounter == route.getWaypoints().size()) {
            Toast.makeText(this, "Please finish route", Toast.LENGTH_LONG).show();
        } else {
            if (timerRunning) {
                //check if the scanned NFC Tag is the right one
                if (text.equals(route.getWaypoints().get(nextWaypointCounter).getWaypoint().getWaypointId())) {
                    date = new Date();
                    protocolStringTimes += " ;" + sdf.format(date);
                    nextWaypointCounter += 1;
                    stopTimer();
                    setupInformation();
                } else {
                    Toast.makeText(this, "Wrong waypoint", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "Please press start before scanning", Toast.LENGTH_LONG).show();
            }
        }

    }


    /**
     * Sets up the information gui for the guard:
     * If the last waypoint is reached, no new timer is set and no next waypoint is shown
     */
    public void setupInformation() {

        GuardRoute selectedRoute = this.getSelectedRoute();
        Route route = this.getRoute();
        Guard guard = this.getLoggedInGuard();


        if (nextWaypointCounter == route.getWaypoints().size()) {
            btnFinishRouteRef.setVisibility(View.VISIBLE);
            btnStartCountdownRef.setVisibility(View.INVISIBLE);
            tvNextWaypointNameRef.setText("");
            tvNoteRef.setText("Please finish route");
        } else {
            RouteWaypoint nextWaypoint = route.getWaypoints().get(nextWaypointCounter);
            long timeLeft = nextWaypoint.getDuration().toMinutes();
            timeLeftInMilliseconds = 60000 * timeLeft; // 60000 millisec = 1 min
            String formattedStartTime = formatStartTime(selectedRoute.getTime());
            tvTimeRef.setText(formattedStartTime);
            tvRouteNameRef.setText(route.getRouteName());
            tvNoteRef.setText(nextWaypoint.getWaypoint().getWaypointNote());
            tvNextWaypointNameRef.setText(nextWaypoint.getWaypoint().getWaypointName());
            updateTimer();
            startTimer();
        }

        protocolString = route.getRouteName() + route.getRouteId() + "; " + guard.getForename() + " " + guard.getSurname() + "; " + selectedRoute.getTime() + "; ";
    }

    public String formatStartTime(String time) {
        //String formattedTime = time.substring(0,1) + ":" + time.substring(2,3);
        String formattedTime = new StringBuilder(time).insert(2, ":").toString();
        return formattedTime;
    }

    public void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        btnStartCountdownRef.setText("PAUSE");
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        btnStartCountdownRef.setText("START");
        timerRunning = false;
        /**
         *TODO log Entry
         */
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        tvCountdownRef.setText(timeLeftText);
        if (timeLeftText.equals("0:00")) {
            Toast.makeText(PatrolActivity.this, "Silent alarm would now be sent",
                    Toast.LENGTH_SHORT).show();
            /**
             * TODO Log Entry when the alarm is send
             */
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerRunning) {
            stopTimer();
        }
    }

    /******************************************************************************
     **********************************Write to NFC Tag****************************
     ******************************************************************************/
    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        // Enable I/O
        ndef.connect();
        // Write the message
        ndef.writeNdefMessage(message);
        // Close the connection
        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        WriteModeOn();
    }


    /******************************************************************************
     **********************************Enable Write********************************
     ******************************************************************************/
    private void WriteModeOn() {
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    /******************************************************************************
     **********************************Disable Write*******************************
     ******************************************************************************/
    private void WriteModeOff() {
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }
}

