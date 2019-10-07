package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvHelloRef;
    Button btnButtonRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHelloRef = (TextView) findViewById(R.id.tvHello);
        btnButtonRef = (Button) findViewById(R.id.btnButton);

        btnButtonRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DisplayActivity.class);
                String message = tvHelloRef.getText().toString();
                intent.putExtra("Message", message);
                startActivity(intent);

//                TextDialog dialog = new TextDialog();
//                dialog.show(getFragmentManager(), TextDialog.class.getSimpleName());
            }
        });


    }
}
