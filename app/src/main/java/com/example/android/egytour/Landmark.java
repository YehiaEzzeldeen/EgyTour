package com.example.android.egytour;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class Landmark implements Comparable{
    String name;
    String latitiude;
    String longitude;
    String rating;
    String image_path;
    String phone_number;
    Double distance;
    Bitmap icon;

    public Landmark(String name,String latitiude,String longitude,String rating,String image_path,String phone_number,Bitmap icon){
        this.name=name;
        this.latitiude=latitiude;
        this.longitude=longitude;
        this.rating=rating;
        this.image_path=image_path;
        this.phone_number=phone_number;
        this.icon=icon;
    }
    public String getName(){
        return this.name;
    }
    public String getLatitiude(){
        return this.latitiude;
    }
    public String getLongitude(){
        return this.longitude;
    }
    public String getRating(){
        return this.rating;
    }
    public String getImage_path(){
        return this.image_path;
    }
    public String getPhone_number(){
        return this.phone_number;
    }
    public Bitmap getIcon(){
        return this.icon;
    }
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }


    public int compareTo(Object o) {
        Landmark l=(Landmark) o;
        int x1=Integer.parseInt(this.rating);
        int x2=Integer.parseInt(l.rating);
        return x1-x2;
    }
}