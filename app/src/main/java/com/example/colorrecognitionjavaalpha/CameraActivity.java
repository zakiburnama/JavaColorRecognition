package com.example.colorrecognitionjavaalpha;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CameraActivity extends org.opencv.android.CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

//    private CameraBridgeViewBase mOpenCvCameraView;

    TextView ttextView;

    private Scalar lowerBound;  // Lower color range for detection
    private Scalar upperBound;  // Upper color range for detection

    private Mat mRgba;  // Input frame
    private Mat mHsv;  // Frame converted to HSV color space
    private Mat mMask;  // Binary mask for color detection

    private Integer midHor;
    private Integer midVer;

    private List<MatOfPoint> contours;  // Detected contours

    private static final String TAG = "CameraActivity";
//    private Mat mRgba;
    private Mat mGrey;
    private CameraBridgeViewBase mOpenCVCamera;

    private BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch(status){
                case LoaderCallbackInterface.SUCCESS:{
                    Log.i(TAG, "opencv is loaded");
                    mOpenCVCamera.enableView();
                }
                default:{
                    super.onManagerConnected(status);
                }
                break;
            }
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

        mOpenCVCamera = (CameraBridgeViewBase) findViewById(R.id.frame_surface);
        mOpenCVCamera.setVisibility(SurfaceView.VISIBLE);
        mOpenCVCamera.setCvCameraViewListener(this);

        // Set the color range for detection (example: detecting blue color)
        lowerBound = new Scalar(100, 100, 100);
        upperBound = new Scalar(255, 255, 255);

        ttextView = (TextView) findViewById(R.id.textView);
        ttextView.setText("WWW");
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
        Log.i(TAG, "onCameraViewStarted lebar = " + width + " tinggi = " + height );
//        mRgba = new Mat(height,width, CvType.CV_8UC4);
//        mGrey = new Mat(height,width, CvType.CV_8UC1);

        midHor = width / 2;
        midVer = height / 2;

        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mHsv = new Mat(height, width, CvType.CV_8UC3);
        mMask = new Mat(height, width, CvType.CV_8UC1);
        contours = new ArrayList<>();

    }
    @Override
    public void onCameraViewStopped(){
//        Log.i(TAG, "onCameraViewStopped ");
//        mRgba.release();

        mRgba.release();
        mHsv.release();
        mMask.release();
        contours.clear();
    }

    private String cekWarna(double hue_value) {
        String color = "Undefined";
        if (hue_value < 5) {
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
        Log.i(TAG, "onCameraFrame ");
        mRgba = inputFrame.rgba();
        mGrey = inputFrame.gray();

//        double[] pixelValue = mRgba.get(0, 0);
//        double[] nilaiHSV = mHsv.get(0, 0);
        double[] pixelValue = mRgba.get(midHor, midVer);
        double[] nilaiHSV = mHsv.get(midHor, midVer);

        double blue = pixelValue[0];
        double green = pixelValue[1];
        double red = pixelValue[2];

        Log.i("TAG", "#### mid " + midHor + " mid " + midVer);
        Log.d("PixelValue", "#### RGB B: " + blue + ", G: " + green + ", R: " + red);

        // Convert to HSV color space
        Imgproc.cvtColor(mRgba, mHsv, Imgproc.COLOR_RGB2HSV);

        double hhsv = nilaiHSV[0];
        String warna = cekWarna(hhsv);

//        ttextView.setText("warna");

        Log.i("TAG", "#### HSV " + hhsv);
        Log.i("TAG", "#### WARNA " + warna);

        Log.i("TAG", "#### getHSV " + Arrays.toString(mHsv.get(midHor, midVer)));


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
//        // Filter and draw contours on the original frame
//        for (MatOfPoint contour : contours) {
//            double contourArea = Imgproc.contourArea(contour);
//            if (contourArea > 1000) {  // Example filter based on contour area
//                Imgproc.drawContours(mRgba, contours, -1, new Scalar(0, 255, 0), 2);
//            }
//        }

//        return  mGrey;
        return  mRgba;
    }

}