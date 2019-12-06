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

public class ScheduleGuardListActivity extends AppCompatActivity {

    Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_guard_list);

        DatabaseGuard guardDatabase = new DatabaseGuard(this);
        ListView listView = findViewById(R.id.guardList);

        ArrayList<String> guardStringList = new ArrayList<>();

        if(guardDatabase.getGuardCount()>0) {
            for (Guard guard : guardDatabase.getAllGuards()) {
                guardStringList.add(guard.toString());
            }
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
                Intent intent = new Intent(view.getContext(), ScheduleActivity.class);
                Guard selectedGuard = guardDatabase.getAllGuards().get(position);
                intent.putExtra("selectedGuard", selectedGuard);
                startActivity(intent);
                finish();
            }
        });
    }
}
