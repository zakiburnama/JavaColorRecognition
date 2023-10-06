package com.example.colorrecognitionjavaalpha;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolutionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolutionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] move;
    private ArrayList<String> listMove = new ArrayList<>();
    private RecyclerView rvSolution;


    public SolutionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolutionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolutionFragment newInstance(String param1, String param2) {
        SolutionFragment fragment = new SolutionFragment();
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

            move = mParam2.split(" ");
            listMove.addAll(Arrays.asList(move));
            Log.i("TAG", "#### listMove00 "+ listMove);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solution, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSolution = view.findViewById(R.id.rv_solusi);
        rvSolution.setHasFixedSize(true);
        showRecyclerSolution();

    }

    private void showRecyclerSolution() {
        Log.i("TAG", "#### listMove2 "+ listMove);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSolution.setLayoutManager(layoutManager);
//        rvSolution.setLayoutManager(new LinearLayoutManager(getContext()));
        ListSolutionAdapter listSolutionAdapter = new ListSolutionAdapter(listMove);
        rvSolution.setAdapter(listSolutionAdapter);
    }
}