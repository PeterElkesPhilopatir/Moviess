package com.example.peter.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.koushikdutta.ion.builder.Builders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 9/16/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "MOVIES_DATABASE";
    public static int DATABASE_VERSION = 1;
    public Movie movie;
    String movieID;


    public DatabaseHelper(Context context,Movie movie) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // movieID=movie.getId();
        this.movie=movie;
        movieID=movie.getId();


            Log.d("movie title",""+movie.getOriginalTitle());
            Log.d("movie des",""+movie.getOverView());

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createMovieTable = "CREATE TABLE " +MovieContract.MovieEntry.MOVIE_TABLE_NAME+"(\n" +
                MovieContract.MovieEntry.MOVIE_ID + " TEXT,\n" +
                MovieContract.MovieEntry.MOVIE_TITLE + " TEXT,\n" +
                MovieContract.MovieEntry.MOVIE_DESCRIPTION + " TEXT,\n" +
                MovieContract.MovieEntry.MOVIE_RATING + " TEXT,\n" +
                MovieContract.MovieEntry.MOVIE_RELEASE_DATE +" TEXT,\n"+
                MovieContract.MovieEntry.MOVIE_POSTER_PATH + " TEXT,\n"+
                "UNIQUE ("+MovieContract.MovieEntry.MOVIE_ID+") ON CONFLICT REPLACE)\n";


        try {sqLiteDatabase.execSQL(createMovieTable);
            Log.d("created",""+createMovieTable);

        } catch (Exception e){Log.e("not created ",""+e.getMessage());}


//        String createTrailerTable = "create table " + MovieContract.MovieEntry.TRAILER_TABLE_NAME +"(\n"+
//                MovieContract.MovieEntry.TRAILER_ID + " integer primary key auto increment,\n" +
//                MovieContract.MovieEntry.TRAILER_NAME + " TEXT,\n"+
//                MovieContract.MovieEntry.TRAILER_KEY + " TEXT,\n"+
//                MovieContract.MovieEntry.TRAILER_MOVIE_ID + " integer primary key auto increment,\n"+
//                "FOREIGN KEY("+MovieContract.MovieEntry.TRAILER_MOVIE_ID+") REFERENCES "+MovieContract.MovieEntry.MOVIE_TABLE_NAME+"("+MovieContract.MovieEntry.MOVIE_TRAILER_ID+")\n"+
//                ", UNIQUE ("+MovieContract.MovieEntry.TRAILER_ID+") ON CONFLICT REPLACE)\n";

//        try {

//            String createTrailerTable = "CREATE TABLE " +MovieContract.MovieEntry.TRAILER_TABLE_NAME+"(\n" +
//                MovieContract.MovieEntry.TRAILER_ID + " integer primary key auto increment,\n" +
//                MovieContract.MovieEntry.TRAILER_NAME + " TEXT,\n" +
//                MovieContract.MovieEntry.TRAILER_KEY + " TEXT,\n" +
//                MovieContract.MovieEntry.TRAILER_MOVIE_ID + " integer primary key auto increment,\n"+
//                "UNIQUE ("+MovieContract.MovieEntry.TRAILER_ID+") ON CONFLICT REPLACE)\n";
//
//
//            sqLiteDatabase.execSQL(createTrailerTable);
//            Log.d("trailer created ",""+createTrailerTable);
//        } catch (Exception e) {
//            Log.e("not created trailer ",""+e.getMessage());
//
//        }


//        String createReviewTable = "CREATE TABLE " + MovieContract.MovieEntry.REVIEW_TABLE_NAME +"(\n"+
//                MovieContract.MovieEntry.REVIEW_ID + "TEXT PRIMARY KEY,\n" +
//                MovieContract.MovieEntry.REVIEW_AUTHOR + "TEXT,\n"+
//                MovieContract.MovieEntry.REVIEW_CONTENT + "TEXT,\n"+
//                MovieContract.MovieEntry.REVIEW_MOVIE_ID + "TEXT,\n"+
//                "FOREIGN KEY("+MovieContract.MovieEntry.REVIEW_MOVIE_ID+") REFERENCES "+MovieContract.MovieEntry.MOVIE_TABLE_NAME+"("+MovieContract.MovieEntry.MOVIE_REVIEW_ID+")\n"+
//                ", UNIQUE ("+MovieContract.MovieEntry.REVIEW_ID+") ON CONFLICT REPLACE)\n";
//
//        sqLiteDatabase.execSQL(createReviewTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String dropQuery1 = "DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.MOVIE_TABLE_NAME;
        sqLiteDatabase.execSQL(dropQuery1);

//        String dropQuery2 = "DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TRAILER_TABLE_NAME;
//        sqLiteDatabase.execSQL(dropQuery2);

