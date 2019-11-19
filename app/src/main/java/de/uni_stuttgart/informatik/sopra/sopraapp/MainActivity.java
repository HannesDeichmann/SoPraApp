package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvLoginRef;
    Button btnLoginRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLoginRef = (TextView) findViewById(R.id.tvLogin);
        btnLoginRef = (Button) findViewById(R.id.btnLogin);

        btnLoginRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminActivity.class);
                startActivity(intent);

//                TextDialog dialog = new TextDialog();
//                dialog.show(getFragmentManager(), TextDialog.class.getSimpleName());
            }
        });


    }
}
