package com.dadashow.intentserviceexampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edittext);
        textView = findViewById(R.id.textView);
    }

    public void download(View view) {
        MyResultReceiver resultReceiver = new MyResultReceiver(new Handler());
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("url", editText.getText().toString());
        textView.setText("downloading...");
        intent.putExtra("resultReceiver", resultReceiver);
        startService(intent);
    }

    class MyResultReceiver extends ResultReceiver {
        Handler handler;

        public MyResultReceiver(Handler handler) {
            super(handler);
            this.handler = handler;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 1 && resultData != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String data = resultData.getString("websiteResult");
                        textView.setText(data);
                    }
                });
            }
            super.onReceiveResult(resultCode, resultData);
        }
    }
}