//        String dropQuery3 = "DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.REVIEW_TABLE_NAME;
//        sqLiteDatabase.execSQL(dropQuery3);

        onCreate(sqLiteDatabase);

    }

    public void insertMovie()
    {

        SQLiteDatabase db = getWritableDatabase();


            try {
                String insertingQuery = "insert into " + MovieContract.MovieEntry.MOVIE_TABLE_NAME + "(" + MovieContract.MovieEntry.MOVIE_TITLE + "," + MovieContract.MovieEntry.MOVIE_DESCRIPTION + "," +
                        MovieContract.MovieEntry.MOVIE_RATING + "," + MovieContract.MovieEntry.MOVIE_POSTER_PATH +
                        "," + MovieContract.MovieEntry.MOVIE_RELEASE_DATE + ")" +
                        " values ('" + movie.getOriginalTitle() + "'," +
                        "'" + movie.getOverView() + "'," +
                        "'" + movie.getVoteAverage() + "'," +
                        "'" + movie.getPosterPath() + "'," +
                        "'" + movie.getReleaseDate() + "')";
                db.execSQL(insertingQuery);
                Log.d("inserted","done");
            }
            catch (Exception e)
            {
                Log.e("errorrr",""+e.getMessage());
            }

//        try {
//
//            ContentValues contentValues = new ContentValues();
//
//            contentValues.put(MovieContract.MovieEntry.MOVIE_TITLE,movie.getOriginalTitle());
//            contentValues.put(MovieContract.MovieEntry.MOVIE_DESCRIPTION,movie.getOverView());
//            contentValues.put(MovieContract.MovieEntry.MOVIE_RATING,movie.getVoteAverage());
//            contentValues.put(MovieContract.MovieEntry.MOVIE_POSTER_PATH,movie.getPosterPath());
//            contentValues.put(MovieContract.MovieEntry.MOVIE_RELEASE_DATE,movie.getReleaseDate());
//
//            Long rowID = db.insert(MovieContract.MovieEntry.MOVIE_TABLE_NAME,null,contentValues);
//
//
//
//             if (rowID !=-1)
//             {
//                 Log.d("inserted",""+rowID);
//             }
//         } catch (Exception e)
//         {
//             Log.e("EWI",""+e.getMessage());
//         }
//
//        db.close();
//
//

    }

    public void insertTrailers(List <Trailer> trailers) {

        for (int i=0;i<trailers.size();i++){
            insertTrailerr(trailers.get(i));
        }

    }

    public void insertTrailerr (Trailer trailer)
    {
        final SQLiteDatabase db = getWritableDatabase();

        try {

            String insertTrailer = "insert into " + MovieContract.MovieEntry.TRAILER_TABLE_NAME +"("+
                MovieContract.MovieEntry.TRAILER_NAME+"," +MovieContract.MovieEntry.TRAILER_KEY+")"+
                " values " + "(" + "'"+trailer.getName()+"','"+trailer.getKey()+"'"+")";

            db.execSQL(insertTrailer);

            Log.d("query trailer",""+insertTrailer);


        } catch (Exception e) {
        Log.e("error TRAILER",""+e.getMessage());
        }


        db.close();
    }

    public void insertReviews (Review review)
    {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.REVIEW_AUTHOR,review.getAuther());
        cv.put(MovieContract.MovieEntry.REVIEW_CONTENT,review.getContent());
        cv.put(MovieContract.MovieEntry.REVIEW_MOVIE_ID,movieID);

        getWritableDatabase().insert(MovieContract.MovieEntry.REVIEW_TABLE_NAME,null,cv);

    }

    public List<Movie> getMovies ()
    {

        String selectQuery = "SELECT  * FROM " + MovieContract.MovieEntry.MOVIE_TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        List <Movie> movies = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                movie.setId(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_ID)));
                movie.setOverView(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_DESCRIPTION)));
                movie.setVoteAverage(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_RATING)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_RELEASE_DATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_POSTER_PATH)));

                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movies;
    }

    public List<Trailer> getTrailers ()
    {

        String selectQuery = "SELECT  * FROM " + MovieContract.MovieEntry.TRAILER_TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        List <Trailer> trailers = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Trailer trailer = new Trailer();
                trailer.setId(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TRAILER_ID)));
                trailer.setName(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TRAILER_NAME)));
                trailer.setKey(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TRAILER_KEY)));

                trailers.add(trailer);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return trailers;
    }


    public List<Review> getReviews()
    {
        String selectQuery = "SELECT  * FROM " + MovieContract.MovieEntry.REVIEW_TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        List <Review> reviews = new ArrayList<>();
        if (cursor.moveToFirst())
        {
            do {
                Review review = new Review();
                review.setId(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TRAILER_ID)));
                review.setAuther(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.REVIEW_AUTHOR)));
                review.setContent(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.REVIEW_CONTENT)));

                reviews.add(review);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return reviews;
    }
}
