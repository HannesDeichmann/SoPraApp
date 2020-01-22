package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static de.uni_stuttgart.informatik.sopra.sopraapp.DrawActivity.stringToBitmap;

public class MapFragment extends Fragment {

    DrawingView drawingView;
    private static Route actualRoute;

    public static MapFragment newInstance(Route route, String str){
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("route", route);
        bundle.putString("str", str);
        fragment.setArguments(bundle);

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container,false);
        actualRoute = (Route) getArguments().getSerializable("route");
        System.out.println(actualRoute.getWaypoints().size());
        System.out.println(actualRoute.getWaypointStrings().size());
        actualRoute = (Route) getArguments().getSerializable("route");
        drawingView = (DrawingView) view.findViewById(R.id.canvas);
        drawingView.setWaypoints(actualRoute.getOnlyWaypoints());
        drawingView.setDrawRouteOnMap(true);

        String str = getArguments().getString("str");

        if(getBitmapFromPreferences(str)!= null) {
            if (!getBitmapFromPreferences(str).equals("")) {
                drawingView.setBitmap(getBitmapFromPreferences(str));
            }
        }


        return view;
    }

    public static Route getActualRoute(){
        if(actualRoute!=null) {
            return actualRoute;
        }else{
            return null;
        }
    }
    public Bitmap getBitmapFromPreferences(String str){
        return stringToBitmap(str);
    }

    private static void drawCostumText(Canvas canvas, Circle circle){
        Paint paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(circle.paint.getColor());
        canvas.drawText("9",circle.cx - circle.radius/2,circle.cy + (3*circle.radius)/4, paint);
    }
}


