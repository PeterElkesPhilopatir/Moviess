package com.example.peter.movies;

import android.net.Uri;

/**
 * Created by peter on 9/16/2016.
 */
public class MovieContract  {


    public static final String CONTENT_AUTHORITY = "com.example.peter.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public class MovieEntry {

        public static final String MOVIE_TABLE_NAME = "movie";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "movie_name";
        public static final String MOVIE_DESCRIPTION = "movie_description";
        public static final String MOVIE_RATING = "movie_rating";
        public static final String MOVIE_POSTER_PATH = "movie_poster_path";
        public static final String MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String MOVIE_TRAILER_ID = "m_trailer_id";
        public static final String MOVIE_REVIEW_ID = "m_review_id";


        public static final String TRAILER_TABLE_NAME = "trailer";
        public static final String TRAILER_ID = "trailer_id";
        public static final String TRAILER_MOVIE_ID = "t_movie_id";
        public static final String TRAILER_NAME = "trailer_name";
        public static final String TRAILER_KEY = "trailer_key";

        public static final String REVIEW_TABLE_NAME = "review";
        public static final String REVIEW_ID = "review_id";
        public static final String REVIEW_MOVIE_ID = "r_movie_id";
        public static final String REVIEW_AUTHOR = "review_author";
        public static final String REVIEW_CONTENT = "review_content";


    }
}
