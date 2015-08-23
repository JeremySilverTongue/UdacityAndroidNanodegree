package com.udacity.silver.and.myappportfolio;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class AppPortfolioActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_portfolio);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_portfolio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchProject1(View view){
        Toast.makeText(this,R.string.project_not_implemented,Toast.LENGTH_LONG).show();
    }

    public void launchProject2(View view){
        Toast.makeText(this,R.string.project_not_implemented,Toast.LENGTH_LONG).show();
    }

    public void launchProject3(View view){
        Toast.makeText(this,R.string.project_not_implemented,Toast.LENGTH_LONG).show();
    }

    public void launchProject4(View view){
        Toast.makeText(this,R.string.project_not_implemented,Toast.LENGTH_LONG).show();
    }

    public void launchProject5(View view){
        Toast.makeText(this,R.string.project_not_implemented,Toast.LENGTH_LONG).show();
    }

    public void launchProject6(View view){
        Toast.makeText(this,R.string.project_not_implemented,Toast.LENGTH_LONG).show();
    }
}
