package com.example.lenovo.networkingpractise;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
public class ContainerAsyncTask extends AsyncTask<String,Void,ArrayList<Container>> {
onDownloadCompleteListener mListener;

 void setOnDownloadCompleteListener(onDownloadCompleteListener listener){
     mListener=listener;
 }
    @Override
    protected ArrayList<Container> doInBackground(String... params) {
       String urlString=params[0];
        try{
            URL url=new URL(urlString);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();
            Scanner s=new Scanner(inputStream);
            String str="";
            while (s.hasNext()){
                str+=s.nextLine();
            }
            return parseCourses(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Container> parseCourses(String str) {
      try {
          JSONArray containerJsonArray=new JSONArray(str);
          ArrayList<Container> containerArray=new ArrayList<>();
          for(int i=0;i<containerJsonArray.length();i++){
              JSONObject containerJson=(JSONObject)containerJsonArray.get(i);
            int userId=containerJson.getInt("userId");
              int id=containerJson.getInt("id");
              String title=containerJson.getString("title");
              String body=containerJson.getString("body");
              Container c=new Container(userId,id,title,body);
              containerArray.add(c);
          }
          return containerArray;
      } catch (JSONException e) {
          e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Container> containers) {
        super.onPostExecute(containers);
        if(mListener!=null){
            mListener.onDownloadComplete(containers);
        }
    }
}

interface onDownloadCompleteListener{
   void onDownloadComplete(ArrayList<Container> containersList);
}