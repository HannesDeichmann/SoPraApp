package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.graphics.Paint;

/**
 * Class to draw circles of waypoints on the Map
 *
 * @author Gabriel Bonnet 3410781
 */
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
