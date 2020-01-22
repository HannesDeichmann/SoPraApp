package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class DrawingView extends View {
    private static Bitmap bitmap;
    private ArrayList<Waypoint> waypoints = new ArrayList<>();
    private static Circle circle;
    public DrawingView(Context context) {
        super(context);
    }
    private static boolean drawRouteOnMap = false;
    private static ArrayList<Waypoint> doneWaypoints = new ArrayList<>();

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setDrawRouteOnMap(Boolean boo){
        drawRouteOnMap = boo;
    }

    public static void setDoneWaypoints(ArrayList<Waypoint> list){ doneWaypoints = list; }

    public static void addDoneWaypoint(Waypoint wp){ doneWaypoints.add(wp);}

    public void addCurentCircle(Circle circle) {
        this.circle = circle;
    }

    public void setWaypoints(ArrayList<Waypoint> waypointList) {
        this.waypoints = waypointList;
    }

    public Circle getLastCircle() {
        if (this.circle != null) {
            return this.circle;
        }
        return null;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if(!drawRouteOnMap) {
            if (this.circle != null) {
                canvas.drawCircle(circle.cx, circle.cy, circle.radius, circle.paint);
            }
            drawOldWaypoints(canvas);
        }else {
            drawRoute(MapFragment.getActualRoute(), canvas);
        }
    }
    private void drawOldWaypoints(Canvas canvas){
            for (Waypoint waypoint : waypoints) {
                if (!waypoint.getWaypointId().equals(DrawActivity.waypoint.getWaypointId()))
                    if (waypoint.getWaypointPosition() != null) {
                        if (waypoint.getWaypointPosition().length() > 5) {
                            canvas.drawCircle(waypoint.getXKoordinate(), waypoint.getYKoordinate()
                                    , TouchListener.circleRadius / 2, TouchListener.circlePaint);
                        }
                    }
            }
    }

    private void drawRoute(Route route, Canvas canvas){

        ArrayList<Waypoint> waypointList = new ArrayList<>();
        if(route.getWaypointStrings()!= null && route.getWaypoints()!=null){
            if(route.getWaypointStrings().size() <= route.getWaypoints().size()){
                for(RouteWaypoint rwp: route.getWaypoints()){
                    waypointList.add(rwp.getWaypoint());
                }
            } else {
                for(RouteWaypointStrings rwps: route.getWaypointStrings()){
                    for(Waypoint wp: waypoints){
                        if(wp.getWaypointId().equals(rwps.getUserId())){
                            waypointList.add(wp);
                        }
                    }
                }
            }
        }
        /////////////////////////////////////TESSSST
        //this.addDoneWaypoint(waypointList.get(0));
        //this.addDoneWaypoint(waypointList.get(1));
        //////////////////////////////////////////
        Waypoint lastwp = new Waypoint();
        Paint wpPaint = getRoutePaint();
        Paint routePaint = getRoutePaint();
        for (Waypoint wp:waypointList) {
            if(this.doneWaypoints.contains(wp)){
                wpPaint.setColor(Color.GREEN);
                if(this.doneWaypoints.contains(lastwp)){
                    routePaint.setColor(Color.GREEN);
                } else {
                    routePaint = getRoutePaint();
                }
            } else {
                wpPaint = getRoutePaint();
                routePaint = getRoutePaint();
            }
            canvas.drawCircle(wp.getXKoordinate(),wp.getYKoordinate(),TouchListener.circleRadius/2, wpPaint);
            if(!lastwp.getWaypointPosition().equals("")){
                canvas.drawLine(lastwp.getXKoordinate(),lastwp.getYKoordinate(),wp.getXKoordinate(),wp.getYKoordinate(), routePaint);
            }
            lastwp = wp;
        }
        for (Waypoint wp:waypointList) {
            drawWaypointText(wp,canvas);
        }
    }
    private void drawWaypointText(Waypoint wp, Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setTextSize(50);
        canvas.drawText(wp.getWaypointName(),wp.getXKoordinate()- TouchListener.circleRadius,
                wp.getYKoordinate()-TouchListener.circleRadius,paint);
    }

    private Paint getRoutePaint(){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        return paint;
    }
}
