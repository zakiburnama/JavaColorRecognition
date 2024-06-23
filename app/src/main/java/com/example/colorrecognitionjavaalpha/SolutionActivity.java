package com.example.colorrecognitionjavaalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SolutionActivity extends AppCompatActivity {

    private String sunflower, cross, firstLayer, secondLayer,yellowCross, OLL, PLL;
    private TextView tvTitle;
    private ImageView btnClose;
    private ImageButton btnSunflower, btnCross, btn1L, btn2L, btnYellowCross, btnOLL, btnPLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_solution);

        // Get all algorithm
        Intent intent = getIntent();
        sunflower = intent.getStringExtra("sunflower");
        cross = intent.getStringExtra("whiteCross");
        firstLayer = intent.getStringExtra("whiteCorners");
        secondLayer = intent.getStringExtra("secondLayer");
        yellowCross = intent.getStringExtra("yellowCross");
        OLL = intent.getStringExtra("OLL");
        PLL = intent.getStringExtra("PLL");

        // Set title
        tvTitle = findViewById(R.id.tv_solusi_tittle);
        tvTitle.setText("sunflower");

        // declare all button
        btnClose = findViewById(R.id.iv_solusi_back);
        btnSunflower = findViewById(R.id.button_solution_1);
        btnCross = findViewById(R.id.button_solution_2);
        btn1L = findViewById(R.id.button_solution_3);
        btn2L = findViewById(R.id.button_solution_4);
        btnYellowCross = findViewById(R.id.button_solution_5);
        btnOLL = findViewById(R.id.button_solution_6);
        btnPLL = findViewById(R.id.button_solution_7);

        // highlight button
        clearButton();
        btnSunflower.setBackgroundColor(Color.CYAN);

        // set button to onClick
        btnClose.setOnClickListener(this::onClick);
        btnSunflower.setOnClickListener(this::onClick);
        btnCross.setOnClickListener(this::onClick);
        btn1L.setOnClickListener(this::onClick);
        btn2L.setOnClickListener(this::onClick);
        btnYellowCross.setOnClickListener(this::onClick);
        btnOLL.setOnClickListener(this::onClick);
        btnPLL.setOnClickListener(this::onClick);

        // add fragment to frag container to desplay
        SolutionFragment solutionFragment = SolutionFragment.newInstance("sunflower", sunflower);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainerView2, solutionFragment, SolutionFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    public void onClick(View view) {
        String param1;
        String param2;

        clearButton();

        switch (view.getId()) {
            case R.id.iv_solusi_back:
                finish();
                break;
            case R.id.iv_solusi_info:
                IntroductionFragment introductionFragment = new IntroductionFragment().newInstance("solution", "tes");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView2, introductionFragment, IntroductionFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_solution_1:
                param1 = "sunflower";
                param2 = sunflower;
                btnSunflower.setBackgroundColor(Color.CYAN);
                movetosolution(param1, param2);
                break;
            case R.id.button_solution_2:
                param1 = "cross";
                param2 = cross;
                btnCross.setBackgroundColor(Color.CYAN);
                movetosolution(param1, param2);
                break;
            case R.id.button_solution_3:
                param1 = "first layer";
                param2 = firstLayer;
                btn1L.setBackgroundColor(Color.CYAN);
                movetosolution(param1, param2);
                break;
            case R.id.button_solution_4:
                param1 = "second layer";
                param2 = secondLayer;
                btn2L.setBackgroundColor(Color.CYAN);
                movetosolution(param1, param2);
                break;
            case R.id.button_solution_5:
                param1 = "yellow cross";
                param2 = yellowCross;
                btnYellowCross.setBackgroundColor(Color.CYAN);
                movetosolution(param1, param2);
                break;
            case R.id.button_solution_6:
                param1 = "OLL";
                param2 = OLL;
                btnOLL.setBackgroundColor(Color.CYAN);
                movetosolution(param1, param2);
                break;
            case R.id.button_solution_7:
                param1 = "PLL";
                param2 = PLL;
                btnPLL.setBackgroundColor(Color.CYAN);
                movetosolution(param1, param2);
                break;
        }

//        tvTitle.setText(param1);
//
//        SolutionFragment solutionFragment = SolutionFragment.newInstance(param1, param2);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragmentContainerView2, solutionFragment, MateriFragment.class.getSimpleName())
//                .addToBackStack(null)
//                .commit();
    }

    private void movetosolution(String param1, String param2){
        tvTitle.setText(param1);
        SolutionFragment solutionFragment = SolutionFragment.newInstance(param1, param2);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView2, solutionFragment, MateriFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    // To clear button color background when pressed
    private void clearButton() {
        btnSunflower.setBackgroundColor(Color.TRANSPARENT);
        btnCross.setBackgroundColor(Color.TRANSPARENT);
        btn1L.setBackgroundColor(Color.TRANSPARENT);
        btn2L.setBackgroundColor(Color.TRANSPARENT);
        btnYellowCross.setBackgroundColor(Color.TRANSPARENT);
        btnOLL.setBackgroundColor(Color.TRANSPARENT);
        btnPLL.setBackgroundColor(Color.TRANSPARENT);
    }
}