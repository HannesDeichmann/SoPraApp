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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.graphics.Color;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import static de.uni_stuttgart.informatik.sopra.sopraapp.TouchListener.circleRadius;

public class DrawActivity extends AppCompatActivity {
    DrawingView drawingView;
    Button btnSaveRef;
    Button btnPickImgRef;
    DatabaseWaypoint databaseWaypoint = new DatabaseWaypoint((this));
    private final static int GALLERY_REQUEST_CODE = 1;
    public static int BITMAP_WIDTH = 500;
    public static int BITMAP_HEIGHT = 600;
    public static Waypoint waypoint;

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
    public void setBitmapToPreferences(String value, Context context){
        SharedPreferences mPrefs = getSharedPreferences("saved_bitmap",0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("Save", value);
        editor.commit();
    }

    public Bitmap getBitmapFromPreferences(Context context){
        SharedPreferences sPreferences = getSharedPreferences("saved_bitmap",0);
        return stringToBitmap(sPreferences.getString("Save", ""));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            Uri imageUri = data.getData();
            setBitmapToPreferences(bitmapToString(uriToBitmap(imageUri)),this);
            drawingView.setBitmap(uriToBitmap(imageUri));
        }
    }

    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap stringToBitmap(String string){
        try {
            byte [] encodeByte=Base64.decode(string,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private Bitmap uriToBitmap(Uri selectedFileUri) {
        Bitmap image = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            BITMAP_WIDTH = image.getWidth();
            BITMAP_HEIGHT = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return image;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        btnSaveRef = findViewById(R.id.btnSaveMap);
        btnPickImgRef = findViewById(R.id.btnPickImg);
        drawingView = (DrawingView) findViewById(R.id.canvas);
        drawingView.setOnTouchListener(new TouchListener());
        drawingView.setWaypoints(databaseWaypoint.getAllWaypoints());

        if(getBitmapFromPreferences(this)!= null){
            if(!getBitmapFromPreferences(this).equals("")) {
                drawingView.setBitmap(getBitmapFromPreferences(this));
            }
        }

        if(drawingView.getBitmap() == null){
            drawingView.setBitmap(Bitmap.createBitmap(DrawActivity.BITMAP_WIDTH, DrawActivity.BITMAP_HEIGHT,
                    Bitmap.Config.ARGB_8888));
            drawingView.invalidate();
        }
        waypoint = (Waypoint) getIntent().getExtras().get("waypoint");
        if(waypoint.getWaypointPosition().length() > 5) {
            String pos = waypoint.getWaypointPosition();
            String[] splitted = pos.split(";");
            Circle circle = new Circle(Float.valueOf(splitted[0]), Float.valueOf(splitted[1]),
                    circleRadius, TouchListener.circlePaint);
            drawingView.addCurentCircle(circle);
        }else {
            Toast.makeText(getApplicationContext(),"Waypoint has no Position yet", Toast.LENGTH_SHORT).show();
        }
        btnPickImgRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(DrawActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(DrawActivity.this, permissions, GALLERY_REQUEST_CODE);
                }else {
                    openGallery();
                }
            }
        });




        btnSaveRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawingView.getBitmap()== null){
                    Toast toast = Toast.makeText(getApplicationContext(),"Add Bitmap first", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (drawingView.getLastCircle() != null) {
                    String location = Float.valueOf(drawingView.getLastCircle().cx).toString()+";"+
                            Float.valueOf(drawingView.getLastCircle().cy).toString();

                    waypoint.setWaypointPosition(location);

                    Intent intent = new Intent(view.getContext(), WaypointActivity.class);
                    intent.putExtra("wpLocation", waypoint);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"There is no last Circle", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("onRequestPermissionsResult: EXTERNAL_STORAGE_READ permission granted--------------------------------------------------------------------------------------------------");
                    openGallery();
                } else {
                    System.out.println("onRequestPermissionsResult: EXTERNAL_STORAGE_READ permission denied-------------------------------------------------------------------------------------------------------------");
                }
                break;
            default:
                break;
        }
    }
}