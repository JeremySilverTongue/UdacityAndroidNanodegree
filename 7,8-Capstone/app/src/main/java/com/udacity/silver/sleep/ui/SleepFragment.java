package com.udacity.silver.sleep.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.udacity.silver.sleep.R;
import com.udacity.silver.sleep.data.SleepContract;
import com.udacity.silver.sleep.data.SleepPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class SleepFragment extends Fragment {


    @BindView(R.id.sleep_button)
    Button sleepButton;

    @BindView(R.id.wake_up_button)
    Button wakeButton;

    @BindView(R.id.asleep_layout)
    View asleepLayout;

    @BindView(R.id.awake_layout)
    View awakeLayout;

    @BindView(R.id.ad_view)
    AdView adView;

    public SleepFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sleep, container, false);


        ButterKnife.bind(this, root);

        sleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SleepPreferenceUtils.goToSleep(getContext());
                awakeLayout.setVisibility(View.GONE);
                asleepLayout.setVisibility(View.VISIBLE);
                SleepPreferenceUtils.goToSleep(getContext());
            }
        });

        wakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SleepPreferenceUtils.wakeUp(getContext());
                asleepLayout.setVisibility(View.GONE);
                awakeLayout.setVisibility(View.VISIBLE);
                SleepPreferenceUtils.wakeUp(getContext());
                Timber.d("%s", SleepContract.getAllNights(getContext()).toString());
            }
        });

        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        return root;
    }

}
