package com.example.android.egytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    public static final String LOGINURL ="http://192.168.1.3/get.php";
    public ArrayList<Landmark> ratingSortedLandmarks=new ArrayList<Landmark>();
    public ArrayList<Landmark> landmarks=new ArrayList<Landmark>();
    public ArrayList<String> names=new ArrayList<String>();
    public ArrayList<String> latitudes=new ArrayList<String>();
    public ArrayList<String> longitudes=new ArrayList<String>();
    public ArrayList<String> ratings=new ArrayList<String>();
    public ArrayList<String> image_paths=new ArrayList<String>();
    public ArrayList<String> images=new ArrayList<String>();
    public ArrayList<String> phone_numbers=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        String username = getIntent().getStringExtra("fullname");
        String email = getIntent().getStringExtra("e-mail");
        Button currentLocationButton = (Button)findViewById(R.id.currentLocationButton);
        Button logoutButton = (Button)findViewById(R.id.logoutButton);
        Button showNearby = (Button)findViewById(R.id.showNearby);
        TextView userText = (TextView)findViewById(R.id.userText);
        userText.setText(username);
        TextView mailText = (TextView)findViewById(R.id.mailText);
        mailText.setText(email);
        StringRequest sr= new StringRequest(Request.Method.GET, LOGINURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String o=jsonObject.getString("length");
                    int length=Integer.parseInt(o);
                    Log.d("!!!!!!!!!!!!!!!!!!!!!",""+length);
                    for(int j=1;j<=9;j++){
                        String name=jsonObject.getString("name"+j);
                        names.add(name);
                        String latitude=jsonObject.getString("latitude"+j);
                        latitudes.add(latitude);
                        String longitude=jsonObject.getString("longitude"+j);
                        longitudes.add(longitude);
                        String rating=jsonObject.getString("rating"+j);
                        ratings.add(rating);
                        String image_path=jsonObject.getString("image_path"+j);
                        image_paths.add(image_path);
                        String phone_number=jsonObject.getString("phone_number"+j);
                        phone_numbers.add(phone_number);
                        String image=jsonObject.getString("image"+j);
                        images.add(image);

                    }
                    for (int u=0;u<names.size();u++){
                        Log.d("!!!!!!!!!!!!!!!!!!!!!",names.get(u));
                    }

                }catch(JSONException e){
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d("MyActivity","kek");
                Toast.makeText(WelcomeActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {

            //@Override
            // protected Map<String, String> getParams() throws AuthFailureError {
            //   Map<String, String> params= new HashMap<String, String>();
            // params.put(KEY_email,EMAIL);
            //params.put(KEY_password,PASS);


            //return params;
            //}

        };

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);


    }
    public void currentLocation(View v){
        Intent x = new Intent(WelcomeActivity.this,MapsActivity.class);
        startActivity(x);
    }
    public void logout(View v){
        Intent x = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(x);
    }
    public void showNearby(View v){
        Intent x = new Intent(WelcomeActivity.this,list.class);
        x.putStringArrayListExtra("names",names);
        x.putStringArrayListExtra("latitudes",latitudes);
        x.putStringArrayListExtra("longitudes",longitudes);
        x.putStringArrayListExtra("ratings",ratings);
        x.putStringArrayListExtra("image_paths",image_paths);
        x.putStringArrayListExtra("phone_numbers",phone_numbers);
        x.putStringArrayListExtra("images",images);

        startActivity(x);
    }
}
