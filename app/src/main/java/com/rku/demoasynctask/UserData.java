package com.rku.demoasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.rku.demoasynctask.MyUtil.userdata;

public class UserData extends AppCompatActivity {
    TextView txtId,txtName,txtUsername,txtEmail,txtStreet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);


        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtUsername = findViewById(R.id.txtUserName);
        txtEmail = findViewById(R.id.txtEmail);
        txtStreet = findViewById(R.id.txtStreet);


        Intent intent = getIntent();
        int i = intent.getIntExtra("userdata",0);

        try {
            JSONObject userObj = userdata.getJSONObject(i);

            txtId.setText(userObj.getString("id"));
            txtName.setText(userObj.getString("name"));
            txtUsername.setText(userObj.getString("username"));
            txtEmail.setText(userObj.getString("email"));

            JSONObject addObj = userObj.getJSONObject("address");
            txtStreet.setText(addObj.getString("street"));


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}