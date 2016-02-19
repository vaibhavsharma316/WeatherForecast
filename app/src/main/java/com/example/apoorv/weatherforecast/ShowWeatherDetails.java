package com.example.apoorv.weatherforecast;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


public class ShowWeatherDetails extends AppCompatActivity {


    Double latitude,longitude;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        getSupportActionBar().setTitle("Weather Details");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(ShowWeatherDetails.this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent().getExtras().getString("multiple").equalsIgnoreCase("no"))
        {

            new WeatherDetailsByCurrentLocation(ShowWeatherDetails.this,getIntent().getExtras().getDouble("lat"),getIntent().getExtras().getDouble("lon"),recyclerView).execute();


        }
        else if(getIntent().getExtras().getString("multiple").equalsIgnoreCase("yes"))
        {

            new MultipleCitiesForecast(ShowWeatherDetails.this,recyclerView,getIntent().getExtras().getStringArray("cities")).execute();


        }


            }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_weather_details, menu);
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




}
