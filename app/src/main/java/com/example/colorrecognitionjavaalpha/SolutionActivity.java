package com.example.colorrecognitionjavaalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SolutionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_solution);

        Intent intent = getIntent();
        String sunflower = intent.getStringExtra("sunflower");

        SolutionFragment solutionFragment = SolutionFragment.newInstance("sunflower", sunflower);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainerView2, solutionFragment, SolutionFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();

        TextView tvTittle = findViewById(R.id.tv_solusi_tittle);
        tvTittle.setText("sunflower");
        ImageView btnClose = findViewById(R.id.iv_solusi_back);
        btnClose.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        finish();
    }
}