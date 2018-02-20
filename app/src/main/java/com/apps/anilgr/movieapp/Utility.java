package com.apps.anilgr.movieapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by anilgr on 19/2/18.
 */

public class Utility {
    static  String BASE_URL = "http://image.tmdb.org/t/p/w300";

    public static ArrayList<Movie> getMovies(String jsonData)
    {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONObject responseObj  = new JSONObject(jsonData);
            JSONArray resultsArray = responseObj.getJSONArray("results");
             int i = 0;
            while(resultsArray.get(i) != null)
            {
               JSONObject movieObj = (JSONObject) resultsArray.get(i);
               Movie movie = new Movie();
               movie.id = String.valueOf(movieObj.getInt("id"));
               movie.rating = movieObj.getDouble("vote_average");
               movie.title = movieObj.getString("title");
               movie.overview = movieObj.getString("overview");
               String[] tmp = movieObj.getString("release_date").split("-");
               movie.release_year = tmp[0];
               movie.poster_path = BASE_URL+movieObj.getString("poster_path");
               movies.add(movie);
               i++;

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        Log.d("OBJECTCREATED",String.valueOf(movies.size()));
        return movies;
    }
}
