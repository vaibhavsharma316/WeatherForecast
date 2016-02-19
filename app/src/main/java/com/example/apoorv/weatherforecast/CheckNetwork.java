package com.example.apoorv.weatherforecast;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;


public class CheckNetwork {

        Context context;

    public CheckNetwork(Context context)
    {
        this.context=context;
    }

public boolean CheckNetworkConnection() {

    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


    NetworkInfo info[] = manager.getAllNetworkInfo();

if (info!=null)
{
    for (int i = 0; i < info.length; i++) {

        if (info[i].getState()==NetworkInfo.State.CONNECTED) {
            return true;
        }
    }

}



    return false;
   }




}
