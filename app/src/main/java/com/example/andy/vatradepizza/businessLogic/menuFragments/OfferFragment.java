package com.example.andy.vatradepizza.businessLogic.menuFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.andy.vatradepizza.R;


public class OfferFragment extends Fragment {
    View rootView;
    Button button;

    public OfferFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_offer, container, false);
        button = rootView.findViewById(R.id.offer_button);
        button.setOnClickListener(v -> {
            // Perform action on click
            Toast.makeText(getContext(), "a mers", Toast.LENGTH_SHORT).show();
        });
        return rootView;
    }

}
