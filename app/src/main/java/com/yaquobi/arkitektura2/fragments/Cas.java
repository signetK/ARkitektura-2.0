package com.yaquobi.arkitektura2.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yaquobi.arkitektura2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cas.
     */
    // TODO: Rename and change types and number of parameters
    public static Cas newInstance(String param1, String param2) {
        Cas fragment = new Cas();
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

        // Inflate the layout and store in a variable
        View view = inflater.inflate(R.layout.fragment_cas, container, false);

        // Find the arrow in the included header
        ImageView backArrow = view.findViewById(R.id.arrow);

        // Set the click listener
        backArrow.setOnClickListener(v -> {
            // Go back to Home fragment
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Home())
                    .commit();
        });

        // Return the view after setting up the listener
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setItemIconTintList(ColorStateList.valueOf(Color.BLACK));
        bottomNav.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
    }


}