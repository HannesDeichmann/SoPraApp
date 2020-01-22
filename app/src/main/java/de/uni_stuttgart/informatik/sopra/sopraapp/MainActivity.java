package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.time.Duration;

public class MainActivity extends AppCompatActivity {
    TextView tvLoginRef;
    ImageView ivAebLogoRef;
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

        etUsernameRef = findViewById(R.id.etUsername);
        etPasswordRef = findViewById(R.id.etPassword);
        btnLoginRef = findViewById(R.id.btnLogin);
        tvLoginFeedbackRef = findViewById(R.id.tvLoginFeedback);
        ivAebLogoRef = findViewById(R.id.imageViewAebLogo);
        tvLoginFeedbackRef.setText("");

        btnLoginRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                guardUsername=etUsernameRef.getText().toString();
                guardPassword=etPasswordRef.getText().toString();
                loggedIn=false;

                //the admin mode is accessable with the username "admin and password "admin"
                if((guardUsername.equals("admin") && guardPassword.equals("admin"))){
                    Intent intent = new Intent(view.getContext(), AdminActivity.class);
                    startActivity(intent);
                }else if(guardUsername.equals("") || guardPassword.equals("")) {
                    tvLoginFeedbackRef.setText("Please enter a username and a password");
                }else{
                    //check every guard if the given username is found and if yes, check if the
                    //passwords match
                    for (Guard guard : databaseGuard.getAllGuards()) {
                        if (guard.getUserId().equals(guardUsername)) {
                            if (AesCrypto.decrypt(guard.getUserPassword(), GuardActivity.secretKey).equals(guardPassword)) {
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
