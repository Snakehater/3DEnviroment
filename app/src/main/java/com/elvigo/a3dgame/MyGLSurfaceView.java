package com.elvigo.a3dgame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    // Implementing on touch to record user inputs
//    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final float TOUCH_SCALE_FACTOR = 0.15f;
    private float previousX;
    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;

//                // reverse direction of rotation above the mid-line
//                if (y > getHeight() / 2) {
//                    dx = dx * -1 ;
//                }
//
//                // reverse direction of rotation to left of the mid-line
//                if (x < getWidth() / 2) {
//                    dy = dy * -1 ;
//                }

//                renderer.setAngleX(
//                        renderer.getAngle() -
//                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                renderer.setAngleX(
                        renderer.getAngleX() +
                                ((dx) * TOUCH_SCALE_FACTOR));
                renderer.setAngleY(
                        renderer.getAngleY() -
                                ((dy) * TOUCH_SCALE_FACTOR));

                requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }
    // Ends here
}