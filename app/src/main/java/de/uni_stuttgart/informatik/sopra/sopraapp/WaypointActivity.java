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
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static de.uni_stuttgart.informatik.sopra.sopraapp.NFCTagActivity.ERROR_DETECTED;
import static de.uni_stuttgart.informatik.sopra.sopraapp.NFCTagActivity.WRITE_SUCCESS;

public class WaypointActivity extends AppCompatActivity {

    Button btnEditWaypointRef;
    Button btnAcceptWaypointRef;
    Button btnDeleteWaypointRef;
    Button btnAssignWaypointRef;

    EditText etWaypointNameRef;
    EditText etWaypointIdRef;
    EditText etWaypointPositionRef;
    EditText etWaypointNoteRef;

    DatabaseWaypoint databaseWaypoint;
    DatabaseRoute databaseRoute;
    public static boolean newWaypoint = true;

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

    private void clearTextFields() {
        etWaypointNameRef.setText("");
        etWaypointIdRef.setText("");
        etWaypointPositionRef.setText("");
        etWaypointNoteRef.setText("");
    }

    private Waypoint getEditedWaypoint() {
        return databaseWaypoint.getWaypointById(getIntent().getIntExtra("editedWaypointId", 0));
    }

    private void checkEditNewWaypoint() {
        if (newWaypoint == true) {
            btnDeleteWaypointRef.setVisibility(View.INVISIBLE);
        } else {
            btnDeleteWaypointRef.setVisibility(View.VISIBLE);
            etWaypointNameRef.setText(getEditedWaypoint().getWaypointName());
            etWaypointIdRef.setText(getEditedWaypoint().getWaypointId());
            etWaypointPositionRef.setText(getEditedWaypoint().getWaypointPosition());
            etWaypointNoteRef.setText(getEditedWaypoint().getWaypointNote());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint);
        context = this;

        btnEditWaypointRef = findViewById(R.id.btnEditWaypoint);
        btnAcceptWaypointRef = findViewById(R.id.btnAcceptWaypoint);
        btnDeleteWaypointRef = findViewById(R.id.btnDeleteWaypoint);
        btnAssignWaypointRef = findViewById(R.id.btnAssignWaypoint);

        etWaypointNameRef = findViewById(R.id.etWaypointName);
        etWaypointIdRef = findViewById(R.id.etWaypointId);
        etWaypointPositionRef = findViewById(R.id.etWaypointPosition);
        etWaypointNoteRef = findViewById(R.id.etWaypointNote);

        databaseWaypoint = new DatabaseWaypoint(this);
        databaseRoute = new DatabaseRoute(this);

        checkEditNewWaypoint();

        btnAcceptWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etWaypointIdRef.getText().length() == Waypoint.waypointIdLength) {
                    Waypoint createdWaypoint = new Waypoint();
                    createdWaypoint.setWaypointName(etWaypointNameRef.getText().toString());
                    createdWaypoint.setWaypointId(etWaypointIdRef.getText().toString());
                    createdWaypoint.setWaypointPosition(etWaypointPositionRef.getText().toString());
                    createdWaypoint.setWaypointNote(etWaypointNoteRef.getText().toString());
                    if (newWaypoint) databaseWaypoint.addWaypoint(createdWaypoint);
                    else {
                        databaseWaypoint.deleteWaypoint(createdWaypoint);
                        databaseWaypoint.addWaypoint(createdWaypoint);
                    }
                    newWaypoint = true;

                    Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                    startActivity(intent);
                    finish();
                    // clearTextFields();
                } else {
                    //TODO Alertfenster
                    //etWaypointIdRef.setText("WaypointId needs " + Waypoint.waypointIdLength +" chars");
                    Toast toast = Toast.makeText(getApplicationContext(), "ID needs 6 chars", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        //wp needs to be deleted out of the routes that use the wp
        btnDeleteWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Route route : databaseRoute.getAllRoutes()) {
                    for (int i = 0; i < route.getWaypointStrings().size(); i++) {
                        int wpId = Integer.parseInt(route.getWaypointStrings().get(i).getUserId());
                        if (Integer.parseInt(getEditedWaypoint().getWaypointId()) == wpId) {
                            route.getWaypointStrings().remove(i);

                            databaseRoute.deleteRoute(route);
                            databaseRoute.addRoute(route);
                        }
                    }
                }
                databaseWaypoint.deleteWaypoint(getEditedWaypoint());
                newWaypoint = true;
                Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnEditWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WaypointListActivity.class);
                intent.putExtra("root", "WaypointActivity");
                startActivity(intent);
                finish();
            }
        });

        btnAssignWaypointRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etWaypointIdRef.getText().length() == Waypoint.waypointIdLength) {
                    try {
                        if(myTag ==null) {
                            Toast.makeText(context, ERROR_DETECTED, Toast.LENGTH_SHORT).show();
                        } else {
                            write(etWaypointIdRef.getText().toString(), myTag);
                            Toast.makeText(context, WRITE_SUCCESS, Toast.LENGTH_LONG ).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(context, WRITE_ERROR, Toast.LENGTH_LONG ).show();
                        e.printStackTrace();
                    } catch (FormatException e) {
                        Toast.makeText(context, WRITE_ERROR, Toast.LENGTH_LONG ).show();
                        e.printStackTrace();
                    }
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "ID needs 6 chars", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

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
        writeTagFilters = new IntentFilter[] { tagDetected };
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
//        String tagId = new String(msgs[0].getRecords()[0].getType());
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
    }

    /******************************************************************************
     **********************************Write to NFC Tag****************************
     ******************************************************************************/
    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = { createRecord(text) };
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
        String lang       = "en";
        byte[] textBytes  = text.getBytes();
        byte[] langBytes  = lang.getBytes("US-ASCII");
        int    langLength = langBytes.length;
        int    textLength = textBytes.length;
        byte[] payload    = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1,              langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);

        return recordNFC;
    }



    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        readFromIntent(intent);
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume(){
        super.onResume();
        WriteModeOn();
    }



    /******************************************************************************
     **********************************Enable Write********************************
     ******************************************************************************/
    private void WriteModeOn(){
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }
    /******************************************************************************
     **********************************Disable Write*******************************
     ******************************************************************************/
    private void WriteModeOff(){
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }
}