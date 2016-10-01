package com.example.peter.movies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 13/08/2016.
 */
public class adapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String>givenURLs = new ArrayList<>();

    public adapter(Context mContext,List<String>givenURLs){
        super(mContext,R.layout.single_movie,R.id.movie_poster,givenURLs);
        this.mContext=mContext;
        this.givenURLs=givenURLs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    View gridItem = convertView;
    if(gridItem == null){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gridItem = inflater.inflate(R.layout.single_movie,parent,false);
    }
    ImageView image = (ImageView) gridItem.findViewById(R.id.movie_poster);
//    image.setLayoutParams(new GridView.LayoutParams(500, 780));
    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w300"+givenURLs.get(position))
                .placeholder(R.mipmap.logo)
                .into(image);


    return gridItem;
}

}
