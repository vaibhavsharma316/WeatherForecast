package com.example.apoorv.weatherforecast;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MultipleCitiesForecast extends AsyncTask<Void,Void,Void> {


    Context context;
    ProgressDialog dialog;
    String icon_url;
    JSONObject map=null;
    RecyclerView recyclerView;
    String user_input[];
    JSONArray arr=new JSONArray();

    public MultipleCitiesForecast(Context context,RecyclerView recyclerView,String user_input[])
    {
        this.recyclerView=recyclerView;
        this.context=context;
        this.user_input=user_input;
        icon_url=context.getResources().getString(R.string.icon_url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait");
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {


            for (int i=0;i<user_input.length;i++)
            {

                doNetworkOperation(user_input[i]);


            }


        }
       catch (Exception e)
       {
             e.printStackTrace();
       }
                return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
        recyclerView.setAdapter(new WeatherDetailsByLocationAdapter(context,arr));

    }



    public void doNetworkOperation(String name)
    {
        InputStream is=null;

        try {
            URL url=new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q="+name+"&units=metric"+"&cnt=14"+"&appid="+context.getResources().getString(R.string.APPID));
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            StringBuilder builder=new StringBuilder();
            is=conn.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String data=null;
            while ((data=reader.readLine())!=null)
            {
                builder.append(data);
            }
            is.close();
            reader.close();

             Log.v("Data","Response "+builder.toString());

            JSONObject jsonObject=new JSONObject(builder.toString());

            for (int i=0;i<jsonObject.getJSONArray("list").length();i++) {


                JSONObject list_data=jsonObject.getJSONArray("list").getJSONObject(i);
                map=new JSONObject();
                map.put("city", jsonObject.getJSONObject("city").getString("name"));

                map.put("country", jsonObject.getJSONObject("city").getString("country"));

                map.put("date",new TimeStampToDate().UnixTimeToDate(list_data.getLong("dt")));
                map.put("humidity",list_data.getInt("humidity"));
                map.put("day",list_data.getJSONObject("temp").getString("day"));
                map.put("night",list_data.getJSONObject("temp").getString("night"));

                for (int j=0;j<list_data.getJSONArray("weather").length();j++)
                {

                    map.put("description", list_data.getJSONArray("weather").getJSONObject(j).getString("description"));
                    map.put("icon", icon_url+list_data.getJSONArray("weather").getJSONObject(j).getString("icon")+".png");

                }



                arr.put(map);


            }



            Log.v("json data map", arr.toString());













        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }



}


