package com.example.android.sunshine.wear;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

public class WeatherListenerService extends WearableListenerService {


    GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        googleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        googleApiClient.connect();
    }





    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        super.onDataChanged(dataEventBuffer);

        for (DataEvent event : dataEventBuffer){
            DataItem dataItem = event.getDataItem();
            DataMap
        }
    }
}
