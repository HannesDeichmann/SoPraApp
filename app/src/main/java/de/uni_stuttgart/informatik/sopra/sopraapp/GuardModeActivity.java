package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuardModeActivity extends AppCompatActivity {

    Button btnSelectARouteToStartRef;
    Button btnLogOutRef;
    TextView tvUsernameRef;
    private Guard loggedInGuard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_mode);

        loggedInGuard = (Guard) getIntent().getExtras().get("guard");

        btnSelectARouteToStartRef = findViewById(R.id.btnSelectARouteToStart);
        btnLogOutRef = findViewById(R.id.btnLogOut);
        tvUsernameRef = findViewById(R.id.tvUsername);
        tvUsernameRef.setText(loggedInGuard.getUserId());

        btnSelectARouteToStartRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GuardModeRouteListActivity.class);
                intent.putExtra("loggedInGuard", loggedInGuard);
                startActivity(intent);
            }
        });

        btnLogOutRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                finish();
            }
        });
    }
}
