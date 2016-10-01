package com.example.peter.movies;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.peter.movies.R.string.movie;

/**
 * Created by Batot on 9/28/2016.
 */

public class MainFragment extends Fragment  {

    View rootView;


    public interface Callback
    {
        void onItemSelected (Movie pos ) ;
    }



    public static String api_key = "edb65204fa73b0ef0776ba3ad3af8243";
    private String url = "https://api.themoviedb.org/3/movie/popular?api_key=";

    //fields
    public  static List  <Movie> movieList;
    public  List<String> URLs = new ArrayList<>();
    GridView gridView;
    adapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_main,container,false);
         gridView = (GridView) rootView.findViewById(R.id.movie_grid);

        setHasOptionsMenu(true);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                try {
                    Movie movie = movieList.get(position);
                    ((Callback)getActivity()).onItemSelected(movie);
                } catch (Exception e) {
    Log.e("error Fragment",""+e.getMessage());

                    }

            }
        });

//        gridView.setAdapter(mAdapter);

        return rootView;
    }
    @Override
    public void onStart ()
    {
        loadData(url+api_key);
        super.onStart();
    }

    public void loadData(String mUrl)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),getString(R.string.app_name),"Loading");

        Ion.with(this)
                .load("GET", mUrl)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        progressDialog.dismiss();
                        Log.d("myTag",""+result);

                        //check error
                        if (e!=null)
                        {
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                            Log.e("tagE",""+e.getMessage());
                        }
                        // data parsing
                        List<Movie> movies =ParsingUtils.movieListParsing(result);
                        movieList=movies;
                        if (movies==null)
                        {
                            Toast.makeText(getActivity(),"movies = null !!!",Toast.LENGTH_LONG).show();
                            Log.e("nullll",""+e.getMessage());
                            return;
                        }

                        for (int i=0;i<movies.size();i++)
                            URLs.add(movies.get(i).getPosterPath());

                         mAdapter = new adapter(getActivity(),URLs);
                        gridView.setAdapter(mAdapter);



                    }
                });
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == R.id.topRated)
        {
            if(url == "https://api.themoviedb.org/3/movie/top_rated?api_key="+api_key)
            {
                // do nothing
            }
            else
            url = "https://api.themoviedb.org/3/movie/top_rated?api_key="+api_key;
//            URLs.clear();
            mAdapter.clear();
            loadData(url);
        }

        if (item.getItemId() == R.id.popular)
        {
            if (url == "https://api.themoviedb.org/3/movie/popular?api_key="+api_key)
            {
                // do nothing
            }else
            url = "https://api.themoviedb.org/3/movie/popular?api_key="+api_key;
//            URLs.clear();
            mAdapter.clear();
            loadData(url);
        }

        if  (item.getItemId() == R.id.favs)
        {
            List <Movie> moviees = new ArrayList<>();
            Movie movie = new Movie();
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(),movie);
            moviees = databaseHelper.getMovies();
            if (moviees.size()==0)
            {
                Toast.makeText(getActivity(),"no Marked Movies !",Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(getActivity(),"You Have marked "+moviees.size()+" Movies",Toast.LENGTH_SHORT).show();

                mAdapter.clear();
                for (int i=0;i<moviees.size();i++)
                    URLs.add(moviees.get(i).getPosterPath());

                gridView.setAdapter(mAdapter);

            }

        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Log.d("position = ", "" + position);

        Movie movie = movieList.get(position);
        mAdapter.clear();
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.KEY, movie);
        startActivity(intent);
    }
}
