package com.opinnapp.opinnapp.tabholder.home.tabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.adapters.OAStoryImagesAdapter;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OADateUtil;
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryMultiChoiceImages;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;
import com.opinnapp.opinnapp.tabholder.OAApplication;
import com.opinnapp.opinnapp.tabholder.comments.CommentsActivity;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vzaffalon on 10/06/17.
 */

public class SwipeCardsAdapter extends BaseAdapter {

    private List<OAStory> stories;
    private Context context;

    private CircleImageView ivUserPhoto;
    private TextView tvUserName, tvStoryTime, tvDescription, tvExpirationTime,tvExpirationText, tvTags,tvNumberOfLikes,tvNumberofDislikes;
    private LinearLayout btnLike, btnDislike, btnComments, btnBookmark;
    private ImageView ivLike, ivDislike, ivComments, ivBookmark;
    ViewPager viewPager;
    PageIndicatorView indicatorView;

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

        if (stories.get(position) instanceof OAStoryMultiChoiceImages) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cell_story_images, parent, false);
        }
        if (stories.get(position) instanceof  OAStoryTextOnly) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cell_story_text_only, parent, false);
        }
        context = convertView.getContext();
        ivUserPhoto = (CircleImageView) convertView.findViewById(R.id.cell_story_iv_user_photo);
        tvUserName = (TextView) convertView.findViewById(R.id.cell_story_tv_user_name);
        //tvUserUrl = (TextView) convertView.findViewById(R.id.cell_story_tv_user_url);
        tvStoryTime = (TextView) convertView.findViewById(R.id.cell_story_tv_story_time);
        tvDescription = (TextView) convertView.findViewById(R.id.cell_story_tv_description);
        tvExpirationTime = (TextView) convertView.findViewById(R.id.cell_story_tv_expiration_time);
        tvExpirationText = (TextView) convertView.findViewById(R.id.cell_story_tv_expiration_text);
        tvTags = (TextView) convertView.findViewById(R.id.cell_story_tv_tags);
        btnLike = (LinearLayout) convertView.findViewById(R.id.cell_story_btn_like);
        btnBookmark = (LinearLayout) convertView.findViewById(R.id.cell_story_btn_bookmark);
        btnComments = (LinearLayout) convertView.findViewById(R.id.cell_story_btn_comments);
        btnDislike = (LinearLayout) convertView.findViewById(R.id.cell_story_btn_dislike);
        ivLike = (ImageView) convertView.findViewById(R.id.cell_story_iv_like);
        ivDislike = (ImageView) convertView.findViewById(R.id.cell_story_iv_dislike);
        ivComments = (ImageView) convertView.findViewById(R.id.cell_story_iv_comments);
        ivBookmark = (ImageView) convertView.findViewById(R.id.cell_story_iv_bookmark);

        viewPager = (ViewPager) convertView.findViewById(R.id.cell_story_view_pager);
        indicatorView = (PageIndicatorView) convertView.findViewById(R.id.cell_story_page_indicator);

        tvNumberOfLikes = (TextView) convertView.findViewById(R.id.cell_number_of_likes);
        tvNumberofDislikes = (TextView) convertView.findViewById(R.id.cell_number_of_dislikes);
        tvNumberofDislikes.setVisibility(View.GONE);
        tvNumberOfLikes.setVisibility(View.GONE);

        setListeners(stories.get(position));

        bindStory(stories.get(position));

        return convertView;
    }

    void setListeners(final OAStory story) {
        ivComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("storyId",story.getId());
                intent.putExtra("userId",story.getOwnerID());
                context.startActivity(intent);
            }
        });
        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (story.isDisliked)
                    OADatabase.dislikeStory(false, story, OAApplication.getUser());

                OADatabase.likeStory(true, story, OAApplication.getUser());

                Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up_filled);
                ivLike.setImageDrawable(myDrawable);
                myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down);
                ivDislike.setImageDrawable(myDrawable);

                updateLikesAndDislikes(story);
            }
        });
        ivDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (story.isLiked)
                    OADatabase.likeStory(false, story, OAApplication.getUser());

                OADatabase.dislikeStory(true, story, OAApplication.getUser());

                Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down_filled);
                ivDislike.setImageDrawable(myDrawable);
                myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up);
                ivLike.setImageDrawable(myDrawable);

                updateLikesAndDislikes(story);
            }
        });

        ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (story.isBookmarked) {
                    ivBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark));
                    OADatabase.favoriteStory(false, story, OAApplication.getUser());
                }
                else {
                    ivBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_filled));
                    OADatabase.favoriteStory(true, story, OAApplication.getUser());
                }
            }
        });
    }

    private void updateLikesAndDislikes(final OAStory story) {
        if (story.isLiked)
            tvNumberOfLikes.setText(String.valueOf(story.getUsersIdThatLiked().size() + 1));
        else
            tvNumberOfLikes.setText(String.valueOf(story.getUsersIdThatLiked().size()));

        if (story.isDisliked)
            tvNumberofDislikes.setText(String.valueOf(story.getUsersIdThatDisliked().size() + 1));
        else
            tvNumberofDislikes.setText(String.valueOf(story.getUsersIdThatDisliked().size() + 1));

        tvNumberOfLikes.setVisibility(View.VISIBLE);
        tvNumberofDislikes.setVisibility(View.VISIBLE);
    }

    void bindStory(OAStory story) {
        if (story instanceof OAStoryMultiChoiceImages) {
            viewPager.setAdapter(new OAStoryImagesAdapter(((OAStoryMultiChoiceImages) story).getImages(), context));
            indicatorView.setViewPager(viewPager);
            viewPager.setCurrentItem(0);
        }

        if (story.getOwner() != null) {
            Picasso.with(context).load(story.getOwner().getImagePath()).resize(100, 100).into(ivUserPhoto);
            tvUserName.setText(story.getOwner().getfName() + " " + story.getOwner().getlName());
        }

        tvStoryTime.setText(OADateUtil.getTimeAgo(story.getCreationDate().getTime()));

        Date now = new Date();
        if (story.getExpirationDate().getTime() > now.getTime()) {
            tvExpirationTime.setText(OADateUtil.exparationString(story.getExpirationDate()));
            tvExpirationText.setText("para expirar");
        }
        else {
            tvExpirationTime.setText("");
            tvExpirationText.setText("Já expirado");
        }

        tvDescription.setText(story.getDescription());

        if (story.getTagsString() != null)
            tvTags.setText(story.getTagsString());

        if (!story.isBookmarked) {
            ivBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark));
        }
        else {
            ivBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_filled));
        }
    }
}
