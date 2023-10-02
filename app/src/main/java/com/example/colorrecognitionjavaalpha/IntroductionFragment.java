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
 * Use the {@link IntroductionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroductionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IntroductionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroductionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroductionFragment newInstance(String param1, String param2) {
        IntroductionFragment fragment = new IntroductionFragment();
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
        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnIntro1 = view.findViewById(R.id.button_introduction_1);
        MaterialButton btnIntro2 = view.findViewById(R.id.button_introduction_2);
        MaterialButton btnIntro3 = view.findViewById(R.id.button_introduction_3);

        btnIntro1.setOnClickListener(this::onClick);
        btnIntro2.setOnClickListener(this::onClick);
        btnIntro3.setOnClickListener(this::onClick);

        ImageView imageView = view.findViewById(R.id.iv_introduction_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    public void onClick(View view) {
        String param1 = null;
        String param2 = null;

        switch (view.getId()){
            case R.id.button_introduction_1:
                param1 = "Posisi dan Bagian Rubik";
                param2 = "1";
                break;
            case R.id.button_introduction_2:
                param1 = "Notasi Pada Rubik";
                param2 = "2";
                break;
            case R.id.button_introduction_3:
                param1 = "Notasi Lanjutan";
                param2 = "3";
                break;
        }

        MateriFragment moveFragment = MateriFragment.newInstance(param1, param2);
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, moveFragment, MateriFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }
}