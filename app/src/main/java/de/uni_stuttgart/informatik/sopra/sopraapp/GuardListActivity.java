package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class GuardListActivity extends AppCompatActivity {

    Button btnCancel;
    ListView listView;
    DatabaseGuard guardDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_list);

        guardDatabase = new DatabaseGuard(this);
        btnCancel = findViewById(R.id.btnCancel);
        listView = findViewById(R.id.guardList);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardActivity.newGuard = true;
                Intent intent = new Intent(view.getContext(), GuardActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<String> guardStringList = new ArrayList<>();

        for(Guard guard : guardDatabase.getAllGuards()){
            guardStringList.add(guard.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                guardStringList);

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(), GuardActivity.class);
                GuardActivity.newGuard = false;
                //EIGENTLICH: Item.at(position) oder so ...
                int guardId = Integer.parseInt(guardDatabase.getAllGuards().get(position).getUserId());
                intent.putExtra("editedGuardId", guardId);
                startActivity(intent);
            }
        });
    }
}
