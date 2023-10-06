package com.example.colorrecognitionjavaalpha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class CameraActivity extends org.opencv.android.CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private Mat mRgba;  // Input frame
    private Mat mHsv;  // Frame converted to HSV color space
    private Mat mMask;  // Binary mask for color detection

    private Canvas canvas;

    private Button btnNext;

    char[][] warnaRubik = new char[3][3];
    char[][][] warnaSisiRubik = new char[6][3][3];

    boolean rightIsScanned = false, leftIsScanned = false,
            frontIsScanned = false, backIsScanned = false,
            upIsScanned = false, downIsScanned = false;

    private String sunflower = new String(), whiteCross = new String(),
            whiteCorners = new String(), secondLayer = new String(),
            yellowCross = new String(), OLL = new String(), PLL = new String();

    private String movesToPerform = new String(), movesPerformed = new String();

    private int phase = 0;
    private String phaseString;
    //Helps keep track of moves to perform, and allows for painting of moves
    private int movesIndex = 0;


    private Cube cube = new Cube();

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

        mOpenCVCamera = (CameraBridgeViewBase) findViewById(R.id.frame_surface);
        mOpenCVCamera.setVisibility(SurfaceView.VISIBLE);
        mOpenCVCamera.setCvCameraViewListener(this);

        //
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        //
        ImageView drawingImageView = (ImageView) this.findViewById(R.id.imageView2);
        Bitmap bitmap = Bitmap.createBitmap((int) width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        drawingImageView.setImageBitmap(bitmap);

        // Declare BUTTON
        Button btnCamera = findViewById(R.id.button_scan);
        btnCamera.setOnClickListener(this::onClick);
        Button btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(this::onClick);
        btnNext = findViewById(R.id.button_next);
        btnNext.setOnClickListener(this::onClick);
        btnNext.setVisibility(View.GONE);
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

        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mHsv = new Mat(height, width, CvType.CV_8UC3);
        mMask = new Mat(height, width, CvType.CV_8UC1);
        contours = new ArrayList<>();

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
    }

    @Override
    public void onCameraViewStopped(){
        mRgba.release();
        mHsv.release();
        mMask.release();
        contours.clear();
    }

    private char cekWarna(double hue, double saturation, double value) {
        char color = 'X';
        if (saturation < 70) {
            color = 'W';
        } else if (value < 70) {
            color = 'X';
        } else {
            if (hue < 20) {
                color = 'O';
            }  else if (hue < 64) {
                color = 'Y';
            } else if (hue < 90) {
                color = 'G';
            } else if (hue < 130) {
                color = 'B';
            } else {
                color = 'R';
            }
        }
        return color;
    }

    private void putWarna() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(0);

        int l, t,
                r, b,
                side=0;

        switch (warnaRubik[1][1]) {
            // LEFT = 0
            case 'R':
                l=10;   t=190-10;
                r=60;   b=240-10;
                side = 0;
                leftIsScanned = true;
                break;
            // UP = 1
            case 'Y':
                l=190;  t=10-10;
                r=240;  b=60-10;
                side = 1;
                upIsScanned = true;
                break;
            // FRONT = 2
            case 'G':
                l=190;  t=190-10;
                r=240;  b=240-10;
                side = 2;
                frontIsScanned = true;
                break;
            // BACK = 3
            case 'B':
                l=550;  t=190-10;
                r=600;  b=240-10;
                side = 3;
                backIsScanned = true;
                break;
            // RIGHT = 4
            case 'O':
                l=370;  t=190-10;
                r=420;  b=240-10;
                side = 4;
                rightIsScanned = true;
                break;
            // DOWN = 5
            case 'W':
                l=190;  t=370-10;
                r=240;  b=420-10;
                side = 5;
                downIsScanned = true;
                break;
            default:
                l=10;   t=10-10;
                r=60;   b=60-10;
                break;
        }

        int temptl=l, temptt=t,
                temptr=r, temptb=b;

        for (int i=0; i<3; i++) {
            if (i > 0) {
                temptt+=60;
                temptb+=60;
            }
            for (int j=0; j<3; j++) {
                warnaSisiRubik[side][i][j] = warnaRubik[i][j];
                switch (warnaRubik[i][j]) {
                    case 'R':
                        paint.setColor(Color.rgb(255,0,0));
                        break;
                    case 'B':
                        paint.setColor(Color.rgb(0,0,255));
                        break;
                    case 'G':
                        paint.setColor(Color.rgb(0,255,0));
                        break;
                    case 'Y':
                        paint.setColor(Color.rgb(255, 255,0));
                        break;
                    case 'O':
                        paint.setColor(Color.rgb(255,120,0));
                        break;
                    case 'W':
                        paint.setColor(Color.rgb(255,255,255));
                        break;
                    default:
                        paint.setColor(Color.rgb(0,0,0));
                        break;
                }
                canvas.drawRect(temptl, temptt, temptr, temptb, paint);
                temptl+=60;
                temptr+=60;
                if (j>1) {
                    temptl=l;
                    temptr=r;
                }
            }
        }

        if (rightIsScanned && leftIsScanned && upIsScanned && downIsScanned && frontIsScanned && backIsScanned) {
            btnNext.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.GONE);
        }
    }

    @Override
    public Mat onCameraFrame(@NonNull CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba = inputFrame.rgba();
// Make 9 Rect in middle camera
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

// Save Color
        Imgproc.cvtColor(mRgba, mHsv, Imgproc.COLOR_RGB2HSV);

        // LEFT
        double[] pixelHSVTL = mHsv.get(midHor, midVer+120-120);
        warnaRubik[0][0] = cekWarna(pixelHSVTL[0], pixelHSVTL[1], pixelHSVTL[2]);
        double[] pixelHSVML = mHsv.get(midHor, midVer+120);
        warnaRubik[1][0] = cekWarna(pixelHSVML[0], pixelHSVML[1], pixelHSVML[2]);
        double[] pixelHSVBL = mHsv.get(midHor, midVer+120+120);
        warnaRubik[2][0] = cekWarna(pixelHSVBL[0], pixelHSVBL[1], pixelHSVBL[2]);
        // MID
        double[] pixelHSVTM = mHsv.get(midHor-120, midVer+120-120);
        warnaRubik[0][1] = cekWarna(pixelHSVTM[0], pixelHSVTM[1], pixelHSVTM[2]);
        double[] pixelHSVMM = mHsv.get(midHor-120, midVer+120);
        warnaRubik[1][1] = cekWarna(pixelHSVMM[0], pixelHSVMM[1], pixelHSVMM[2]);
        double[] pixelHSVBM = mHsv.get(midHor-120, midVer+120+120);
        warnaRubik[2][1] = cekWarna(pixelHSVBM[0], pixelHSVBM[1], pixelHSVBM[2]);
        // RIGHT
        double[] pixelHSVTR = mHsv.get(midHor-120-120, midVer+120-120);
        warnaRubik[0][2] = cekWarna(pixelHSVTR[0], pixelHSVTR[1], pixelHSVTR[2]);
        double[] pixelHSVMR = mHsv.get(midHor-120-120, midVer+120);
        warnaRubik[1][2] = cekWarna(pixelHSVMR[0], pixelHSVMR[1], pixelHSVMR[2]);
        double[] pixelHSVBR = mHsv.get(midHor-120-120, midVer+120+120);
        warnaRubik[2][2] = cekWarna(pixelHSVBR[0], pixelHSVBR[1], pixelHSVBR[2]);

        // TODO Delete soon
        // Tulisan di MID
        double[] hsvValue = mHsv.get(midHor-120, midVer+120);
        double hue = hsvValue[0];
        double sat = hsvValue[1];
        double val = hsvValue[2];

        char warna = cekWarna(hue, sat, val);   // value is string

        Scalar colorPutih = new Scalar(255, 255, 255);

        Point pointText = new Point(midHor, midVer);
        Imgproc.putText(mRgba, String.valueOf(warna), pointText, 3, 1, colorPutih, 3);

        Point pointTextLeft = new Point(midHor, midVer+120);
        Imgproc.putText(mRgba, String.valueOf(hue), pointTextLeft, 3, 1, colorPutih, 3);

        //TODO value warna masih salah
        //Variabel Atas Bawah Kiri Kanan
        Scalar colorTop = new Scalar(0, 0, 0);
        Scalar colorBot = new Scalar(0, 0, 0);
        Scalar colorLef = new Scalar(0, 0, 0);
        Scalar colorRig = new Scalar(0, 0, 0);

        //Library Warna
        Scalar GREEN    = new Scalar(90, 255, 0);
        Scalar RED      = new Scalar(200, 0, 255);
        Scalar BLUE     = new Scalar(255, 0, 0);
        Scalar YELLOW   = new Scalar(60, 255, 255);
        Scalar ORANGE   = new Scalar(20, 128, 255);
        Scalar WHITE    = new Scalar(255, 255, 255);
        Scalar BLACK    = new Scalar(0, 0, 0);

        //Percabangan warna
        switch (warna) {
            case 'R':
                colorTop = YELLOW;
                colorBot = WHITE;
                colorLef = BLUE;
                colorRig = GREEN;
                break;
            case 'Y':
                colorTop = ORANGE;
                colorBot = RED;
                colorLef = BLACK;
                colorRig = GREEN;
                break;
            case 'G':
                colorTop = YELLOW;
                colorBot = WHITE;
                colorLef = RED;
                colorRig = ORANGE;
                break;
            case 'B':
                colorTop = YELLOW;
                colorBot = WHITE;
                colorLef = ORANGE;
                colorRig = RED;
                break;
            case 'O':
                colorTop = YELLOW;
                colorBot = WHITE;
                colorLef = GREEN;
                colorRig = BLUE;
                break;
            case 'W':
                colorTop = RED;
                colorBot = ORANGE;
                colorLef = BLUE;
                colorRig = GREEN;
                break;
            default:
                break;
        }

        // TOP
        Point pointTopA = new Point(midHor-120-50-50, midVer+120);
        Point pointTopB = new Point(midHor-120-50-50, midVer-120);
        Imgproc.line(mRgba, pointTopA, pointTopB, colorTop, 5);
        // BOT
        Point pointBotA = new Point(midHor+120+50+50, midVer+120);
        Point pointBotB = new Point(midHor+120+50+50, midVer-120);
        Imgproc.line(mRgba, pointBotA, pointBotB, colorBot, 5);
        // LEFT
        Point pointLefA = new Point(midHor-120, midVer+120+50+50);
        Point pointLefB = new Point(midHor+120, midVer+120+50+50);
        Imgproc.line(mRgba, pointLefA, pointLefB, colorLef, 5);
        // RIGHT
        Point pointRigA = new Point(midHor-120, midVer-120-50-50);
        Point pointRigB = new Point(midHor+120, midVer-120-50-50);
        Imgproc.line(mRgba, pointRigA, pointRigB, colorRig, 5);

        //
        return  mRgba;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(0);
        paint.setColor(Color.rgb(255,255,255));

        switch (view.getId()) {
            case R.id.button_scan:
                putWarna();
                break;
            case R.id.button_next:
                //TODO make intent to solution activity and send all data below
                cube.setAllColors(warnaSisiRubik);

                sunflower = cube.makeSunflower();
                whiteCross = cube.makeWhiteCross();
                whiteCorners = cube.finishWhiteLayer();
                secondLayer = cube.insertAllEdges();
                yellowCross = cube.makeYellowCross();
                OLL = cube.orientLastLayer();
                PLL = cube.permuteLastLayer();

                movesToPerform = sunflower;
                movesPerformed = new String();

                movesIndex = 0; phase = 0;
                phaseString = "Sunflower";

                Log.i("TAG", "#### MTP: " + movesToPerform);
                Log.i("TAG", "#### sunflower: " + sunflower);
                Log.i("TAG", "#### whiteCross: " + whiteCross);
                Log.i("TAG", "#### whiteCorners: " + whiteCorners);
                Log.i("TAG", "#### secondLayer: " + secondLayer);
                Log.i("TAG", "#### yellowCross: " + yellowCross);
                Log.i("TAG", "#### OLL: " + OLL);
                Log.i("TAG", "#### PLL: " + PLL);
                Log.i("TAG", "#### MP: " + movesPerformed);

                cube.setAllColors(warnaSisiRubik);

                Intent intent = new Intent(this, SolutionActivity.class);
                intent.putExtra("sunflower", sunflower);
                startActivity(intent);


//                canvas.drawText("awdad", 10, 600, paint);

                break;
            case R.id.button_back:
//                cube.showCube();
//                cube.testTurning();
//                paint.setTextSize(24);
//                canvas.drawText("awdad", 10, 600, paint);
//                Toast.makeText(this, "WWADA", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}