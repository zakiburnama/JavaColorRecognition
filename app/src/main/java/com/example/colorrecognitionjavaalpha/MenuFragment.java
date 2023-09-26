package com.example.colorrecognitionjavaalpha;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MenuFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnNotation = view.findViewById(R.id.button_notation);
        btnNotation.setOnClickListener(this::onClick);
        Button btnBeginner = view.findViewById(R.id.button_beginner);
        btnBeginner.setOnClickListener(this::onClick);
        Button btnIntermediate = view.findViewById(R.id.button_intermediate);
        btnIntermediate.setOnClickListener(this::onClick);

        CardView cardIntroduction = view.findViewById(R.id.card_introduction);
        cardIntroduction.setOnClickListener(this::onClick);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_introduction:
            case R.id.button_notation:
                showPopup(view);
                break;
            case R.id.button_beginner:
                BeginnerFragment beginnerFragment = new BeginnerFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, beginnerFragment, BeginnerFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_intermediate:
                Toast.makeText(getContext(),"Not Avaiable Yet", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void showPopup(View view) {
//        View popupView = View.inflate(getContext(), R.layout.popup_action, null);

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_action, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Set color etc
        ImageView imageViewPop = popupView.findViewById(R.id.iv_popup_icon);
        imageViewPop.setImageResource(R.drawable.monster2);
        Button buttonPopStart = popupView.findViewById(R.id.button_mulai);
        buttonPopStart.setBackgroundColor(R.color.blue);
        buttonPopStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Not Avaiable Yet", Toast.LENGTH_SHORT).show();
            }
        });

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });

    }
}