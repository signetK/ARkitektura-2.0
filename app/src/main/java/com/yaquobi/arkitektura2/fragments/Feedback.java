package com.yaquobi.arkitektura2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yaquobi.arkitektura2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Feedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feedback extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText feedbackEmail, feedbackMessage;
    private Button submitFeedbackBtn;

    public Feedback() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Feedback.
     */
    // TODO: Rename and change types and number of parameters
    public static Feedback newInstance(String param1, String param2) {
        Feedback fragment = new Feedback();
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
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        feedbackEmail = view.findViewById(R.id.feedbackEmail);
        feedbackMessage = view.findViewById(R.id.feedbackMessage);
        submitFeedbackBtn = view.findViewById(R.id.submitFeedbackBtn);

        View backArrow = view.findViewById(R.id.arrow);
        if (backArrow != null) {
            backArrow.setOnClickListener(v ->
                    requireActivity().getSupportFragmentManager().popBackStack()
            );
        }

        submitFeedbackBtn.setOnClickListener(v -> {
            String email = feedbackEmail.getText().toString().trim();
            String message = feedbackMessage.getText().toString().trim();

            if (email.isEmpty() || message.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                // For now just show toast (you can later save/send feedback)
                Toast.makeText(getContext(),
                        "Thank you for your feedback!", Toast.LENGTH_SHORT).show();

                // Optionally clear fields after submit
                feedbackEmail.setText("");
                feedbackMessage.setText("");
            }
        });
    }
}