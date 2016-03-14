package com.dattgk.instagramclient;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhoto;

    public static final String mediaID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
        photos = new ArrayList<>();
        aPhoto = new InstagramPhotosAdapter(this, photos);
        TextView tvComment = (TextView)findViewById(R.id.tvComment);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchPopularPhotos();

                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);


        lvPhotos.setAdapter(aPhoto);

        fetchPopularPhotos();

    }

    public void showComment(View v) {
        Intent i = new Intent(this, comment_Activity.class);
        i.putExtra("mediaID", (String) v.getTag());
        startActivity(i);
    }

    public void fetchPopularPhotos() {

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler() {

            //on success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray photosJSON = null;
                try {

                    aPhoto.clear();

                    photosJSON = response.getJSONArray("data");

                    for (int i = 0; i < photosJSON.length(); i++) {

                        JSONObject photoJSON = photosJSON.getJSONObject(i); //take the object in the position "i" in array

                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imageURL = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.id = photoJSON.getString("id");


                        JSONArray array = photoJSON.getJSONObject("comments").getJSONArray("data");


                        //get 2 latest cmts
                        photo.comment = array.getJSONObject(array.length() - 1).getString("text");
                        photo.comment2 = array.getJSONObject(array.length() - 2).getString("text");


                        photo.imgUserURL = photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.timeUtil = photoJSON.getJSONObject("caption").getString("created_time");


                        // GET THE username of 2 latest comments
                        photo.userCmt = array.getJSONObject(array.length() - 1).getJSONObject("from").getString("username");
                        photo.userCmt2 = array.getJSONObject(array.length() - 2).getJSONObject("from").getString("username");
                        // add all the items to the photos arraylist
                        photos.add(photo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aPhoto.notifyDataSetChanged();

            }

            //on fail

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }
}
