package com.example.android.egytour;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;



public class LandmarksAdapter extends ArrayAdapter<Landmark> {
    private Context context;
    private ArrayList<Landmark> landmarks;





    public  LandmarksAdapter (Context context , ArrayList<Landmark> s ){
        super(context,0,s);
        this.context=context;
        this.landmarks = s;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Landmark current = getItem(position);
        ImageView image = (ImageView) listItemView.findViewById(R.id.landmarkImage);
        TextView name = (TextView) listItemView.findViewById(R.id.landmarkName);
        TextView rating = (TextView) listItemView.findViewById(R.id.landmarkRating);
        TextView distance = (TextView) listItemView.findViewById(R.id.dist);
        TextView number = (TextView) listItemView.findViewById(R.id.landmarkNumber);
        name.setText(current.getName());
        image.setImageBitmap(current.getIcon());
        rating.setText(current.getRating());
        distance.setText( new DecimalFormat("##.#").format(100*current.getDistance())+"Km");
        number.setText(current.getPhone_number());
        return listItemView;



    }
}
