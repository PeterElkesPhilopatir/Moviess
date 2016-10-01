package com.example.peter.movies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 10/08/2016.
 */
public class ParsingUtils {

    public static Movie movieParsing(String jsonString)

    {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            Movie movie = new Movie();
            movie.setOriginalTitle(jsonObject.getString("original_title"));
            movie.setOverView(jsonObject.getString("overview"));
            movie.setPosterPath(jsonObject.getString("poster_path"));
            movie.setReleaseDate(jsonObject.getString("release_date"));
            movie.setVoteAverage(jsonObject.getString("vote_average"));
            movie.setId(jsonObject.getString("id"));

            Log.d("pathes", "" + movie.getPosterPath());
            Log.d("original titles", "" + movie.originalTitle);
            Log.d("overViews", "" + movie.getOverView());
            Log.d("date", "" + movie.getReleaseDate());
            Log.d("rating", "" + movie.getVoteAverage());
            Log.d("id", "" + movie.getId());

            return movie;


        } catch (JSONException e) {
            Log.e("JSONEXCEPTION", "" + e.getMessage());
            return null;
        }
    }

    public static List<Movie> movieListParsing(String responseString) {
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");


            List<Movie> movieList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
                movieList.add(movieParsing(jsonArray.getJSONObject(i).toString()));
            return movieList;


        } catch (JSONException e) {
            Log.e("JSON", "" + e.getMessage());
            return null;
        }
    }

}