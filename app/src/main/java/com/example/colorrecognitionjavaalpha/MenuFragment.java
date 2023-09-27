package com.example.colorrecognitionjavaalpha;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

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

        MaterialCardView cardIntroduction = view.findViewById(R.id.card_introduction);
        cardIntroduction.setOnClickListener(this::onClick);
        MaterialCardView cardIBeginner = view.findViewById(R.id.card_beginner);
        cardIBeginner.setOnClickListener(this::onClick);
        MaterialCardView cardIntermediate = view.findViewById(R.id.card_intermediate);
        cardIntermediate.setOnClickListener(this::onClick);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_introduction:
                showPopup(view, 1);
                break;
            case R.id.button_notation:
                moveToIntroduction();
                break;
            case R.id.card_beginner:
                showPopup(view, 2);
                break;
            case R.id.button_beginner:
                moveToBeginner();
                break;
            case R.id.card_intermediate:
                showPopup(view, 3);
                break;
            case R.id.button_intermediate:
                Toast.makeText(getContext(),"Not Avaiable Yet", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void moveToIntroduction() {
        IntroductionFragment introductionFragment = new IntroductionFragment();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, introductionFragment, IntroductionFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    private void moveToBeginner() {
        BeginnerFragment beginnerFragment = new BeginnerFragment();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, beginnerFragment, BeginnerFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @SuppressLint("ResourceAsColor")
    private void showPopup(View view, int level) {
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

        //
        int flag = 0;
        String popTittle = "Pengenalan "+ flag +"/3";
        String popDesc = "Tahap mengenal rubik";

        //Set color etc
        ImageView imageViewPop = popupView.findViewById(R.id.iv_popup_icon);
        MaterialButton buttonPopStart = popupView.findViewById(R.id.button_mulai);
        TextView textViewPopTitle = popupView.findViewById(R.id.tv_popup_title);
        TextView textViewPopDesc = popupView.findViewById(R.id.tv_popup_desc);

        if (level == 1) {
            buttonPopStart.setBackgroundColor(R.color.green2);
            imageViewPop.setImageResource(R.drawable.monster1);
            popTittle = "Pengenalan "+ flag +"/3";
            popDesc = "Tahap mengenal rubik";
        }else if (level == 2) {
            buttonPopStart.setBackgroundColor(R.color.blue);
            imageViewPop.setImageResource(R.drawable.monster2);
            popTittle = "Layer by layer "+ flag +"/6";
            popDesc = "Algoritma dasar";
        } else if (level == 3) {
            buttonPopStart.setBackgroundColor(R.color.green3);
            imageViewPop.setImageResource(R.drawable.monster3);
            popTittle = "CFOP "+ flag +"/6";
            popDesc = "COMING SOON";
        }
        textViewPopTitle.setText(popTittle);
        textViewPopDesc.setText(popDesc);

        buttonPopStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level == 1 ) {
                    moveToIntroduction();
                    popupWindow.dismiss();
                }
                else if (level == 2){
                    moveToBeginner();
                    popupWindow.dismiss();
                }
                else if (level == 3){
                    Toast.makeText(getContext(),"Not Avaiable Yet", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
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