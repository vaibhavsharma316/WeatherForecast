package com.example.apoorv.weatherforecast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;


public class WeatherDetailsByLocationAdapter extends RecyclerView.Adapter<WeatherDetailsByLocationAdapter.ViewHolder> {

    JSONArray arr;
    Context context;

    public WeatherDetailsByLocationAdapter(Context context,JSONArray arr)
    {
        this.arr=arr;
        this.context=context;
    }

    @Override
    public WeatherDetailsByLocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherDetailsByLocationAdapter.ViewHolder holder, int position) {

        try {
            holder.city_name.setText(arr.getJSONObject(position).getString("city"));
            holder.today_temp.setText(arr.getJSONObject(position).getString("day")+" C");
            holder.date.setText("Date: "+arr.getJSONObject(position).getString("date"));
            holder.day.setText("D: "+arr.getJSONObject(position).getString("day")+" C");
            holder.night.setText("N: "+arr.getJSONObject(position).getString("night")+" C");
            holder.humidity.setText("Humidity: "+arr.getJSONObject(position).getString("humidity")+" %");
            holder.description.setText(arr.getJSONObject(position).getString("description"));
            Picasso.with(context).load(arr.getJSONObject(position).getString("icon")).into(holder.icon_image);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }






    }

    @Override
    public int getItemCount() {
        return arr.length();
    }


 public class ViewHolder extends RecyclerView.ViewHolder
{

     TextView today_temp,city_name,description,humidity,day,night,date;
       ImageView icon_image;



    public ViewHolder(View itemView) {
        super(itemView);


        today_temp=(TextView)itemView.findViewById(R.id.today_temp);
        city_name=(TextView)itemView.findViewById(R.id.city_name);
        description=(TextView)itemView.findViewById(R.id.description);
        humidity=(TextView)itemView.findViewById(R.id.humidity);
        day=(TextView)itemView.findViewById(R.id.day);
        night=(TextView)itemView.findViewById(R.id.night);
        date=(TextView)itemView.findViewById(R.id.date);
        icon_image=(ImageView)itemView.findViewById(R.id.icon);


    }









}


}
