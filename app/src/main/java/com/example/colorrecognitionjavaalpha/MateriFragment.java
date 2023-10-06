package com.example.colorrecognitionjavaalpha;

import android.content.res.TypedArray;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer pos = 0;
    private Integer listSize;
    private Boolean play = false;

    private RecyclerView rvMateri;
//    private ArrayList<Materi> listMateri = new ArrayList<>();

    private DatabaseReference databaseReference;

    public MateriFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MateriFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MateriFragment newInstance(String param1, String param2) {
        MateriFragment fragment = new MateriFragment();
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
        Log.i("TAG", "#### onCreateView");
        return inflater.inflate(R.layout.fragment_materi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("TAG", "#### MateriFragment = "+mParam1);
        Log.i("TAG", "#### MateriFragment = "+mParam2);

        rvMateri = view.findViewById(R.id.rv_materi);
        rvMateri.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("data").child("pengenalan").child("pengenalan1");
        getAllData();

        ImageView btnPrev = view.findViewById(R.id.iv_materi_previous);
        btnPrev.setOnClickListener(this::onClick);
        ImageView btnPause = view.findViewById(R.id.iv_materi_pause);
        btnPause.setOnClickListener(this::onClick);
        ImageView btnNext = view.findViewById(R.id.iv_materi_next);
        btnNext.setOnClickListener(this::onClick);
        ImageView btnClose = view.findViewById(R.id.iv_materi_back);
        btnClose.setOnClickListener(this::onClick);
    }

    private void getAllData() {
        ArrayList<Materi> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.i("TAG", "#### snapshot: "+ snapshot);
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        Materi materi = new Materi();
                        materi.setTitle(dataSnapshot.child("title").getValue(String.class));
                        materi.setDescription(dataSnapshot.child("description").getValue(String.class));
                        materi.setPhoto(dataSnapshot.child("photo").getValue(Integer.class));
                        materi.setReaded(dataSnapshot.child("isReaded").getValue(Boolean.class));
                        list.add(materi);
                    }
                    showRecycler(list);
                    listSize = list.size() - 1;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        });
    }

    private void showRecycler(ArrayList<Materi> listMateri) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMateri.setLayoutManager(layoutManager);
        ListMateriAdapter listMateriAdapter = new ListMateriAdapter(listMateri);
        rvMateri.setAdapter(listMateriAdapter);
//        rvMateri.setPadding(150,0,150,0);

        listMateriAdapter.setOnItemClickCallback(this::showSelectedItem);

        final LinearSnapHelper snapHelper = new LinearSnapHelper(){
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
        snapHelper.attachToRecyclerView(rvMateri);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolder = rvMateri.findViewHolderForAdapterPosition(pos);
                CardView cardView = viewHolder.itemView.findViewById(R.id.item_materi);
                cardView.animate().scaleX(1).scaleY(1).setDuration(100).setInterpolator(new AccelerateInterpolator()).start();
            }
        }, 100);

//        play = true; // For auto scroll
//        setPlay(); // paly

        rvMateri.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(layoutManager);
                pos = layoutManager.getPosition(view);
                Log.i("TAG", "#### position: "+ pos);

                RecyclerView.ViewHolder viewHolder = rvMateri.findViewHolderForAdapterPosition(pos);
                CardView cardView = viewHolder.itemView.findViewById(R.id.item_materi);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    cardView.animate().setDuration(100).scaleX(1).scaleY(1).setInterpolator(new AccelerateInterpolator()).start();
                } else {
                    cardView.animate().setDuration(100).scaleX(0.75f).scaleY(0.75f).setInterpolator(new AccelerateInterpolator()).start();
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void setPlay(){
        if (play) {
            Log.i("TAG", "#### PLAY: ");
            final Handler handler = new Handler();
//            int count = 0;
            final Runnable runnable = new Runnable() {
                public void run() {
                    // need to do tasks on the UI thread
//                    Log.d(TAG, "Run test count: " + count);
                    if (pos < listSize) {
                        pos++;
                        rvMateri.smoothScrollToPosition(pos);
                        handler.postDelayed(this, 5000);
                    }
                }
            };
            // trigger first time
            handler.post(runnable);

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    pos++;
//                    rvMateri.smoothScrollToPosition(pos);
//                }
//            }, 10000);
        } else {
            Log.i("TAG", "#### ELSE: ");

        }
    }

    private void showSelectedItem(Materi materi) {
        Toast.makeText(getContext(), "Kamu memilih " + materi.getTitle(), Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_materi_back:
                getParentFragmentManager().popBackStack();
                break;
            case R.id.iv_materi_previous:
                if (pos > 0){
                    pos--;
                    rvMateri.smoothScrollToPosition(pos);
                } else {
                    Toast.makeText(getContext(), "HABIS", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_materi_pause:
                break;
            case R.id.iv_materi_next:
                if (pos < listSize){
                    pos++;
                    rvMateri.smoothScrollToPosition(pos);
                } else {
                    Toast.makeText(getContext(), "HABIS", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}