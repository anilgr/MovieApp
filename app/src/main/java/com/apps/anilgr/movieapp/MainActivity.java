package com.apps.anilgr.movieapp;


import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  {
    private  RecyclerView mRecyclerview;
    private ArrayList<Movie> mMovies;
    private RecyclerView.Adapter mAdapter;
    private View.OnClickListener gridItemClickListener;
    private  static String API_URL  = "https://api.themoviedb.org/3/movie/popular?api_key=54eeadb7c2e869f939a82e97174f2e5b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = findViewById(R.id.recycler_view);
        mRecyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        mMovies = getMovies(API_URL);
        gridItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //int position = (int)v.getTag(1);
                Toast.makeText(getApplicationContext(),
                        "like to watch: "+((Movie)v.getTag()).title,
                        Toast.LENGTH_SHORT).show();
            }
        };
        mAdapter = new customAdapter(getApplicationContext(),mMovies,gridItemClickListener);
        mRecyclerview.setAdapter(mAdapter);
         //MovieFetchTask task = new MovieFetchTask(getApplicationContext());
         //task.execute(API_URL);


    }

    public ArrayList<Movie> getMovies(String url) {
        MovieFetchTask task = new MovieFetchTask(getApplicationContext());
        try {
            String result = task.execute(url).get();
            return  Utility.getMovies(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<Movie>();
    }



    public  static  class movieHolder extends  RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        View mView;
        public movieHolder(View itemView) {
            super(itemView);
            mView = itemView;
            imageView = (ImageView)itemView.findViewById(R.id.movie_poster_image);
            textView = (TextView)itemView.findViewById(R.id.item_text);
        }
    }
    public static class customAdapter extends RecyclerView.Adapter<movieHolder>
    {   private  Context mContext;
        private  ArrayList<Movie> movies;
        private View.OnClickListener listener;
        public customAdapter(Context context, ArrayList<Movie> movies, View.OnClickListener listener) {
            this.mContext = context;
            this.movies = movies;
            this.listener = listener;
        }

        @Override
        public movieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.movie_holder_item_view,
                    parent,
                    false);


            return new movieHolder(v);
        }

        @Override
        public void onBindViewHolder(movieHolder holder, int position) {
              Movie movie=movies.get(position);
              holder.mView.setOnClickListener(listener);
              holder.mView.setTag(movies.get(position));
              holder.textView.setText(movie.title);
              Picasso.with(mContext).load(Uri.parse(movie.poster_path)).into(holder.imageView);
        }



        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

}
