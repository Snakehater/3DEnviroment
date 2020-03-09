package com.elvigo.a3dgame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.elvigo.a3dgame.objects.Cube;
import com.elvigo.a3dgame.objects.Square;
import com.elvigo.a3dgame.objects.Triangle;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;
    private Cube mCube;

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // initialize a triangle
        mTriangle = new Triangle();
        // initialize a square
        mSquare = new Square();
        // initialize a cube
        mCube = new Cube();

        Matrix.setIdentityM(mRotationMatrix, 0);

        gl.glEnable(GL10.GL_CULL_FACE);
//        gl.glCullFace(GL10.GL_FRONT);
    }

    private float[] rotationMatrix = new float[16];
    @Override
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        mSquare.draw();

//        // Matrix stuff for projection on 3d graphics
//        // Set the camera position (View matrix)
//        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
//
//        // Calculate the projection and view transformation
//        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
//
//        // Draw shape
//        // Rotating stuff
//
//        float[] scratch = new float[16];
//
//        // Create a rotation for the triangle
//        // long time = SystemClock.uptimeMillis() % 4000L;
//        // float angle = 0.090f * ((int) time);
//        Matrix.setRotateM(rotationMatrix, 0, mAngle, 0, 0, -1.0f);
//
//        // Combine the rotation matrix with the projection and camera view
//        // Note that the vPMatrix factor *must be first* in order
//        // for the matrix multiplication product to be correct.
//        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);
//
//        // Draw triangle
//        mSquare.draw(scratch);
//
//        // Ends here

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -1, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // zoom out a bit
        Matrix.translateM(vPMatrix, 0, 0, 0, 4.5f);


        int angleOffset = 1;
        // update the angles for the x and y rotation
//        setAngle(getAngle() - angleOffset);
//        setAngle(180f);
        // rotate and draw
        rotate();
        mCube.draw(vPMatrix);
    }

    private void rotate() {
        float[] animationMatrix = new float[16];
        Matrix.setIdentityM(animationMatrix, 0);

        // rotate in x and y direction, apply that to the intermediate matrix
        Matrix.rotateM(animationMatrix, 0, getAngleX(), 0, 1f, 0);
        Matrix.rotateM(animationMatrix, 0, getAngleY(), 1f, 0, 0);

        // concatenate the animation and the rotation matrix; the order is important
        Matrix.multiplyMM(animationMatrix, 0, animationMatrix, 0, mRotationMatrix, 0);

        Matrix.multiplyMM(vPMatrix, 0, vPMatrix, 0, animationMatrix, 0);
    }

    private void rotate(float angle, float x, float y, float z) {
        float[] animationMatrix = new float[16];
        Matrix.setIdentityM(animationMatrix, 0);

        // rotate in x and y direction, apply that to the intermediate matrix
        Matrix.rotateM(animationMatrix, 0, angle, x, y, z);

        // concatenate the animation and the rotation matrix; the order is important
        Matrix.multiplyMM(animationMatrix, 0, animationMatrix, 0, mRotationMatrix, 0);

        Matrix.multiplyMM(vPMatrix, 0, vPMatrix, 0, animationMatrix, 0);
    }

    private final float[] mRotationMatrix = new float[16];

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];



    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    // Compiling GLSL (OpenGl Shading Language)
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    // Rotating functions

    public volatile float mFactorX = 0;
    public volatile float mFactorY = 0;
    public volatile float mAngleX = 0;
    public volatile float mAngleY = 0;

    public float getAngleX() {
        return mAngleX;
    }

    public float getAngleY() {
        return mAngleY;
    }
//
//    public float getFactorX(){
//        return mFactorX;
//    }
//
//    public float getFactorY() {
//        return mFactorY;
//    }

    public void setAngleX(float angle) {
        mAngleX = angle;
    }

    public void setAngleY(float angle) {
        mAngleY = angle;
    }
//
//    public void setFactorX(float factor) {
//        mFactorX = factor;
//    }
//
//    public void setFactorY(float factor) {
//        mFactorY = factor;
//    }

    // Ends here
}