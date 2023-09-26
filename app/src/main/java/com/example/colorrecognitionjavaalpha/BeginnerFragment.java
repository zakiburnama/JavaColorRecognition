package com.example.colorrecognitionjavaalpha;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeginnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeginnerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BeginnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeginnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeginnerFragment newInstance(String param1, String param2) {
        BeginnerFragment fragment = new BeginnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beginner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnSunflowe = view.findViewById(R.id.button_sunflower);
        MaterialButton btnCross = view.findViewById(R.id.button_cross);
        MaterialButton btn1layer = view.findViewById(R.id.button_1l);
        MaterialButton btn2layer = view.findViewById(R.id.button_2l);
        MaterialButton btnOLL = view.findViewById(R.id.button_oll);
        MaterialButton btnPLL = view.findViewById(R.id.button_pll);

        btnSunflowe.setOnClickListener(this::onClick);
        btnCross.setOnClickListener(this::onClick);
        btn1layer.setOnClickListener(this::onClick);
        btn2layer.setOnClickListener(this::onClick);
        btnOLL.setOnClickListener(this::onClick);
        btnPLL.setOnClickListener(this::onClick);

        ImageView imageView = view.findViewById(R.id.iv_introduction_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment menuFragment = new MenuFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, menuFragment, MenuFragment.class.getSimpleName())
                        .disallowAddToBackStack()
                        .commit();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sunflower:
                break;
            case R.id.button_cross:
                break;
            case R.id.button_1l:
                break;
            case R.id.button_2l:
                break;
            case R.id.button_oll:
                break;
            case R.id.button_pll:
                break;
        }
    }
}