package com.udacity.silver.sleep.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.silver.sleep.R;
import com.udacity.silver.sleep.data.SleepContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.SleepItemViewHolder> {


    private Cursor cursor;

    private final Context context;

    private final DateFormat dateFormat;
    private final DateFormat timeFormat;

    public SleepAdapter(Context context) {
        this.context = context;
        dateFormat = SimpleDateFormat.getDateInstance();
        timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
    }

    public void setCursor(Cursor cursor) {
        if (this.cursor != null) {
            this.cursor.close();
        }
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public SleepItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_sleep, parent, false);
        return new SleepItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SleepItemViewHolder holder, int position) {

        cursor.moveToPosition(position);

        long rawDay = cursor.getLong(SleepContract.POSITION_DAY);
        long rawSleep = cursor.getLong(SleepContract.POSITION_SLEEP);
        long rawWake = cursor.getLong(SleepContract.POSITION_WAKE);
        double rawDuration = cursor.getDouble(SleepContract.POSITION_DURATION);

        String date = dateFormat.format(new Date(rawDay));
        String sleep = timeFormat.format(new Date(rawSleep));
        String wake = timeFormat.format(new Date(rawWake));
        String duration = String.format(Locale.getDefault(), "%01.1f", rawDuration);


        holder.date.setText(date);
        holder.sleep.setText(sleep);
        holder.wake.setText(wake);
        holder.duration.setText(duration);

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
        }
        return count;
    }

    class SleepItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.sleep)
        TextView sleep;

        @BindView(R.id.wake)
        TextView wake;

        @BindView(R.id.duration)
        TextView duration;


        public SleepItemViewHolder(View itemView) {

            super(itemView);


            ButterKnife.bind(this, itemView);

        }
    }
}
