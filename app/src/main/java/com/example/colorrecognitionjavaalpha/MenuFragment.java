package com.example.colorrecognitionjavaalpha;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Button btnExpert = view.findViewById(R.id.button_expert);
        btnExpert.setOnClickListener(this::onClick);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_notation:
                Toast.makeText(getContext(),"Not Avaiable Yet", Toast.LENGTH_SHORT).show();
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
            case R.id.button_expert:
                Toast.makeText(getContext(),"Not Avaiable Yet", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}