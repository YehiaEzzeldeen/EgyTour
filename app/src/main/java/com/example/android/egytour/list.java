package com.example.android.egytour;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class list extends AppCompatActivity {
    public ArrayList<Landmark> landmarkArrayList= new ArrayList<Landmark>();
    public ArrayList<Landmark> ratinglandmarkArrayList= new ArrayList<Landmark>();
    public ArrayList<Landmark> distancelandmarkArrayList= new ArrayList<Landmark>();
    public ArrayList<Bitmap> icons= new ArrayList<Bitmap>();
    double longitude;
    double latitude;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FloatingActionButton reSort = (FloatingActionButton) findViewById(R.id.reSort);
        try{
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             longitude = location.getLongitude();
             latitude = location.getLatitude();

        }
        catch (SecurityException e){
            e.printStackTrace();
        }
        ArrayList<String> names=getIntent().getStringArrayListExtra("names");
        Log.d("!!!!!!!!!!!!!!!!!!!",""+names.size());
        ArrayList<String> latitudes=getIntent().getStringArrayListExtra("latitudes");
        ArrayList<String> longitudes=getIntent().getStringArrayListExtra("longitudes");
        ArrayList<String> ratings=getIntent().getStringArrayListExtra("ratings");
        ArrayList<String> image_paths=getIntent().getStringArrayListExtra("image_paths");
        ArrayList<String> phone_numbers=getIntent().getStringArrayListExtra("phone_numbers");
        ArrayList<String> images= getIntent().getStringArrayListExtra("images");
        icons=convertToBitMap(images);
        for (int i=0;i<names.size();i++){
            Landmark l= new Landmark(names.get(i),latitudes.get(i),longitudes.get(i),ratings.get(i),image_paths.get(i),phone_numbers.get(i),icons.get(i));
            ratinglandmarkArrayList.add(l);
            double lat=Double.parseDouble(l.getLatitiude());
            double lon=Double.parseDouble(l.getLongitude());
            double dis= Math.sqrt(Math.pow(lat-latitude,2)+Math.pow(lon-longitude,2));
            l.setDistance(dis);
            landmarkArrayList.add(l);
        }
        Log.d("!!!!!!!!!!!!!!!!!!!",""+landmarkArrayList.size());
        Log.d("!!!!!!!!!!!!!!!!!!!",""+ratinglandmarkArrayList.size());
        int bte5 = landmarkArrayList.size();
        for(int k=0;k<bte5;k++){
            Landmark tmp = min(landmarkArrayList);
            distancelandmarkArrayList.add(tmp);
            landmarkArrayList.remove(tmp);

        }
        Log.d("ana naughty boy ",distancelandmarkArrayList.size()+"");
        listView = (ListView) findViewById(R.id.list);
        LandmarksAdapter adapter = new LandmarksAdapter(list.this,distancelandmarkArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Landmark landmark = distancelandmarkArrayList.get(i);
                Intent x = new Intent(list.this ,MapsActivity.class );
                x.putExtra("latitude" , landmark.getLatitiude());
                x.putExtra("longitude", landmark.getLongitude());
                x.putExtra("name",landmark.getName());
                startActivity(x);
            }
        });

        //Log.d("!!!!!!!!!!!!",landmarkArrayList.get(1).getPhone_number());

    }
    public void reSort(View v){
        LandmarksAdapter adapter = new LandmarksAdapter(list.this,ratinglandmarkArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Landmark landmark = ratinglandmarkArrayList.get(i);
                Intent x = new Intent(list.this ,MapsActivity.class );
                x.putExtra("latitude" , landmark.getLatitiude());
                x.putExtra("longitude", landmark.getLongitude());
                x.putExtra("name",landmark.getName());
                startActivity(x);
            }
        });
    }
    public Landmark min(ArrayList<Landmark> x){

        Landmark res= x.get(0);
        for(int i=1;i<x.size();i++){
            if(x.get(i).getDistance()<res.getDistance()){
                res= x.get(i);
            }
        }
        return res;
    }
    public ArrayList<Bitmap> convertToBitMap(ArrayList<String> images){
        for(int i=0;i<images.size();i++){
            byte[] decodedString= Base64.decode(images.get(i),Base64.DEFAULT);
            Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            icons.add(decodedByte);

        }
        return icons;
    }

}
