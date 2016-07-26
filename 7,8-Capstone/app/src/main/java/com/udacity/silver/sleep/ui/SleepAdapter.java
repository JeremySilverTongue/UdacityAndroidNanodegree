package com.udacity.silver.sleep.ui;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;



public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.SleepItemViewHolder> {


    private Cursor cursor;

    public SleepAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public SleepItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SleepItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SleepItemViewHolder extends RecyclerView.ViewHolder {


        public SleepItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
