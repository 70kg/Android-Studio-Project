package com.com.mr_wrong.Bezier;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Wrong on 15/7/20.
 */
public class PathAnimation extends View {
    private Path mPath;
    private Paint mPaint;
    private List<Fllower> fllowers = new ArrayList<>();
    private float phase;


    public PathAnimation(Context context) {
        super(context);

       // WindowManager wm = getw
    }



    class  Fllower{
        private float x;
        private float y;
        private Path path;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        private float value;

    }
}
