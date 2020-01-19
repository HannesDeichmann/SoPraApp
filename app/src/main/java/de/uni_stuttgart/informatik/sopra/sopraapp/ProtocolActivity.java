package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
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
    ListView tvProtocol;
    public static ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        tvProtocol = findViewById(R.id.protocol);
        //hardcode for examples

        list.add("1; Peter; 17:00; 18:00; false; 17:02; 17:13; 17:33; 17:42; 17:50; 18:00");
        list.add("1; Peter; 17:00; 18:00; false; 17:02; 17:13; 17:33; 17:42; 17:50; 18:00");
        list.add("1; Peter; 17:00; 18:00; false; 17:02; 17:13; 17:33; 17:42; 17:50; 18:00");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        tvProtocol.setAdapter(adapter);
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
            stringBuilder.append("Route; Guard; Start; End; Interrupted");
            for (int i = 0; i < 100; i++) {
                stringBuilder.append(";WP" + (i + 1));
            }
            stringBuilder.append("\n");
            for (int i = 0; i < list.size(); i++) {
                stringBuilder.append(list.get(i));
                stringBuilder.append("\n");
            }
            out.write(stringBuilder.toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}