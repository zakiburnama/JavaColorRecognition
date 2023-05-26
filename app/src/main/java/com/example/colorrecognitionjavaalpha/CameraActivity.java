package com.example.colorrecognitionjavaalpha;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CameraActivity extends org.opencv.android.CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private Mat mRgba;  // Input frame
    private Mat mHsv;  // Frame converted to HSV color space
    private Mat mMask;  // Binary mask for color detection

    private Button btn;

    private String warna;

    private Integer midHor, midVer;

    private Point point1, point2, point3, point4, point5, point6,
            pointTopLeft1, pointTopLeft2, pointMidLeft1, pointMidLeft2, pointBotLeft1, pointBotLeft2,
            pointTopRight1, pointTopRight2, pointMidRight1, pointMidRight2, pointBotRight1, pointBotRight2;

    private List<MatOfPoint> contours;  // Detected contours

    private static final String TAG = "CameraActivity";

    private CameraBridgeViewBase mOpenCVCamera;

    private final BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS) {
                Log.i(TAG, "opencv is loaded");
                mOpenCVCamera.enableView();
            }
            super.onManagerConnected(status);
        }
    };

    public CameraActivity(){
        Log.i(TAG, "CameraActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityCompat.requestPermissions(CameraActivity.this, new String[] {Manifest.permission.CAMERA}, 1);

        setContentView(R.layout.activity_camera);

        btn = findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), warna, Toast.LENGTH_SHORT).show();
            }
        });


        mOpenCVCamera = (CameraBridgeViewBase) findViewById(R.id.frame_surface);
        mOpenCVCamera.setVisibility(SurfaceView.VISIBLE);
        mOpenCVCamera.setCvCameraViewListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mOpenCVCamera.setCameraPermissionGranted();
                }else{
                    //permision denied
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug()){

            Log.i(TAG, "onResume");
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else{

            Log.i(TAG, "onResume Gagal");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallBack);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mOpenCVCamera != null){
            Log.i(TAG, "onPause");
            mOpenCVCamera.disableView();

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mOpenCVCamera != null){
            Log.i(TAG, "onDestroy");
            mOpenCVCamera.disableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height){
        midHor = width / 2;
        midVer = height / 2;

        // MID
        point1 = new Point(midHor-50, midVer-50);
        point2 = new Point(midHor+50, midVer+50);
        // MID TOP
        point3 = new Point(midHor-50-120, midVer-50);
        point4 = new Point(midHor+50-120, midVer+50);
        // MID BOT
        point5 = new Point(midHor-50+120, midVer-50);
        point6 = new Point(midHor+50+120, midVer+50);

        // TOP LEFT
        pointTopLeft1 = new Point(midHor-50-120, midVer-50+120);
        pointTopLeft2 = new Point(midHor+50-120, midVer+50+120);
        // MID LEFT
        pointMidLeft1 = new Point(midHor-50, midVer-50+120);
        pointMidLeft2 = new Point(midHor+50, midVer+50+120);
        // RIGHT LEFT
        pointBotLeft1 = new Point(midHor-50+120, midVer-50+120);
        pointBotLeft2 = new Point(midHor+50+120, midVer+50+120);

        // TOP RIGHT
        pointTopRight1 = new Point(midHor-50-120, midVer-50-120);
        pointTopRight2 = new Point(midHor+50-120, midVer+50-120);
        // MID RIGHT
        pointMidRight1 = new Point(midHor-50, midVer-50-120);
        pointMidRight2 = new Point(midHor+50, midVer+50-120);
        // RIGHT RIGHT
        pointBotRight1 = new Point(midHor-50+120, midVer-50-120);
        pointBotRight2 = new Point(midHor+50+120, midVer+50-120);

        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mHsv = new Mat(height, width, CvType.CV_8UC3);
        mMask = new Mat(height, width, CvType.CV_8UC1);
        contours = new ArrayList<>();
    }

    @Override
    public void onCameraViewStopped(){
        mRgba.release();
        mHsv.release();
        mMask.release();
        contours.clear();
    }

    private String cekWarna(double hue_value) {
        String color = "Undefined";
        if (hue_value < 9) {
            color = "RED";
        } else if (hue_value < 22) {
            color = "ORANGE";
        } else if (hue_value < 33) {
            color = "YELLOW";
        } else if (hue_value < 78) {
            color = "GREEN";
        } else if (hue_value < 131) {
            color = "BLUE";
        } else if (hue_value < 170) {
            color = "VIOLET";
        } else {
            color = "RED";
        }
        return color;
    }

    @Override
    public Mat onCameraFrame(@NonNull CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba = inputFrame.rgba();
//
        // MID TOP
        double[] pixelValue2 = mRgba.get(midHor-120, midVer+120-120);
        Scalar color2 = new Scalar(pixelValue2);
        Imgproc.rectangle (mRgba, point3, point4, color2, 5);
        // MID
        double[] pixelValue1 = mRgba.get(midHor-120, midVer+120);
        Scalar color1 = new Scalar(pixelValue1);
        Imgproc.rectangle (mRgba, point1, point2, color1, 5);
        // MID BOT
        double[] pixelValue3 = mRgba.get(midHor-120, midVer+120+120);
        Scalar color3 = new Scalar(pixelValue3);
        Imgproc.rectangle (mRgba, point5, point6, color3, 5);
//
        // TOP LEFT
        double[] pixelValueTopLeft = mRgba.get(midHor, midVer+120-120);
        Scalar colorTopLeft = new Scalar(pixelValueTopLeft);
        Imgproc.rectangle (mRgba, pointTopLeft1, pointTopLeft2, colorTopLeft, 5);
        // MID LEFT
        double[] pixelValueMidLeft = mRgba.get(midHor, midVer+120);
        Scalar colorMidLeft = new Scalar(pixelValueMidLeft);
        Imgproc.rectangle (mRgba, pointMidLeft1, pointMidLeft2, colorMidLeft, 5);
        // BOT LEFT
        double[] pixelValueBotLeft = mRgba.get(midHor, midVer+120+120);
        Scalar colorBotLeft = new Scalar(pixelValueBotLeft);
        Imgproc.rectangle (mRgba, pointBotLeft1, pointBotLeft2, colorBotLeft, 5);
//
        // TOP RIGHT
        double[] pixelValueTopRight = mRgba.get(midHor-120-120, midVer+120-120);
        Scalar colorTopRight = new Scalar(pixelValueTopRight);
        Imgproc.rectangle (mRgba, pointTopRight1, pointTopRight2, colorTopRight, 5);
        // MID RIGHT
        double[] pixelValueMidRight = mRgba.get(midHor-120-120, midVer+120);
        Scalar colorMidRight = new Scalar(pixelValueMidRight);
        Imgproc.rectangle (mRgba, pointMidRight1, pointMidRight2, colorMidRight, 5);
        // BOT RIGHT
        double[] pixelValueBotRight = mRgba.get(midHor-120-120, midVer+120+120);
        Scalar colorBotRight = new Scalar(pixelValueBotRight);
        Imgproc.rectangle (mRgba, pointBotRight1, pointBotRight2, colorBotRight, 5);


        Imgproc.cvtColor(mRgba, mHsv, Imgproc.COLOR_RGB2HSV);

        double[] hsvValue = mHsv.get(midHor-120, midVer+120);
        double vhsv = hsvValue[0];
        warna = cekWarna(vhsv);

        Point point = new Point(midHor, midVer);
        Imgproc.putText(mRgba, warna, point, 2, 3, color2, 4);



//        double blue = pixelValue1[0];
//        double green = pixelValue1[1];
//        double red = pixelValue1[2];
//
//        Log.i("TAG", "#### pixelValue1 " + Arrays.toString(pixelValue1));
//        Log.d("PixelValue", "#### RGB B: " + blue + ", G: " + green + ", R: " + red);
//        Log.i("TAG", "#### HSV " + hhsv);
//        Log.i("TAG", "#### WARNA " + warna);
//        Log.i("TAG", "#### getHSV " + Arrays.toString(mHsv.get(midHor, midVer)));


//        // Set the color range for detection (example: detecting blue color)
//        Scalar lowerBound = new Scalar(100, 100, 100);
//        Scalar upperBound = new Scalar(130, 255, 255);
//
//        // Convert to HSV color space
//        Imgproc.cvtColor(mRgba, mHsv, Imgproc.COLOR_RGB2HSV);
//
//        // Create a binary mask for color detection
//        Core.inRange(mHsv, lowerBound, upperBound, mMask);
//
//        // Apply morphological operations to remove noise
//        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
//        Imgproc.dilate(mMask, mMask, kernel);
//        Imgproc.erode(mMask, mMask, kernel);
//
//        // Find contours in the mask
//        Imgproc.findContours(mMask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//        Imgproc.drawContours(mRgba, contours, -1, new Scalar(0, 255, 0), 2); //
//
//        for (MatOfPoint m: contours) {
//            Rect r = Imgproc.boundingRect(m);
//            Imgproc.rectangle(mRgba, r, new Scalar(255, 0, 0), 2);
//        }
//
//        contours.clear();

        return  mRgba;
    }

}