package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ProtocolActivity extends AppCompatActivity {
    Boolean access;
    Button btnExport;
    RecyclerView tvProtocol;
    DatabasePatrol databasePatrol;
    RecyclerView.Adapter dataAdapter;
    RecyclerView.LayoutManager rvManagerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        tvProtocol = findViewById(R.id.protocol);
        btnExport = findViewById(R.id.btnExport);
        databasePatrol = new DatabasePatrol(this);
        ProtocolAdapter.databasePatrol = databasePatrol;
        rvManagerRef = new LinearLayoutManager(this);
        tvProtocol.setLayoutManager(rvManagerRef);
        dataAdapter = new ProtocolAdapter();
        tvProtocol.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();

        if (ContextCompat.checkSelfPermission(ProtocolActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProtocolActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            writeFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("onRequestPermissionsResult: EXTERNAL_STORAGE_WRITE permission granted");
                    writeFile();
                } else {
                    System.out.println("onRequestPermissionsResult: EXTERNAL_STORAGE_WRITE permission denied");
                }
                break;
            default:
                break;
        }
    }

    public void writeFile() {
        try {
            File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/Protocol/Protocol" + System.currentTimeMillis() + ".csv");
            if (!folder.exists()) {
                folder.getParentFile().mkdirs();
                folder.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(folder));
            StringBuilder stringBuilder = new StringBuilder();
            addFirstRow(stringBuilder);
            addAllPatrols(stringBuilder);
            out.write(stringBuilder.toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAllPatrols(StringBuilder stringBuilder){
        for(int i = 0; i < databasePatrol.getPatrolCount(); i++){
            stringBuilder.append(databasePatrol.getPatrol(i));
            stringBuilder.append("\n");
        }
    }

    private void addFirstRow(StringBuilder stringBuilder){
        stringBuilder.append("Number; Route; Guard; planned Start; actual Start");
        for(int i = 1; i <= this.getMaxWaypoints(); i++) {
            stringBuilder.append(";WP" + i);
        }
        stringBuilder.append("; End");
        stringBuilder.append("\n");
    }

    private int getMaxWaypoints(){
        int max = 0;
        for(int i = 0; i< databasePatrol.getPatrolCount(); i++){
            if(getWaypointList(i).size() > max){
                max = getWaypointList(i).size();
            }
        }
        return max;
    }

    private ArrayList<String> getWaypointList(int position){
        ArrayList<String> waypoints = new ArrayList<>();
        String[] stringArray =  databasePatrol.getPatrol(position).split(";");
        for(String s: stringArray){
            waypoints.add(s);
        }
        for(int i = 0; i < 4;i++){
            waypoints.remove(i);
        }
        return waypoints;
    }
}