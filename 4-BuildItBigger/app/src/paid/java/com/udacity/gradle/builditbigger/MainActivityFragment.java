package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.jokedisplay.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeReceiver {

    ProgressBar mLoadingIndicator = null;
    String joke = "No joke loaded";


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mLoadingIndicator = (ProgressBar) root.findViewById(R.id.loading_indicator);
        Button button = (Button) root.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });

        return root;
    }

    public void tellJoke(){
        fetchJoke();
    }

    void fetchJoke(){
        mLoadingIndicator.setVisibility(View.VISIBLE);
        GetJokeTask task = new GetJokeTask();
        task.execute(this);
    }

    public void receiveJoke(String joke){
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        this.joke = joke;
        launchJokeActivity();
    }

    public void launchJokeActivity() {
            Intent intent = new Intent(getActivity(), JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_KEY, joke);
            startActivity(intent);
    }

}
