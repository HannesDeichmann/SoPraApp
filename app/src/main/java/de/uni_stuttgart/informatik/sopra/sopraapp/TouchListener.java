package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class TouchListener implements View.OnTouchListener {
    Circle circle;
    public static final int circleRadius = 30;
    public static final Paint circlePaint = getPaint();

    private static Paint getPaint(){
        Paint paint;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        return paint;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        DrawingView drawingView = (DrawingView) view;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                circle = new Circle(x,y,circleRadius,getPaint());
                drawingView.addCurentCircle(circle);
                break;
        }
        drawingView.invalidate();
        return true;
    }
}
