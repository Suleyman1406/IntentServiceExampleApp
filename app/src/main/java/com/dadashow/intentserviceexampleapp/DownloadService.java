package com.dadashow.intentserviceexampleapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String urlStringExtra = intent.getStringExtra("url");
        ResultReceiver resultReceiver=intent.getParcelableExtra("resultReceiver");
        URL url;
        String result = "";
        HttpURLConnection httpURLConnection;
        try {
            url = new URL(urlStringExtra);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            int data = inputStreamReader.read();
            while (data!=-1){
                char ch=(char) data;
                result+=ch;
                data=inputStreamReader.read();
            }

            Bundle bundle=new Bundle();
            bundle.putString("websiteResult",result);
            resultReceiver.send(1,bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: " + Thread.currentThread().getName());
        super.onDestroy();
    }

}
