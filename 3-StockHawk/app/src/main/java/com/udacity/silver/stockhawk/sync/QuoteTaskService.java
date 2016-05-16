package com.udacity.silver.stockhawk.sync;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;


public class QuoteTaskService extends GcmTaskService {

    public static final String TAG = QuoteTaskService.class.getSimpleName();

    public static final String PERIODIC_TAG = TAG + " periodic";
    public static final String ONEOFF_TAG = TAG + " oneoff";


    @Override
    public int onRunTask(TaskParams taskParams) {
        QuoteSyncJob.getQuotes(getApplicationContext());
        return 0;
    }

}
