package com.ubpis.inventame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoftPOSNumericPadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoftPOSNumericPadFragment extends Fragment {
    public SoftPOSNumericPadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soft_pos_numeric_pad, container, false);
    }
}