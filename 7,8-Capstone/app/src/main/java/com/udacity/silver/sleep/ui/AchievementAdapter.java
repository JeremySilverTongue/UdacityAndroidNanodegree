package com.udacity.silver.sleep.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.silver.sleep.R;
import com.udacity.silver.sleep.data.SleepPreferenceUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {


    private final Context context;

    private final List<String> keys;
    private final List<String> titles;
    private final List<String> descriptions;

    private Set<String> completeAchievements;

    public AchievementAdapter(Context context) {
        this.context = context;

        keys = Arrays.asList(context.getResources().getStringArray(R.array.trophy_case_keys));
        titles = Arrays.asList(context.getResources().getStringArray(R.array.trophy_case_titles));
        descriptions = Arrays.asList(context.getResources().getStringArray(R.array.trophy_case_descriptions));

        completeAchievements = SleepPreferenceUtils.getAchievements(context);
        Timber.d("Complete cheevos: %s", completeAchievements);

    }

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_achievement, parent, false);
        return new AchievementViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position) {

        if (completeAchievements.contains(keys.get(position))) {
            holder.trophy.setVisibility(View.VISIBLE);
        } else {
            holder.trophy.setVisibility(View.INVISIBLE);
        }


        holder.title.setText(titles.get(position));
        holder.description.setText(descriptions.get(position));


    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    class AchievementViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.trophy)
        ImageView trophy;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView description;

        public AchievementViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
