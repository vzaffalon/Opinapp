package com.opinnapp.opinnapp.tabholder.home.tabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;
import com.opinnapp.opinnapp.tabholder.comments.CommentsActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vzaffalon on 10/06/17.
 */

public class SwipeCardsAdapter extends BaseAdapter {

    private List<OAStory> stories;
    private Context context;

    private CircleImageView ivUserPhoto;
    private TextView tvUserName, tvUserUrl, tvStoryTime, tvDescription, tvExpirationTime, tvTags,tvNumberOfLikes,tvNumberofDislikes;
    private LinearLayout btnLike, btnDislike, btnComments, btnBookmark;
    private ImageView ivLike, ivDislike, ivComments, ivBookmark;

    public SwipeCardsAdapter(List<OAStory> stories,Context context) {
        this.stories = stories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stories.size();
    }

    @Override
    public OAStory getItem(int position) {
        return stories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.cell_story_text_only, parent, false);
        context = convertView.getContext();
        ivUserPhoto = (CircleImageView) convertView.findViewById(R.id.cell_story_iv_user_photo);
        tvUserName = (TextView) convertView.findViewById(R.id.cell_story_tv_user_name);
        tvUserUrl = (TextView) convertView.findViewById(R.id.cell_story_tv_user_url);
        tvStoryTime = (TextView) convertView.findViewById(R.id.cell_story_tv_story_time);
        tvDescription = (TextView) convertView.findViewById(R.id.cell_story_tv_description);
        tvExpirationTime = (TextView) convertView.findViewById(R.id.cell_story_tv_expiration_time);
        tvTags = (TextView) convertView.findViewById(R.id.cell_story_tv_tags);
        btnLike = (LinearLayout) convertView.findViewById(R.id.cell_story_btn_like);
        btnBookmark = (LinearLayout) convertView.findViewById(R.id.cell_story_btn_bookmark);
        btnComments = (LinearLayout) convertView.findViewById(R.id.cell_story_btn_comments);
        btnDislike = (LinearLayout) convertView.findViewById(R.id.cell_story_btn_dislike);
        ivLike = (ImageView) convertView.findViewById(R.id.cell_story_iv_like);
        ivDislike = (ImageView) convertView.findViewById(R.id.cell_story_iv_dislike);
        ivComments = (ImageView) convertView.findViewById(R.id.cell_story_iv_comments);
        ivBookmark = (ImageView) convertView.findViewById(R.id.cell_story_iv_bookmark);

        tvNumberOfLikes = (TextView) convertView.findViewById(R.id.cell_number_of_likes);
        tvNumberofDislikes = (TextView) convertView.findViewById(R.id.cell_number_of_dislikes);
        tvNumberofDislikes.setVisibility(View.GONE);
        tvNumberOfLikes.setVisibility(View.GONE);

        if (stories.get(position) instanceof  OAStoryTextOnly)
            bindStory((OAStoryTextOnly) stories.get(position));

        ivComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("storyId",stories.get(position).getId());
                intent.putExtra("userId",stories.get(position).getOwnerID());
                context.startActivity(intent);
            }
        });

        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up_filled);
                ivLike.setImageDrawable(myDrawable);
                tvNumberOfLikes.setVisibility(View.VISIBLE);
                tvNumberOfLikes.setText("1");
                tvNumberofDislikes.setVisibility(View.VISIBLE);
                tvNumberofDislikes.setText("0");

                myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down);
                ivDislike.setImageDrawable(myDrawable);
            }
        });
        ivDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down_filled);
                ivDislike.setImageDrawable(myDrawable);
                myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up);
                ivLike.setImageDrawable(myDrawable);
                tvNumberOfLikes.setVisibility(View.VISIBLE);
                tvNumberOfLikes.setText("0");
                tvNumberofDislikes.setVisibility(View.VISIBLE);
                tvNumberofDislikes.setText("1");
            }
        });

        ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_bookmark_filled);
                ivBookmark.setImageDrawable(myDrawable);
            }
        });

        return convertView;
    }

    void bindStory(OAStoryTextOnly story) {
        //todo dar bind nas coisas
        if (story.getOwner() != null) {
            Picasso.with(context).load(story.getOwner().getImagePath()).resize(100, 100).into(ivUserPhoto);
            tvUserName.setText(story.getOwner().getName());
            tvUserUrl.setText("@" + story.getOwner().getUrl());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        tvStoryTime.setText(dateFormat.format(story.getCreationDate()));

        //todo arrumar gambiarra
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        tvExpirationTime.setText(dateFormat2.format(story.getExpirationDate()));

        tvDescription.setText(story.getDescription());

        if (story.getTagsString() != null)
            tvTags.setText(story.getTagsString());
    }
}
