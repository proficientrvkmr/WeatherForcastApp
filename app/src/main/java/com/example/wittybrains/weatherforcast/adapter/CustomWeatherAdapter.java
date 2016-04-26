package com.example.wittybrains.weatherforcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wittybrains.weatherforcast.R;

import java.util.ArrayList;

/**
 * Created by admin on 4/26/2016.
 */
public class CustomWeatherAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<String> day;
    private ArrayList<String> date;
    private ArrayList<String> weather;
    private ArrayList<String> max_min_Temp;

    public CustomWeatherAdapter(Context c, ArrayList<String> day, ArrayList<String> date,
                                ArrayList<String> weather, ArrayList<String> max_min_Temp){
        this.mContext = c;
        this.day=day;
        this.date=date;
        this.weather=weather;
        this.max_min_Temp=max_min_Temp;
    }

    @Override
    public int getCount() {
        return day.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.weather_record_stripe, parent, false);
        }

        TextView txt_weather = (TextView) view.findViewById(R.id.txt_weather);
        txt_weather.setText(day.get(position)+" "+date.get(position)+" - "+weather.get(position)+" - "+max_min_Temp.get(position));


        return view;
    }
}
