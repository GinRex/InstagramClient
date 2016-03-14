package com.dattgk.instagramclient;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Dat The Geek on 3/11/2016.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for this pos
        InstagramPhoto photo = getItem(position);

        // check if using a recycle view, if not: inflate
        if (convertView == null) {
            //create new view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvUsername2 = (TextView) convertView.findViewById(R.id.tvUsername2);
        TextView tvLikeCount = (TextView) convertView.findViewById(R.id.tvLike);
        ImageView ivUserPic= (ImageView) convertView.findViewById(R.id.ivUserPic);
        TextView tvDayutil = (TextView) convertView.findViewById(R.id.tvDayutil);
        TextView tvViewcmt = (TextView) convertView.findViewById(R.id.tvComment);
        TextView tvRCmt = (TextView) convertView.findViewById(R.id.tvRComment);
        TextView tvRCmt2 = (TextView) convertView.findViewById(R.id.tvRComment2);



        // set id to tag of the current view
        tvViewcmt.setTag(photo.id);




        int leth = photo.username.length();


        //print User name in blue and bold + caption in normal
        String userCaption = "<font color ='#80e9'>" + "<b>" + photo.username + "</b>" + "</font>" + " " + photo.caption;


        tvCaption.setText(Html.fromHtml(userCaption));
        //tvUsername.setText(photo.username);
        tvUsername2.setText(photo.username);
        DecimalFormat myFormatter = new DecimalFormat("#,###");
        String output = myFormatter.format(photo.likesCount);

        tvLikeCount.setText("♥ " + output + " likes");



        // set User cmt in bue + bold  + their cmt
        String userCmt1 = "<font color ='#80e9'>" + "<b>" + photo.userCmt + "</b>" + "</font>" + " " + photo.comment;
        String userCmt2 = "<font color ='#80e9'>" + "<b>" + photo.userCmt2 + "</b>" + "</font>" + " " + photo.comment2;


        //take 2 latest cmt from postion obj and show to the textview
        tvRCmt.setText(Html.fromHtml(userCmt1));
        tvRCmt2.setText(Html.fromHtml(userCmt2));

        long timeUtil = Long.valueOf(photo.timeUtil) * 1000;

        tvDayutil.setText(DateUtils.getRelativeTimeSpanString(timeUtil, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        // clear the imageview

        ivPhoto.setImageResource(0);

        // insert image using picaso
        Picasso.with(getContext()).load(photo.imageURL).placeholder(R.drawable.loadind).error(R.drawable.loadind).resize(600, 600).centerInside().into(ivPhoto);


        // round the userpic:

        //Transformation round = (Transformation) new RoundedTransformationBuilder().borderColor(Color.BLUE).borderWidthDp(3).cornerRadiusDp(30).oval(false).build();

        ivUserPic.setImageResource(0);
        Picasso.with(getContext()).load(photo.imgUserURL).placeholder(R.drawable.preloader).into(ivUserPic);

        return convertView;
    }


    // take the length of the username and create a margin before the caption equals to it
    /*public String spaces(int length) {

        if (length < 1) {
            return "";
        }

        StringBuilder st = new StringBuilder(" ");
        for (int i = 0; i < length; i++) {
            st.append("  ");
        }
        return st.toString();
    }
    */

}
