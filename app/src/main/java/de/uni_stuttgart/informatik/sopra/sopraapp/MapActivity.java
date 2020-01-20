package de.uni_stuttgart.informatik.sopra.sopraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import static de.uni_stuttgart.informatik.sopra.sopraapp.DrawActivity.stringToBitmap;
import static de.uni_stuttgart.informatik.sopra.sopraapp.TouchListener.circleRadius;

public class MapActivity extends AppCompatActivity {
    DrawingView drawingView;
    Button btnBackToPatrolRef;
    private static Route actualRoute;

    public static Route getActualRoute(){
        if(actualRoute!=null) {
            return actualRoute;
        }else{
            return null;
        }
    }
    public Bitmap getBitmapFromPreferences(Context context){
        SharedPreferences mPrefs = getSharedPreferences("saved_bitmap",0);
        String str = mPrefs.getString("Save", "");
        return stringToBitmap(str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        actualRoute = (Route) getIntent().getExtras().get("route");
        btnBackToPatrolRef = findViewById(R.id.btnBackToPatrol);
        drawingView = (DrawingView) findViewById(R.id.canvas);
        drawingView.setWaypoints(actualRoute.getOnlyWaypoints());
        drawingView.setDrawRouteOnMap(true);


        if(getBitmapFromPreferences(this)!= null){
            if(!getBitmapFromPreferences(this).equals("")) {
                drawingView.setBitmap(getBitmapFromPreferences(this));
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),"Admin need to set a Map first", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }

        btnBackToPatrolRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.setDrawRouteOnMap(false);
                finish();
            }
        });
    }

    private static void drawCostumText(Canvas canvas, Circle circle){
        Paint paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(circle.paint.getColor());
        canvas.drawText("9",circle.cx - circle.radius/2,circle.cy + (3*circle.radius)/4, paint);
    }
}
