package com.example.wittybrains.weatherforcast.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 12/14/2015.
 */
public class AsyncRequest extends AsyncTask<String, Integer, String> {

    private OnAsyncRequestComplete caller;
    private Context context;
    private String method;
    private List<NameValuePair> parameters = null;

    public AsyncRequest(Activity activity, String method, List<NameValuePair> params) {
        caller = (OnAsyncRequestComplete) activity;
        context = activity;
        this.method = method;
        parameters = params;
    }

    public AsyncRequest(Activity activity, String method) {
        caller = (OnAsyncRequestComplete) activity;
        context = activity;
        this.method = method;
    }

    public AsyncRequest(Activity activity) {
        caller = (OnAsyncRequestComplete) activity;
        context = activity;
    }

    public String doInBackground(String... urls) {
        String address = urls[0].toString();
        if (method == "POST") {
            return post(address);
        }

        if (method == "GET") {
            return get(address);
        }

        return null;
    }

    public void onPostExecute(String response) {
        caller.asyncResponse(response);
    }

    protected void onCancelled(String response) {
        caller.asyncResponse(response);
    }

    private String get(String address) {
        try {
            if (parameters != null) {

                String query = "";
                String EQ = "=";
                String AMP = "&";
                for (NameValuePair param : parameters) {
                    query += param.getName() + EQ + URLEncoder.encode(param.getValue()) + AMP;
                }

                if (query != "") {
                    address += "?" + query;
                }
            }
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(address);

            HttpResponse response = client.execute(get);
            return stringifyResponse(response);

        } catch (Exception e) {
            System.out.print(e);
        }

        return null;
    }

    private String post(String address) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(address);

            if (parameters != null) {
                post.setEntity(new UrlEncodedFormEntity(parameters));
            }

            HttpResponse response = client.execute(post);
            return stringifyResponse(response);

        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

    private String stringifyResponse(HttpResponse response) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            reader.close();

            return sb.toString();
        } catch (Exception e) {
            System.out.print(e);
        }

        return null;
    }

    // Interface to be implemented by calling activity
    public interface OnAsyncRequestComplete {
        public void asyncResponse(String response);
    }
}
