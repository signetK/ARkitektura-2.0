package com.yaquobi.arkitektura2.fragments;

import android.os.Bundle;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaquobi.arkitektura2.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.yaquobi.arkitektura2.adapter.CollegeAdapter;
import com.yaquobi.arkitektura2.model.CollegeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private final ActivityResultLauncher<ScanOptions> qrLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerView;
    private FloatingActionButton fabQR;
    private List<CollegeModel> collegeList;


    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fabQR = view.findViewById(R.id.QRScan);

        fabQR.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Scan a QR Code");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CaptureAct.class);
            qrLauncher.launch(options);
        });

        recyclerView = view.findViewById(R.id.Colleges);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        collegeList = new ArrayList<>();
        collegeList.add(new CollegeModel(R.drawable.ui_ccis,R.drawable.ic_ccis, "College of Computing and Information Sciences"));
        collegeList.add(new CollegeModel(R.drawable.ui_coe,R.drawable.ic_coe, "College of Engineering"));
        collegeList.add(new CollegeModel(R.drawable.ui_cas,R.drawable.ic_cas, "College of Arts and Sciences"));
        collegeList.add(new CollegeModel(R.drawable.ui_cafsd,R.drawable.ic_cafsd, "College of Agriculture, Food, and Sustainable Development"));
        collegeList.add(new CollegeModel(R.drawable.ui_cbea,R.drawable.ic_cbea, "College of Business, Entrepreneurship, and Accountancy"));
        collegeList.add(new CollegeModel(R.drawable.ui_chs,R.drawable.ic_chs, "College of Health Sciences"));
        collegeList.add(new CollegeModel(R.drawable.ui_col,R.drawable.ic_col, "College of Law"));
        collegeList.add(new CollegeModel(R.drawable.ui_com,R.drawable.ic_com, "College of Medicine"));


        CollegeAdapter adapter = new CollegeAdapter(collegeList);

        adapter.setOnItemClickListener(position -> {
            Fragment selectedFragment = null;

            switch (position) {
                case 0: selectedFragment = new Ccis(); break;
                case 1: selectedFragment = new Coe(); break;
                case 2: selectedFragment = new Cas(); break;
                case 3: selectedFragment = new Cafsd(); break;
                case 4: selectedFragment = new Cbea(); break;
                case 5: selectedFragment = new Chs(); break;
                case 6: selectedFragment = new Col(); break;
                case 7: selectedFragment = new Com(); break;
            }

            if (selectedFragment != null) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setItemIconTintList(getResources().getColorStateList(R.color.bottom_navigation_selector));
        bottomNav.setItemTextColor(getResources().getColorStateList(R.color.bottom_navigation_selector));
    }

}