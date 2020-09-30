package com.rku.demoasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ListView lstData;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Tutorial 10");

        lstData = findViewById(R.id.lstData);

        new MyAsyncTask().execute();

        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this,UserData.class);
                intent.putExtra("userdata",i);
                startActivity(intent);

            }
        });
    }

    class MyAsyncTask extends AsyncTask {

        ProgressDialog dialog;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.show();

        }

        @Override
        protected Object doInBackground(Object[] objects) {

/* ----------- Using Assets Folder this method ----------- */
/*            try {
                InputStream is = getAssets().open("data.json");
                int size = 0;
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                Log.i("json", json);

                jsonObject = new JSONObject(json);
                jsonArray = jsonObject.getJSONArray("USEFUL");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

/* ----------- End Using Assets Folder this method ----------- */

/* ----------- Using Internet this method ----------- */

            StringBuffer response = new StringBuffer();

            try {
                URL url = new URL(MyUtil.URL_USER);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader ir = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(ir);
                String inputLine = null;

                while ((inputLine = br.readLine()) != null ){
                    response.append(inputLine);
                }
                br.close();
                ir.close();

                MyUtil.userdata = new JSONArray(response.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

/* ----------- End Using Internet this method ----------- */

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            adapter = new CustomAdapter(MainActivity.this,  MyUtil.userdata);
            lstData.setAdapter(adapter);

            if (dialog.isShowing())dialog.dismiss();
        }

    }
}