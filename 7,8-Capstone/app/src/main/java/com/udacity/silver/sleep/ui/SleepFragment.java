package com.udacity.silver.sleep.ui;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
import com.udacity.silver.sleep.services.WakeUpService;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class SleepFragment extends Fragment {

    private static final int NOTIFICAITON_ID = 1234;

    @BindView(R.id.sleep_button)
    private
    Button sleepButton;

    @BindView(R.id.wake_up_button)
    private
    Button wakeButton;

    @BindView(R.id.asleep_layout)
    private
    View asleepLayout;

    @BindView(R.id.awake_layout)
    private
    View awakeLayout;

    @BindView(R.id.ad_view)
    private
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
                createNotification();
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


    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
        builder.setContentTitle("You're sleeping!");
        builder.setSmallIcon(R.drawable.ic_stat_zzz);
        builder.setOngoing(true);

        Intent wakeUpIntent = new Intent(getContext(), WakeUpService.class);

        PendingIntent pendingIntent = PendingIntent.getService(
                getContext(),
                0,
                wakeUpIntent,
                PendingIntent.FLAG_ONE_SHOT
        );

        builder.setContentIntent(pendingIntent);
//        builder.set

        NotificationManager manager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);


        manager.notify(NOTIFICAITON_ID, builder.build());

    }

}
