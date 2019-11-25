package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GuardListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_list);

        ListView listView = findViewById(R.id.guardList);

        ArrayList<String> guardStringList = new ArrayList<>();

        for(Guard guard : Guard.getGuardList()){
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
                String string =  parent.getItemAtPosition(position).toString();
                intent.putExtra("deleteGuard", string);
                startActivity(intent);
            }
        });

    }
}
