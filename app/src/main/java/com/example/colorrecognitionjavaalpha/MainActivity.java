package com.example.colorrecognitionjavaalpha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentContainerView;

import android.app.Fragment;
import android.app.FragmentContainer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        MenuFragment menuFragment = new MenuFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainerView, menuFragment, MenuFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i("TAG", "#### onResume");
//
//        String frag1 = MenuFragment.class.getSimpleName();
//        String frag2 = IntroductionFragment.class.getSimpleName();
//        String frag3 = BeginnerFragment.class.getSimpleName();
//        String frag4 = MateriFragment.class.getSimpleName();
//
//        Log.i("TAG", "#### frag1: "+ frag1);
//        Log.i("TAG", "#### frag1: "+ frag2);
//        Log.i("TAG", "#### frag1: "+ frag3);
//        Log.i("TAG", "#### frag1: "+ frag4);
//
//
////        getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
//        Log.i("TAG", "#### ID: "+ getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView));
//
//        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(frag1);
//        if (menuFragment != null && menuFragment.isVisible()) {
//            Log.i("TAG", "#### IS VISIBLE");
//        } else
//            Log.i("TAG", "#### NOT VISIBLE XXXX");
//
//        if (Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(MenuFragment.class.getSimpleName())).isVisible()) {
//            Log.i("TAG", "#### LAGI DI MENU");
//        }else {
//            Log.i("TAG", "#### LAGI GA DI MENU XXXX");
//        }
//    }
//
//    @Override
//    protected void onResumeFragments() {
//        super.onResumeFragments();
//        Log.i("TAG", "#### LOL1");
//        Log.i("TAG", "#### ID: "+ getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView));
//    }
//
//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        super.onAttachFragment(fragment);
//        Log.i("TAG", "#### LOL2");
//        Log.i("TAG", "#### ID: "+ getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView));
//    }
}