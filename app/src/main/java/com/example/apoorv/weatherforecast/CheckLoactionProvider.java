package com.example.apoorv.weatherforecast;

import android.content.Context;
import android.location.LocationManager;


public class CheckLoactionProvider {

Context context;
public CheckLoactionProvider(Context context)
{
this.context=context;
}

    public boolean checkProvider()
    {

        LocationManager manager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }







}
