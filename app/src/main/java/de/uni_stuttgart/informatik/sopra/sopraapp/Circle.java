package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.graphics.Paint;

public class Circle {
    public float cx;
    public float cy;
    public int radius;
    public Paint paint;

    public Circle(float x, float y, int r, Paint p){
        cx = x;
        cy = y;
        radius = r;
        paint = p;
    }
}
