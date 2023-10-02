package com.example.colorrecognitionjavaalpha;

import android.content.res.TypedArray;
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
import android.widget.ImageView;
import android.widget.Toast;

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

    private RecyclerView rvMateri;
    private ArrayList<Materi> listMateri = new ArrayList<>();

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

        listMateri.addAll(getListMateri());
        showRecycler();


//        ImageView imgContent = view.findViewById(R.id.iv_materi_content);
//        TextView textTittle = view.findViewById(R.id.tv_materi_content_tittle);
//        TextView textDesc = view.findViewById(R.id.tv_materi_content_desc);

//        imgContent.setImageResource(dataPhoto.getResourceId(0, -1));
//        textDesc.setText(introDesc[0]);

        ImageView btnClose = view.findViewById(R.id.iv_materi_back);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    public ArrayList<Materi> getListMateri() {
        String[] introTittle = getResources().getStringArray(R.array.materi_pengenalan_tittle);
        String[] introDesc = getResources().getStringArray(R.array.materi_pengenalan_desc);
        TypedArray introPhoto = getResources().obtainTypedArray(R.array.photo_rubik);

        ArrayList<Materi> list = new ArrayList<>();
        for (int i = 0; i < introTittle.length; i++) {
            Materi materi = new Materi();
            materi.setTittle(introTittle[i]);
            materi.setDescription(introDesc[i]);
            materi.setPhoto(introPhoto.getResourceId(i, -1));
            materi.setReaded(true);
            list.add(materi);
        }
        return list;
    }

    private void showRecycler() {
//        rvMateri.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMateri.setLayoutManager(new LinearLayoutManager(getContext()));
        ListMateriAdapter listMateriAdapter = new ListMateriAdapter(listMateri);
        rvMateri.setAdapter(listMateriAdapter);


        listMateriAdapter.setOnItemClickCallback(this::showSelectedHero);
    }

    private void showSelectedHero(Materi materi) {
        Toast.makeText(getContext(), "Kamu memilih " + materi.getTittle(), Toast.LENGTH_SHORT).show();
    }
}