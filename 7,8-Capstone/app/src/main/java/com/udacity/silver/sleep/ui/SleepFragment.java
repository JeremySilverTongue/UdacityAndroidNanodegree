package com.udacity.silver.sleep.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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


public class SleepFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {


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
                goToSleep();
            }
        });

        wakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SleepPreferenceUtils.wakeUp(getContext());
                wakeUp();
            }
        });

        MobileAds.initialize(getContext(), getString(R.string.ad_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void goToSleep() {
        awakeLayout.setVisibility(View.GONE);
        asleepLayout.setVisibility(View.VISIBLE);
    }


    public void wakeUp() {
        asleepLayout.setVisibility(View.GONE);
        awakeLayout.setVisibility(View.VISIBLE);
        Timber.d("%s", SleepContract.getAllNights(getContext()).toString());
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(SleepPreferenceUtils.SLEEP_KEY)) {
            if (SleepPreferenceUtils.isAsleep(getContext())) {
                goToSleep();
            } else {
                wakeUp();
            }
        }

    }
}

