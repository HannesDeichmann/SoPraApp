package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class GuardListActivity extends AppCompatActivity {
    Button btnCancel;
    ListView listView;
    EditText etSearchGuardRef;
    DatabaseGuard databaseGuard;
    ArrayList<Guard> allGuards = new ArrayList<>();
    ArrayList<String> guardStringList;
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        allGuards = databaseGuard.getAllGuards();
        if (databaseGuard.getGuardCount() > 0) {
            for (Guard guard : databaseGuard.getAllGuards()) {
                guardStringList.add(guard.toString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_list);

        databaseGuard = new DatabaseGuard(this);
        btnCancel = findViewById(R.id.btnCancel);
        listView = findViewById(R.id.guardList);
        etSearchGuardRef = findViewById(R.id.etSearchGuard);
        allGuards = databaseGuard.getAllGuards();
        guardStringList = new ArrayList<>();
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, guardStringList);
        listView.setAdapter(dataAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardActivity.newGuard = true;
                Intent intent = new Intent(view.getContext(), GuardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        etSearchGuardRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = etSearchGuardRef.getText().toString();
                guardStringList.clear();
                for (Guard g : allGuards) {
                    if (g.toString().contains(searchText)) {
                        guardStringList.add(g.toString());
                    }
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), GuardActivity.class);
                GuardActivity.newGuard = false;
                int guardId = Integer.parseInt(guardStringList.get(position).split(":")[0]);
                intent.putExtra("editedGuardId", guardId);
                startActivity(intent);
                finish();
            }
        });
    }
}
