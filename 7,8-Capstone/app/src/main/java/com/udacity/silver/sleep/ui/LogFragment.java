package com.udacity.silver.sleep.ui;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.silver.sleep.R;
import com.udacity.silver.sleep.data.SleepContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LogFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 0;

    @BindView(R.id.log_recycler_view)
    RecyclerView recyclerView;

    private SleepAdapter adapter;

    public LogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_log, container, false);
        ButterKnife.bind(this, root);

        adapter = new SleepAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        getLoaderManager().restartLoader(LOADER_ID, null, this);

        Timber.d("Fragment created");
        return root;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Timber.d("Loader created");
        return new CursorLoader(getContext(), SleepContract.SLEEP_URI, SleepContract.COLUMNS, null, null, SleepContract.COLUMN_DAY);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Timber.d("Cursor set: %d", data.getCount());
        adapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setCursor(null);
    }
}
