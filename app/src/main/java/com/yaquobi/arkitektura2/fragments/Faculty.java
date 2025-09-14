package com.yaquobi.arkitektura2.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaquobi.arkitektura2.R;
import com.yaquobi.arkitektura2.model.FacultyModel;
import com.yaquobi.arkitektura2.adapter.FacultyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Faculty#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Faculty extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private FacultyAdapter adapter;
    private List<FacultyModel> facultyList;

    public Faculty() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Faculty.
     */
    // TODO: Rename and change types and number of parameters
    public static Faculty newInstance(String param1, String param2) {
        Faculty fragment = new Faculty();
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
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        View backArrow = view.findViewById(R.id.arrow);
        if (backArrow != null) {
            backArrow.setOnClickListener(v ->
                    requireActivity().getSupportFragmentManager().popBackStack()
            );
        }

        // RecyclerView setup
        recyclerView = view.findViewById(R.id.facultyRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        // Initialize data
        facultyList = new ArrayList<>();
        facultyList.add(new FacultyModel(R.drawable.dean, "Saturnina F. Nisperos, PhD", "Dean"));
        facultyList.add(new FacultyModel(R.drawable.sec, "Ralph C. Perdido", "College Secretary"));
        facultyList.add(new FacultyModel(R.drawable.deptchair, "Andrea Estrella C. Garcia", "Dept. Chair"));

        facultyList.add(new FacultyModel(R.drawable.acang, "James Patrick A. Acang", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.aguada, "Renzie A. Aguada", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.alano, "Norman F. Alano", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.alviar, "Miriva India V. Alviar", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.baculpo, "Eloihim O. Baculpo", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.contillo, "Gerry L. Contillo", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.cruz, "Sandrex M. Dela Cruz", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.dafun, "Mara Angelika C. Dafun", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.fernandez, "Joshua L. Fernandez", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.gerez, "Winchester R. Gerez", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.gonzales, "Bernard A. Gonzales", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.pagtaconan, "Wilben Christie R. Pagtaconan", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.rafanan, "Noel S. Rafanan", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.ruiz, "Xamantha Angelique E. Ruiz", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.simon, "Lee Carlo F. Simon", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.velasco, "Louis Eusebius S. Velasco", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.villacillo, "Reynold P. Villacillo", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.vidad, "John David Vidad", "Faculty"));

        facultyList.add(new FacultyModel(R.drawable.cacaindin, "Napoleon M. Cacanindin", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.galang, "Annjeannette Alain D. Galang", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.burac, "Michael Angelo P. Burac", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.asuncion, "Ryan Daniel S. Asuncion", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.burgos, "Renante Burgos Jr.", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.cariaga, "Lhester Cariaga", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.castro, "Nathaniel S. Castro", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.pascual, "Franco Rito B. Pascual", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.sagadraca, "Christian A. Sagadraca", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.salvador, "Reynaldo Jr Q. Salvador", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.q_vidad, "Queenee R. Vidad", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.galano, "John Eliezer L. Galano", "Faculty"));
        facultyList.add(new FacultyModel(R.drawable.barruga, "Milagros B. Barruga", "Faculty"));


        adapter = new FacultyAdapter(facultyList);
        recyclerView.setAdapter(adapter);

        return view;
    }



}