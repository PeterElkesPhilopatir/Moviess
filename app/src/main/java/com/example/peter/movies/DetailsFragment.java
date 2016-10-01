package com.example.peter.movies;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Batot on 9/28/2016.
 */

public class DetailsFragment extends Fragment {

    View rootView;
    Intent intent;
    Bundle arg;
    String id;

    List <String> names = new ArrayList<>();
    List <String> keys = new ArrayList<>();
    List <String> authors = new ArrayList<>();
    List <String> contents = new ArrayList<>();
    String stringOfReviews = "";

    List <Trailer> trailersList = new ArrayList<>();
    private ArrayAdapter<String> mTrailerAdapter;

    Movie movie;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        arg=args;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ListView trailersListView = (ListView) rootView.findViewById(R.id.trailersList);
        ImageView image = (ImageView) rootView.findViewById(R.id.detailPoster);
        TextView year = (TextView) rootView.findViewById(R.id.releaseDate);
        TextView rating = (TextView) rootView.findViewById(R.id.rating);
        TextView title = (TextView) rootView.findViewById(R.id.detailTitle);
        TextView description = (TextView) rootView.findViewById(R.id.detailDescription);

        Button review = (Button) rootView.findViewById(R.id.reviews);
        Button favBtn = (Button) rootView.findViewById(R.id.fav);

        intent = getActivity().getIntent();
            if (arg!=null)
            {
                final Movie m = new Movie();
                    m.setId(arg.getString(MovieContract.MovieEntry.MOVIE_ID));
                    m.setOriginalTitle(arg.getString(MovieContract.MovieEntry.MOVIE_TITLE));
                    m.setOverView(arg.getString(MovieContract.MovieEntry.MOVIE_DESCRIPTION));
                    m.setPosterPath(arg.getString(MovieContract.MovieEntry.MOVIE_POSTER_PATH));
                    m.setReleaseDate(arg.getString(MovieContract.MovieEntry.MOVIE_RELEASE_DATE));
                    m.setVoteAverage(arg.getString(MovieContract.MovieEntry.MOVIE_RATING));

                    Picasso.with(getActivity())
                            .load("http://image.tmdb.org/t/p/w1000" + m.getPosterPath())
                            .placeholder(R.mipmap.loading)
                            .into(image);

                    id = m.getId();
                    title.setText("    " + m.getOriginalTitle());
                    description.setText(m.getOverView());
                    rating.setText(m.getVoteAverage() + "/10");
                    year.setText(m.getReleaseDate());

                    favBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), m);

                            databaseHelper.insertMovie();
//                    List <Trailer> trailersToSave = loadTrailers();
//                    databaseHelper.insertTrailers(trailersToSave);
                        }
                    });

                    review.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadReviews();
                        }
                    });
            }
        else if (intent != null && intent.hasExtra(DetailsActivity.KEY)) {
            movie = (Movie) intent.getSerializableExtra(DetailsActivity.KEY);
            Log.d("ifPos", "" + position);
            id = movie.getId();
            title.setText("    " + movie.getOriginalTitle());
            description.setText(movie.getOverView());
            rating.setText(movie.getVoteAverage() + "/10");
            year.setText(movie.getReleaseDate());
//            posterImageView.setImageResource(R.mipmap.logo);
            id = movie.getId();
            //posterImageView.setLayoutParams(new GridView.LayoutParams(500,780));
//            movie/id?api_key=
            Picasso.with(getActivity())
                    .load("http://image.tmdb.org/t/p/w1000" + movie.getPosterPath())
                    .placeholder(R.mipmap.loading)
                    .into(image);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), movie);

                    databaseHelper.insertMovie();
//                    List <Trailer> trailersToSave = loadTrailers();
//                    databaseHelper.insertTrailers(trailersToSave);
                }
            });

            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadReviews();
                }
            });

            // generate random data
            List<String> randomData = new ArrayList<>();

            // setup the adapter
            mTrailerAdapter = new ArrayAdapter<String>(
                    getActivity()
                    , R.layout.single_trailer
                    , R.id.name
                    , randomData
            );

            // reference the list view
            trailersListView = (ListView) rootView.findViewById(R.id.trailersList);

            // bind the adapter to the list view
            trailersListView.setAdapter(mTrailerAdapter);

            trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(keys.get(position))));

                }


            });


            // setting listener

            loadTrailers();
        }

            return rootView;
    }

    private List<Trailer> loadTrailers()
    {

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),getString(R.string.app_name),"Loading Trailers");
        String newUrlTrailer = "http://api.themoviedb.org/3/movie/" +id+ "/videos?api_key=" + MainActivity.api_key;

        Ion.with(this)
                .load("GET",newUrlTrailer)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onCompleted(Exception e, String result) {
                        progressDialog.dismiss();
                        Log.d("Trailer",""+result);

                        //check error
                        if (e!=null)
                        {
                            Log.e("tagE",""+e.getMessage());
                        }
                        // data parsing
                        List<Trailer> trailers =TrailerParsing.trailerListParsing(result);
                        trailersList=trailers;
                        if (trailers==null)
                        {
                            Log.e("nullll",""+e.getMessage());
                            return;
                        }
                        for (int i=0;i<trailers.size();i++)
                        {
                            names.add(trailers.get(i).getName());
                            keys.add(trailers.get(i).getKey());
                        }

                        mTrailerAdapter.clear();
                        mTrailerAdapter.addAll(names);

                        Log.d("names",""+names);

                    }
                });

        return trailersList;

    }


    private  void loadReviews () {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"Loading","");
        String reviewURL = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=" + MainActivity.api_key;
        Ion.with(this)
                .load("GET", reviewURL)
                .asString()
                .setCallback(new FutureCallback<String>() {


                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Reviews", "" + result);
                        progressDialog.dismiss();

                        //check error
                        if (e != null) {
                            Log.e("tagR", "" + e.getMessage());
                        }
                        // data parsing
                        List<Review> reviews = ReviewsParser.ReviewListParsing(result);
                        if (reviews == null) {
                            Log.e("nullll", "" + e.getMessage());
                            return;
                        }

                        Log.d("rrreviews",""+reviews.toString());


                        for (int i = 0; i < reviews.size(); i++) {
                            authors.add(reviews.get(i).getAuther());
                            contents.add(reviews.get(i).getContent());
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Reviews");
                        for(int i=0; i<reviews.size(); i++){
                            if(reviews.get(i)== null){
                                break;
                            }
                            else {

                              stringOfReviews = stringOfReviews + authors.get(i)+"\n-------------------- \n"+contents.get(i)+"\n\n\n";
                            }
                        }

                        builder.setMessage(stringOfReviews);
                        builder.setPositiveButton("Thanks Udacity :) ", null);
                        builder.show();
                    }
                });



                    }

}
