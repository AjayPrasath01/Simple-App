package com.gmail.icalculator;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class GestureListener implements View.OnTouchListener{

    private GestureDetector gesture;
    private int thresold;
    private int velocity_thresold;

    GestureListener(View view){
        thresold = 100;
        velocity_thresold = 50;
        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float xDiff = e1.getX() - e2.getX();
                float yDiff = e1.getY() - e2.getY();

                if ((Math.abs(xDiff) > thresold) && Math.abs(velocityX) > velocity_thresold){
                    if (xDiff < 0){
                        String value = MainActivity.resultbox.getText().toString();
                        if (value.length() > 1) {
                            value = value.substring(0, value.length() - 1);
                        }else if (value.length() == 1) {
                            value = "0";
                        }
                        MainActivity.resultbox.setText(value);
                    }
                }
                return true;
//        return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        };
        gesture = new GestureDetector(listener);
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gesture.onTouchEvent(event);
    }
}
