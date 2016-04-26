package com.example.wittybrains.weatherforcast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.wittybrains.weatherforcast.adapter.CustomWeatherAdapter;
import com.example.wittybrains.weatherforcast.util.AsyncRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncRequest.OnAsyncRequestComplete {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            AsyncRequest asyncRequest = new AsyncRequest(this, "GET");
            asyncRequest.execute("https://demo-61668.onmodulus.net/document.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void asyncResponse(String response) {
        try {
            JSONObject object = new JSONObject(response.toString());
            JSONArray jsonArray = object.getJSONArray("Last7DayWeather");
            ArrayList<String> day = new ArrayList<String>();
            ArrayList<String> date = new ArrayList<String>();
            ArrayList<String> weather = new ArrayList<String>();
            ArrayList<String> max_min_Temp = new ArrayList<String>();

            for(int i=0;i<jsonArray.length();i++){
                JSONObject tempObj = jsonArray.getJSONObject(i);
                day.add(tempObj.get("day").toString());
                date.add(tempObj.get("date").toString());
                weather.add(tempObj.get("weather").toString());
                max_min_Temp.add(tempObj.get("temp").toString());
            }
            listView = (ListView) findViewById(android.R.id.list);
            CustomWeatherAdapter adapter = new CustomWeatherAdapter(MainActivity.this,day,date,weather,max_min_Temp);
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}