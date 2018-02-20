package com.apps.anilgr.movieapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by anilgr on 19/2/18.
 */

public class MovieFetchTask extends AsyncTask<String, Void, String> {
   private static String TAG = "DEBUG_FETCH_TASK";
   private  String result;
   Context mContext;
    public MovieFetchTask(Context context) {

       this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo == null|| !netInfo.isConnected())
        {
            Toast.makeText(mContext,"network not available",Toast.LENGTH_SHORT).show();
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(strings[0]).build();
        //HttpURLConnection connection = null;
        Call call = client.newCall(request);
        Response response = null;
        try {
     //       URL url = new URL(strings[0]);
     //       connection = (HttpURLConnection) url.openConnection();
     //       connection.setConnectTimeout(15000);
     //       connection.setReadTimeout(15000);
     //       connection.setRequestMethod("GET");
     //       connection.connect();
     //       if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
     //           Log.d(TAG,"error connecting to the network");
     //       InputStream inputStream = connection.getInputStream();
     //    //   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
     //    //   char[] buffer = new char[1024];
     //    //   StringBuilder stringBuilder = new StringBuilder();
     //    //   int readNo;
     //    //   while ((readNo = bufferedReader.read(buffer))!= -1)
     //    //   {
     //    //      stringBuilder.append(buffer,0,readNo);
     //    //   }
     //    //   bufferedReader.close();
     //       result = readStream(inputStream,500);
     //       if(inputStream != null)
     //           inputStream.close();
            response = call.execute();
            if(response.isSuccessful())
               result =  response.body().string();
            Log.d(TAG,result);

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {

           //if (connection != null)
            //   connection.disconnect();

        }

        return result;
    }
    public String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        BufferedReader reader = null;
        InputStreamReader readerIn = new InputStreamReader(stream, Charset.forName("UTF-8"));
        reader = new BufferedReader(readerIn);

        StringBuffer buffer = new StringBuffer();
        String line = reader.readLine();
        while (line != null ) {
            buffer.append(line).append("\n");

            line = reader.readLine();

        }
        return buffer.toString();
    }
}
