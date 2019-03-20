package com.example.a12109.cognitivetest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AsyncTaskNetwork extends Activity implements View.OnClickListener {

    Button button;
    private final String TAG = "asyncTask";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch ( view.getId()) {
            case R.id.button:
                Log.d(TAG, "Click on connect!");
                new LongOperation().execute("");
                break;
        }
     }


    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Setup.setUp();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.textView);
            txt.setText("Connected");
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
