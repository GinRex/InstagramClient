package com.dattgk.instagramclient;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.JarException;

import cz.msebera.android.httpclient.Header;

public class comment_Activity extends AppCompatActivity {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> comments;
    private InstagramCommentAdapter aComment;
    public String mediaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lvComment = (ListView)findViewById(R.id.lvComment);
        mediaID = getIntent().getExtras().getString("mediaID");
        Log.i("mediaID", mediaID+""); //to string
        comments = new ArrayList<>();
        aComment = new InstagramCommentAdapter(this, comments);

        lvComment.setAdapter(aComment);



        fetchComment();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
               finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchComment() {

        String url = "https://api.instagram.com/v1/media/"+ mediaID +"/comments?client_id=" + CLIENT_ID;

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray commentsJSON = null;

                try {

                    commentsJSON = response.getJSONArray("data");

                    for (int i = 0; i < commentsJSON.length(); i++) {

                        JSONObject commentJSON = commentsJSON.getJSONObject(i);

                        InstagramPhoto comment = new InstagramPhoto();

                        comment.username = commentJSON.getJSONObject("from").getString("username");
                        comment.imgUserURL = commentJSON.getJSONObject("from").getString("profile_picture");
                        comment.comment = commentJSON.getString("text");


                        comments.add(comment);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                aComment.notifyDataSetChanged();
            }
        });

    }

}
