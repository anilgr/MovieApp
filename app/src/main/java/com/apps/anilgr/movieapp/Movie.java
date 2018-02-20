package com.apps.anilgr.movieapp;

/**
 * Created by anilgr on 19/2/18.
 */

public class Movie {
    String id;
    double rating;
    String title;
    String overview;
    String release_year;
    String poster_path;
    String original_language;

    public Movie() {
    }

    public Movie(String id, String title, double rating,
                 String overview, String release_year,
                 String poster_path, String original_language) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.overview = overview;
        this.release_year = release_year;
        this.poster_path = poster_path;
        this.original_language = original_language;
    }
}
