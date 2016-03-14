package com.dattgk.instagramclient;

import android.content.Context;
import android.media.Image;
import android.os.HandlerThread;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dat The Geek on 3/12/2016.
 */
public class InstagramCommentAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramCommentAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto comment = getItem(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);

        }
        TextView tvUserCmt = (TextView) convertView.findViewById(R.id.tvUsernameCmt);
        ImageView ivUserImage = (ImageView) convertView.findViewById(R.id.ivUserPhoto);

        String userCmt = "<font color ='#80e9'>" + "<b>" + comment.username + "</b>" + "</font>" + " " + comment.comment;

        tvUserCmt.setText(Html.fromHtml(userCmt));
        Picasso.with(getContext()).load(comment.imgUserURL).placeholder(R.drawable.preloader).into(ivUserImage);

        return convertView;
    }
}
