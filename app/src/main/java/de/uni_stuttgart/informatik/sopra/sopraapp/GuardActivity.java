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
    public static boolean newGuard = true;

    private void clearTextFields() {
        etSurnameRef.setText("");
        etForenameRef.setText("");
        etPasswordRef.setText("");
    }

    private Guard getEditedGuard() {
        return guardDatabase.getGuardById(getIntent().getIntExtra("editedGuardId", 0));
    }

    private void setNewUserId(){
        //int a = Integer.valueOf(guardDatabase.getGuard(guardDatabase.getGuardCount() - 1).getUserId()) + 1;
        //int b = Integer.valueOf(guardDatabase.getGuardById(getIntent().getIntExtra("editedGuardId", 0)).getUserId());
        //maxUserId = Integer.max(Integer.max(a,b),maxUserId);
        //TODO Bugfix, lösche Guard mit höchster nummer -> autoincrement geht bei dieser Nummer weiter, angezeigt wird allerdings die kleinere
        tvGuardIdRef.setText(String.valueOf(Integer.valueOf(Integer.valueOf(guardDatabase.getGuard(guardDatabase.getGuardCount() - 1).getUserId()) + 1)));
    }

    private void checkEditNewGuard() {
        if (newGuard == true) {
            tvAkivity.setText("Create a new guard:");
            btnDeleteGuardRef.setVisibility(View.INVISIBLE);
            btnNewId.setVisibility(View.INVISIBLE);
            setNewUserId();
        } else {
            tvAkivity.setText("Edit your guard:");
            tvGuardIdRef.setText(getEditedGuard().getUserId());

            btnDeleteGuardRef.setVisibility(View.VISIBLE);
            btnNewId.setVisibility(View.VISIBLE);

            etSurnameRef.setText(getEditedGuard().getSurname());
            etForenameRef.setText(getEditedGuard().getForename());
            etPasswordRef.setText(getEditedGuard().getUserPassword());
        }
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
        tvGuardIdRef = findViewById(R.id.tvGuardId);
        tvAkivity = findViewById(R.id.tvAktivity);
        guardDatabase = new DatabaseGuard(this);

        checkEditNewGuard();

        btnAcceptGuardRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Guard createdGuard = new Guard();
                createdGuard.setForename(etForenameRef.getText().toString());
                createdGuard.setSurname(etSurnameRef.getText().toString());
                createdGuard.setUserPassword(etPasswordRef.getText().toString());
                guardDatabase.addGuard(createdGuard);
                newGuard = true;
                Intent intent = new Intent(view.getContext(), GuardActivity.class);
                startActivity(intent);
                clearTextFields();
            }
        });
        btnDeleteGuardRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardDatabase.deleteGuard(getEditedGuard());
                newGuard = true;
                Intent intent = new Intent(view.getContext(), GuardActivity.class);
                startActivity(intent);
                clearTextFields();
            }
        });
        btnNewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGuard = true;
                Intent intent = new Intent(view.getContext(), GuardActivity.class);
                startActivity(intent);
            }
        });
        btnEditGuardRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GuardListActivity.class);
                startActivity(intent);
            }
        });
    }
}
