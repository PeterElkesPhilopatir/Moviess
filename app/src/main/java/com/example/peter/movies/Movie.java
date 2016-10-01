package com.example.peter.movies;

import java.io.Serializable;
import java.util.List;

/**
 * Created by peter on 10/08/2016.
 */
public class Movie implements Serializable{
    String posterPath;
    String overView;
    String releaseDate;
    String originalTitle;
    String voteAverage;
    String id;
    String trailerID;
    String reviewID;

    // setters
    public void setPosterPath(String posterPath){this.posterPath=posterPath;}
    public void setOverView(String overView){this.overView=overView;}
    public void setReleaseDate(String releaseDate){this.releaseDate=releaseDate;}
    public void setOriginalTitle(String originalTitle){this.originalTitle=originalTitle;}
    public void setVoteAverage(String voteAverage){this.voteAverage=voteAverage;}
    public void setId (String id){this.id=id;}
    public void setTrailerID (String trailerID){this.trailerID=trailerID;}
    public void setReviewID (String reviewID){this.reviewID=reviewID;}



    // getters


    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getId() {return id;}

    public String getTrailerID(){return trailerID;}

    public String getReviewID() {return reviewID;}



}
