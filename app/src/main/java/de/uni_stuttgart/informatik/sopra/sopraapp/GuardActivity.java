package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class GuardActivity extends AppCompatActivity {
    Button btnEditGuardRef;
    Button btnAcceptGuardRef;
    Button btnDeleteGuardRef;
    Button btnNewId;
    EditText etForenameRef;
    EditText etSurnameRef;
    TextView tvGuardIdRef;
    TextView tvAkivity;
    EditText etPasswordRef;
    DatabaseGuard guardDatabase;
    final static String secretKey = "dasistdiesichereverschluesselung";
    boolean edit;

    public static boolean newGuard = true;

    private void clearTextFields() {
        etSurnameRef.setText("");
        etForenameRef.setText("");
        etPasswordRef.setText("");
    }

    private Guard getEditedGuard() {
        return guardDatabase.getGuardById(getIntent().getIntExtra("editedGuardId", 0));
    }

    private void setNewUserId() {
        if (guardDatabase.getGuardCount() == 0) {
            tvGuardIdRef.setText("1");
        } else {
            tvGuardIdRef.setText(String.valueOf(Integer.valueOf(Integer.valueOf(guardDatabase.getGuard(guardDatabase.getGuardCount() - 1).getUserId()) + 1)));
        }
    }

    private void checkEditNewGuard() {
        if (newGuard == true) {
            tvAkivity.setText("Create a new guard:");

            edit = false;

            btnDeleteGuardRef.setVisibility(View.INVISIBLE);
            btnNewId.setVisibility(View.INVISIBLE);
            setNewUserId();
        } else {
            tvAkivity.setText("Edit your guard:");
            tvGuardIdRef.setText(getEditedGuard().getUserId());

            edit = true;

            btnDeleteGuardRef.setVisibility(View.VISIBLE);
            btnNewId.setVisibility(View.VISIBLE);

            etSurnameRef.setText(getEditedGuard().getSurname());
            etForenameRef.setText(getEditedGuard().getForename());
            etPasswordRef.setText(AesCrypto.decrypt(getEditedGuard().getUserPassword(), secretKey));
        }
    }

    private boolean checkDublicates(Guard guard){
         for(Guard g: guardDatabase.getAllGuards()){
             if((g.getUserId().equals(guard.getUserId())) ||
                     (g.getForename().equals(guard.getForename()) &&
                             g.getSurname().equals(guard.getSurname()))){
                 return true;
             }
         }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);

        btnEditGuardRef = findViewById(R.id.btnEditGuard);
        btnAcceptGuardRef = findViewById(R.id.btnAcceptGuard);
        btnDeleteGuardRef = findViewById(R.id.btnDeleteGuard);
        btnNewId = findViewById(R.id.btnNewId);
        etForenameRef = findViewById(R.id.etForname);
        etSurnameRef = findViewById(R.id.etSurname);
        etPasswordRef = findViewById(R.id.pwUserPassword);
        tvGuardIdRef = findViewById(R.id.tvGuardIDNumber);
        tvAkivity = findViewById(R.id.tvAktivity);
        guardDatabase = new DatabaseGuard(this);

        checkEditNewGuard();

        btnAcceptGuardRef.setOnClickListener(view -> {
            Guard createdGuard = new Guard();
            createdGuard.setForename(etForenameRef.getText().toString());
            createdGuard.setSurname(etSurnameRef.getText().toString());
            createdGuard.setUserPassword(AesCrypto.encrypt(etPasswordRef.getText().toString(),secretKey));
            createdGuard.setUserId(tvGuardIdRef.getText().toString());
            if (checkDublicates(createdGuard)&&!edit) {
                Toast.makeText(getApplicationContext(),"The guard allready exists, select the Guard to edit him.",Toast.LENGTH_SHORT).show();
            }else{
                if (newGuard) guardDatabase.addGuard(createdGuard);
                else guardDatabase.editGuard(createdGuard);
                newGuard = true;
                Intent intent = new Intent(view.getContext(), GuardActivity.class);
                startActivity(intent);
                clearTextFields();
                finish();
            }
        });
        btnDeleteGuardRef.setOnClickListener(view -> {
            guardDatabase.deleteGuard(getEditedGuard());
            newGuard = true;
            Intent intent = new Intent(view.getContext(), GuardActivity.class);
            startActivity(intent);
            clearTextFields();
            finish();
        });
        btnNewId.setOnClickListener(view -> {
            newGuard = true;
            Intent intent = new Intent(view.getContext(), GuardActivity.class);
            startActivity(intent);
            finish();
        });
        btnEditGuardRef.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), GuardListActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
