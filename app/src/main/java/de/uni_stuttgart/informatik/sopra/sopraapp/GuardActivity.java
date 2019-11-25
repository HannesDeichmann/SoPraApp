package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GuardActivity extends AppCompatActivity {
    Button btnShowGuardsRef;
    Button btnCreateGuardsRef;
    Button btnDeleteGuardsRef;
    EditText etForNameRef;
    EditText etSurNameRef;
    EditText etUserIdRef;
    EditText etPasswordRef;
    Guard editedGuard;

    private void clearTextFields(){
        etSurNameRef.setText("");
        etForNameRef.setText("");
        etUserIdRef.setText("");
        etPasswordRef.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);

        btnShowGuardsRef = findViewById(R.id.btnShowGuard);
        btnCreateGuardsRef = findViewById(R.id.btnCreateGuard);
        btnDeleteGuardsRef = findViewById(R.id.btnDeleteGuard);
        etForNameRef = findViewById(R.id.etForname);
        etSurNameRef = findViewById(R.id.etSurname);
        etUserIdRef = findViewById(R.id.etUserId);
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
                    etSurNameRef.setText(guard.getSurname());
                    etForNameRef.setText(guard.getForename());
                    etUserIdRef.setText(guard.getUserId());
                    etPasswordRef.setText(guard.getUserPassword());
                }
            }
        } else {
            clearTextFields();
        }

        btnCreateGuardsRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUserIdRef.getText().length() == 6) {
                    Guard createdGuard;
                    if (editedGuard == null) {
                        createdGuard = new Guard();
                    } else {
                        createdGuard = editedGuard;
                    }
                    createdGuard.setForename(etForNameRef.getText().toString());
                    createdGuard.setSurname(etSurNameRef.getText().toString());
                    createdGuard.setUserId(etUserIdRef.getText().toString());
                    createdGuard.setUserPassword(etPasswordRef.getText().toString());

                    clearTextFields();

                } else {
                    //TODO Allertfenster
                    etUserIdRef.setText("UserId needs 6 chars");
                }
            }
        });

        btnDeleteGuardsRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editedGuard != null && Guard.getGuardList().contains(editedGuard)) {
                    Guard.getGuardList().remove(editedGuard);
                    editedGuard = null;
                    clearTextFields();
                }else {
                    //TODO Alertfenster
                    clearTextFields();
                    etForNameRef.setText("There is no Guard to delete");
                }
            }
        });

        btnShowGuardsRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GuardListActivity.class);
                startActivity(intent);
            }
        });
    }
}
