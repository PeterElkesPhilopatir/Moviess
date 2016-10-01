package com.example.peter.movies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 9/8/2016.
 */
public class TrailerParsing {
    public static Trailer trailerParsing(String jsonString)

    {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            Trailer trailer = new Trailer();
            trailer.setName(jsonObject.getString("name"));
            trailer.setKey(jsonObject.getString("key"));

            return trailer;


        } catch (JSONException e) {
            Log.e("JSONEXCEPTION", "" + e.getMessage());
            return null;
        }

    }

    public static List<Trailer> trailerListParsing(String responseString) {
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");


            List<Trailer> trailerList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
                trailerList.add(trailerParsing(jsonArray.getJSONObject(i).toString()));
            return trailerList;


        } catch (JSONException e) {
            Log.e("JSON", "" + e.getMessage());
            return null;
        }
    }

}