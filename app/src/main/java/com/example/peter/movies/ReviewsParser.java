package com.example.peter.movies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 9/11/2016.
 */
public class ReviewsParser {

    public static Review reviewParsing(String jsonString)

    {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            Review review = new Review();
            review.setAuther(jsonObject.getString("author"));
            review.setContent(jsonObject.getString("content"));

            return review;


        } catch (JSONException e) {
            Log.e("JSONEXCEPTION", "" + e.getMessage());
            return null;
        }
    }

    public static List<Review> ReviewListParsing(String responseString) {
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");


            List<Review> reviewsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
                reviewsList.add(reviewParsing(jsonArray.getJSONObject(i).toString()));
            return reviewsList;


        } catch (JSONException e) {
            Log.e("JSON", "" + e.getMessage());
            return null;
        }
    }

}