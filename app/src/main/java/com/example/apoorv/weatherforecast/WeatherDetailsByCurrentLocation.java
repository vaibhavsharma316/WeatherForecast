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
import java.util.HashMap;
import java.util.Map;


public class WeatherDetailsByCurrentLocation extends AsyncTask<Void,Void,Void> {

    Context context;
    Double lat,lon;
    ProgressDialog dialog;
    String icon_url;
    JSONObject map=null;
    RecyclerView recyclerView;
    JSONArray arr=new JSONArray();
    public WeatherDetailsByCurrentLocation(Context context,Double lat,Double lon,RecyclerView recyclerView)
    {
        this.lat=lat;
        this.lon=lon;
        this.context=context;
        this.recyclerView=recyclerView;
        icon_url=context.getResources().getString(R.string.icon_url);

    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait..");
        dialog.show();
    }




    @Override
    protected Void doInBackground(Void... voids) {


        InputStream is=null;

            try {


                URL url=new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lon+"&units=metric"+"&cnt=14"+"&appid="+context.getResources().getString(R.string.APPID));

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
            catch (Exception e )
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
}
