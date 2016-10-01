package com.example.peter.movies;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.*;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity implements Serializable,MainFragment.Callback
{

    boolean f2Pane;
    public static String api_key = "edb65204fa73b0ef0776ba3ad3af8243";
    private String url = "https://api.themoviedb.org/3/movie/popular?api_key=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.a)!=null)
        {
            f2Pane = true;
        } else {
            f2Pane = false;
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main, new MainFragment()).commit();

        }

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//
//        if (item.getItemId() == R.id.topRated)
//        {
//            if(url == "https://api.themoviedb.org/3/movie/top_rated?api_key="+api_key)
//            {
//                // do nothing
//            }
//            else
//            url = "https://api.themoviedb.org/3/movie/top_rated?api_key="+api_key;
////            URLs.clear();
//            mAdapter.clear();
//            loadData(url);
//        }
//
//        if (item.getItemId() == R.id.popular)
//        {
//            if (url == "https://api.themoviedb.org/3/movie/popular?api_key="+api_key)
//            {
//                // do nothing
//            }else
//            url = "https://api.themoviedb.org/3/movie/popular?api_key="+api_key;
////            URLs.clear();
//            mAdapter.clear();
//            loadData(url);
//        }
//
//        if  (item.getItemId() == R.id.favs)
//        {
//            List <Movie> moviees = new ArrayList<>();
//            Movie movie = new Movie();
//            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this,movie);
//            moviees = databaseHelper.getMovies();
//            if (moviees.size()==0)
//            {
//                Toast.makeText(this,"no Marked Movies !",Toast.LENGTH_SHORT).show();
//            } else
//            {
//                Toast.makeText(this,"You Have marked "+moviees.size()+" Movies",Toast.LENGTH_SHORT).show();
//
//                mAdapter.clear();
//                for (int i=0;i<moviees.size();i++)
//                    URLs.add(moviees.get(i).getPosterPath());
//
//                gridView.setAdapter(mAdapter);
//
//            }
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onItemSelected(Movie pos) {

        if (f2Pane)
        {
            Bundle bundle = new Bundle();

            bundle.putString(MovieContract.MovieEntry.MOVIE_ID,pos.getId());
            bundle.putString(MovieContract.MovieEntry.MOVIE_TITLE, pos.getOriginalTitle());
            bundle.putString(MovieContract.MovieEntry.MOVIE_DESCRIPTION, pos.getOverView());
            bundle.putString(MovieContract.MovieEntry.MOVIE_POSTER_PATH, pos.getPosterPath());
            bundle.putString(MovieContract.MovieEntry.MOVIE_RELEASE_DATE, pos.getReleaseDate());
            bundle.putString(MovieContract.MovieEntry.MOVIE_RATING, pos.getVoteAverage());
            DetailsFragment detailsFragment=new DetailsFragment();
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.b,detailsFragment).commit();
            Log.d("b3d el if ","henaa");
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.KEY, pos);
            startActivity(intent);

        }
    }


    }

