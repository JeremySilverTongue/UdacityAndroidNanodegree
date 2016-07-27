package com.udacity.silver.sleep.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.silver.sleep.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrophyCaseFragment extends Fragment {


    public TrophyCaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trophy_case, container, false);
    }

}
