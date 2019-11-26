package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GuardActivity extends AppCompatActivity {
    Button btnEditGuardRef;
    Button btnAcceptGuardRef;
    Button btnDeleteGuardRef;
    EditText etForenameRef;
    EditText etSurnameRef;
    EditText etGuardIdRef;
    EditText etPasswordRef;
    Guard editedGuard;

    private void clearTextFields(){
        etSurnameRef.setText("");
        etForenameRef.setText("");
        etGuardIdRef.setText("");
        etPasswordRef.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);

        btnEditGuardRef = findViewById(R.id.btnEditGuard);
        btnAcceptGuardRef = findViewById(R.id.btnAcceptGuard);
        btnDeleteGuardRef = findViewById(R.id.btnDeleteGuard);
        etForenameRef = findViewById(R.id.etForname);
        etSurnameRef = findViewById(R.id.etSurname);
        etGuardIdRef = findViewById(R.id.etUserId);
        etPasswordRef = findViewById(R.id.pwUserPassword);

        String deleteGuardString = "";
        deleteGuardString += getIntent().getStringExtra("deleteGuard");
        editedGuard = null;
        if (deleteGuardString.contains(Guard.userIdIndicator)) {
            int indexOfId = deleteGuardString.indexOf(Guard.userIdIndicator);
            deleteGuardString = deleteGuardString.substring(indexOfId, indexOfId + Guard.userIdIndicator.length()
                    + Guard.userIdLength);

            for (Guard guard : Guard.getGuardList()) {
                if (guard.toString().contains(deleteGuardString)) {
                    editedGuard = guard;
                    etSurnameRef.setText(guard.getSurname());
                    etForenameRef.setText(guard.getForename());
                    etGuardIdRef.setText(guard.getUserId());
                    etPasswordRef.setText(guard.getUserPassword());
                }
            }
        } else {
            clearTextFields();
        }

        btnAcceptGuardRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etGuardIdRef.getText().length() == 6) {
                    Guard createdGuard;
                    if (editedGuard == null) {
                        createdGuard = new Guard();
                    } else {
                        createdGuard = editedGuard;
                    }
                    createdGuard.setForename(etForenameRef.getText().toString());
                    createdGuard.setSurname(etSurnameRef.getText().toString());
                    createdGuard.setUserId(etGuardIdRef.getText().toString());
                    createdGuard.setUserPassword(etPasswordRef.getText().toString());

                    clearTextFields();

                } else {
                    //TODO Allertfenster
                    etGuardIdRef.setText("UserId needs 6 chars");
                }
            }
        });

        btnDeleteGuardRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editedGuard != null && Guard.getGuardList().contains(editedGuard)) {
                    Guard.getGuardList().remove(editedGuard);
                    editedGuard = null;
                    clearTextFields();
                }else {
                    //TODO Alertfenster
                    clearTextFields();
                    etForenameRef.setText("There is no Guard to delete");
                }
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
