package com.example.apoorv.weatherforecast;

import android.app.AlertDialog;
import android.content.Context;

public class ShowErrorDialog {


Context context;


    public ShowErrorDialog(Context context)
    {

        this.context=context;

    }



    public void getErrorDialog()
    {
       AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("It seems that your GPS is turned off. Please turn on GPS");
        builder.setTitle("Attention!!");
        builder.create().show();


    }





}
