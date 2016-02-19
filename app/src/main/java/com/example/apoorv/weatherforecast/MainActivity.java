package com.example.apoorv.weatherforecast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks {

    FloatingActionButton current_location;
    Button search;
    MultiAutoCompleteTextView city_box;
    ArrayList<String> results;
    GoogleApiClient client;
    ProgressDialog pd=null;
    Location location;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            initialise();


    }


    public void getUserLocation()
    {

        pd.setMessage("Finding your current location..Please wait");
        pd.show();

        if (new CheckLoactionProvider(MainActivity.this).checkProvider()) {
            client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
            client.connect();
        }
        else {
            pd.dismiss();
            new ShowErrorDialog(MainActivity.this).getErrorDialog();


        }


    }


    public void initialise()
    {

        current_location=(FloatingActionButton)findViewById(R.id.fab);

        current_location.setOnClickListener(this);

        search=(Button)findViewById(R.id.search);
        city_box=(MultiAutoCompleteTextView)findViewById(R.id.city_box);

        search.setOnClickListener(this);

        pd=new ProgressDialog(MainActivity.this);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.main_content);


    }


    @Override
    protected void onStart() {
       // client.connect();
        super.onStart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client!=null)
        {
            client.disconnect();
        }

    }

    @Override
    public void onClick(View view) {


       if (view.getId()==R.id.search)
       {
                if(new CheckNetwork(MainActivity.this).CheckNetworkConnection()) {

                    if (city_box.getText().toString().length() >= 3) {
                        startActivity(new Intent(MainActivity.this, ShowWeatherDetails.class)
                                .putExtra("multiple", "yes")
                                .putExtra("cities", city_box.getText().toString().split(",")));
                    } else {
                        Snackbar.make(coordinatorLayout, "Please enter city name", Snackbar.LENGTH_SHORT).show();
                    }
                }

        else
       {

           Snackbar.make(coordinatorLayout,"No network connection found.",Snackbar.LENGTH_SHORT).show();


       }

       }

        if (view.getId()==R.id.fab) {

            if(new CheckNetwork(MainActivity.this).CheckNetworkConnection())
            {
                getUserLocation();
            }
            else
            {

                Snackbar.make(coordinatorLayout, "No network connection found.", Snackbar.LENGTH_SHORT).show();
            }

        }



    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        location=LocationServices.FusedLocationApi.getLastLocation(client);
        pd.dismiss();

        startActivity(new Intent(MainActivity.this,ShowWeatherDetails.class).putExtra("lat",location.getLatitude()).putExtra("lon",location.getLongitude()).putExtra("multiple","no"));




    }

    @Override
    public void onConnectionSuspended(int i) {




    }





}
