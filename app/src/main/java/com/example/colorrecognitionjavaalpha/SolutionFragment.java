package com.example.colorrecognitionjavaalpha;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Integer pos = 0;
    private ArrayList<String> listMove = new ArrayList<>();
    private Integer listSize;
    private RecyclerView rvSolution, rvMove;


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
            listSize = listMove.size();
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

        ImageView btnPrev = view.findViewById(R.id.iv_solusi_previous);
        btnPrev.setOnClickListener(this::onClick);
        ImageView btnPause = view.findViewById(R.id.iv_solusi_pause);
        btnPause.setOnClickListener(this::onClick);
        ImageView btnNext = view.findViewById(R.id.iv_solusi_next);
        btnNext.setOnClickListener(this::onClick);

        rvSolution = view.findViewById(R.id.rv_solusi);
        rvSolution.setHasFixedSize(true);
        showRecyclerSolution();

        rvMove = view.findViewById(R.id.rv_move);
        rvMove.setHasFixedSize(true);
        showRecyclerMove();

    }

    private void showRecyclerSolution() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSolution.setLayoutManager(layoutManager);
        ListSolutionAdapter listSolutionAdapter = new ListSolutionAdapter(listMove);
        rvSolution.setAdapter(listSolutionAdapter);
    }

    private void showRecyclerMove() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMove.setLayoutManager(layoutManager);
        ListMoveAdapter listMoveAdapter = new ListMoveAdapter(listMove);
        rvMove.setAdapter(listMoveAdapter);

        LinearSnapHelper linearSnapHelper = new LinearSnapHelper(){
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
                    return RecyclerView.NO_POSITION;
                }

                final View currentView = findSnapView(layoutManager);

                if (currentView == null) {
                    return RecyclerView.NO_POSITION;
                }

                LinearLayoutManager myLayoutManager = (LinearLayoutManager) layoutManager;

                int position1 = myLayoutManager.findFirstVisibleItemPosition();
                int position2 = myLayoutManager.findLastVisibleItemPosition();

                int currentPosition = layoutManager.getPosition(currentView);

                if (velocityX > 400) {
                    currentPosition = position2;
                } else if (velocityX < 400) {
                    currentPosition = position1;
                }

                if (currentPosition == RecyclerView.NO_POSITION) {
                    return RecyclerView.NO_POSITION;
                }

                return currentPosition;
            }
        };
        linearSnapHelper.attachToRecyclerView(rvMove);

        rvMove.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = linearSnapHelper.findSnapView(layoutManager);
                pos = layoutManager.getPosition(view);

                RecyclerView.ViewHolder viewHolder = rvSolution.findViewHolderForAdapterPosition(pos);
                TextView textView = viewHolder.itemView.findViewById(R.id.item_move);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    textView.animate().setDuration(100).scaleX(1.5f).scaleY(1.5f).setInterpolator(new AccelerateInterpolator()).start();
                } else {
                    textView.animate().setDuration(100).scaleX(1).scaleY(1).setInterpolator(new AccelerateInterpolator()).start();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_solusi_previous:
                if (pos > 0){
                    pos--;
                    rvSolution.smoothScrollToPosition(pos);
                    rvMove.smoothScrollToPosition(pos);
                } else {
                    Toast.makeText(getContext(), "HABIS", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_solusi_pause:
                Toast.makeText(getContext(), "HABIS", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_solusi_next:
                if (pos < listSize){
                    pos++;
                    rvSolution.smoothScrollToPosition(pos);
                    rvMove.smoothScrollToPosition(pos);
                } else {
                    Toast.makeText(getContext(), "HABIS", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}