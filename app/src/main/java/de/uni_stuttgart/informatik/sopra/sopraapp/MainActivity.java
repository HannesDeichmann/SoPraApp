package de.uni_stuttgart.informatik.sopra.sopraapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    TextView tvLoginRef;
    Button btnAdminLoginRef;
    DatabaseGuard databaseGuard;
    DatabaseWaypoint databaseWaypoint;
    EditText etUsernameRef;
    EditText etPasswordRef;
    Button btnLoginRef;
    String guardUsername;
    String guardPassword;
    TextView tvLoginFeedbackRef;
    boolean loggedIn;
    private Duration duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseGuard = new DatabaseGuard(this);
        databaseWaypoint = new DatabaseWaypoint(this);

        //TODO BUG: Database darf nicht leer sein
        if(databaseGuard.getGuardCount() == 0) {
            databaseGuard.addGuard(new Guard("otto", "müllerich", "1234"));

        }
        if(databaseWaypoint.getWaypointCount() == 0) {
            databaseWaypoint.addWaypoint(new Waypoint("FirstWaypoint", "123456", "Tag", "Note"));
        }
        /*Guard otto = new Guard("Otto", "Muellerich", "2", "1234");
        databaseGuard = new DatabaseGuard(this);
        databaseGuard.addGuard(otto);
        */
        tvLoginRef = (TextView) findViewById(R.id.tvLogin);
        btnAdminLoginRef = (Button) findViewById(R.id.btnAdminLogin);
        etUsernameRef = findViewById(R.id.etUsername);
        etPasswordRef = findViewById(R.id.etPassword);
        btnLoginRef = findViewById(R.id.btnLogin);
        tvLoginFeedbackRef = findViewById(R.id.tvLoginFeedback);
        tvLoginFeedbackRef.setText("");

        //btnAdminLoginRef ist only there for development so while testing you dont need to log in every time
        btnAdminLoginRef.setVisibility(View.VISIBLE);
        btnAdminLoginRef.setText("ImageTest");
        btnAdminLoginRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), DrawActivity.class);
                //startActivity(intent);
                //finish();
            }
        });

        btnLoginRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                guardUsername=etUsernameRef.getText().toString();
                guardPassword=etPasswordRef.getText().toString();
                loggedIn=false;

                //the admin mode is accessable with the username "admin and password "admin"
                if((guardUsername.equals("admin") && guardPassword.equals("admin")) ||
                        (guardUsername.equals("") && guardPassword.equals(""))){
                    Intent intent = new Intent(view.getContext(), AdminActivity.class);
                    startActivity(intent);
                }else if(guardUsername.equals("") || guardPassword.equals("")) {
                    tvLoginFeedbackRef.setText("Please enter a username and a password");
                }else{
                    //check every guard if the given username is found and if yes, check if the
                    //passwords match
                    for (Guard guard : databaseGuard.getAllGuards()) {
                        if (guard.getUserId().equals(guardUsername)) {
                            if (guard.getUserPassword().equals(guardPassword)) {
                                loggedIn = true;
                                Intent intent = new Intent(view.getContext(), GuardModeActivity.class);
                                intent.putExtra("loggedInGuard", guard);
                                startActivity(intent);
                                tvLoginFeedbackRef.setText("");
                                etUsernameRef.setText("");
                                etPasswordRef.setText("");
                            } else {
                                tvLoginFeedbackRef.setText("Wrong password");
                                etPasswordRef.setText("");
                            }
                        }
                    }
                    if (!loggedIn && !tvLoginFeedbackRef.getText().equals("Wrong password")) {
                        tvLoginFeedbackRef.setText("No such username");
                    }
                }
            }
        });
    }
}
