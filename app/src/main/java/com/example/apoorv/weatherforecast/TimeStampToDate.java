package com.example.apoorv.weatherforecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimeStampToDate {


    public String UnixTimeToDate(long value)
    {






      Calendar cal=new GregorianCalendar();
      cal.setTime(new Date(value*1000L));

        int mon=cal.get(Calendar.MONTH)+1;

        return cal.get(Calendar.DAY_OF_MONTH)+"-"+mon+"-"+cal.get(Calendar.YEAR);

    }







}
