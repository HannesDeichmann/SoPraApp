package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuardModeActivity extends AppCompatActivity {

    Button btnSelectARouteToStartRef;
    Guard loggedInGuard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_mode);

        // loggedInGuard = getIntent().getExtra();
        btnSelectARouteToStartRef = findViewById(R.id.btnSelectARouteToStart);

        btnSelectARouteToStartRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GuardModeRouteListActivity.class);
                intent.putExtra("guard", loggedInGuard);
                startActivity(intent);
            }
        });
    }
}
