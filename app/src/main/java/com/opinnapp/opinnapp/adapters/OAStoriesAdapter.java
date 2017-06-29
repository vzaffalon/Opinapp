package com.opinnapp.opinnapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OADateUtil;
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryMultiChoiceImages;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;
import com.opinnapp.opinnapp.tabholder.OAApplication;
import com.opinnapp.opinnapp.tabholder.comments.CommentsActivity;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cayke on 11/05/17.
 */

public class OAStoriesAdapter extends RecyclerView.Adapter {
    private final int TEXT_ONLY_VIEW_TYPE = 0;
    private final int IMAGES_VIEW_TYPE = 1;

    private List<OAStory> stories;
    private Context context;

    public OAStoriesAdapter(List<OAStory> stories, Context context) {
        this.stories = stories;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (stories.get(position) instanceof OAStoryTextOnly)
            return TEXT_ONLY_VIEW_TYPE;
        else
            return IMAGES_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TEXT_ONLY_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_story_text_only, parent, false);
                return new OATextAdapter(view);
            case IMAGES_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_story_images, parent, false);
                return new OAImageAdapter(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OAImageAdapter) {
            ((OAImageAdapter) holder).bindStory((OAStoryMultiChoiceImages) stories.get(position));
        }
        else{
            ((OATextAdapter) holder).bindStory((OAStoryTextOnly) stories.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }


    private class OAImageAdapter extends RecyclerView.ViewHolder {
        Context context;
        CircleImageView ivUserPhoto;
        TextView tvUserName, tvStoryTime, tvDescription, tvExpirationTime, tvExpirationText, tvTags,tvNumberOfLikes,tvNumberofDislikes;
        LinearLayout btnLike, btnDislike, btnComments, btnBookmark;
        ImageView ivLike, ivDislike, ivComments, ivBookmark;

        ViewPager viewPager;
        PageIndicatorView indicatorView;

        OAStoryMultiChoiceImages story;

        OAImageAdapter(View itemView) {
            super(itemView);

            context = itemView.getContext();
            ivUserPhoto = (CircleImageView) itemView.findViewById(R.id.cell_story_iv_user_photo);
            tvUserName = (TextView) itemView.findViewById(R.id.cell_story_tv_user_name);
            //tvUserUrl = (TextView) itemView.findViewById(R.id.cell_story_tv_user_url);
            tvStoryTime = (TextView) itemView.findViewById(R.id.cell_story_tv_story_time);
            tvDescription = (TextView) itemView.findViewById(R.id.cell_story_tv_description);
            tvExpirationTime = (TextView) itemView.findViewById(R.id.cell_story_tv_expiration_time);
            tvExpirationText = (TextView) itemView.findViewById(R.id.cell_story_tv_expiration_text);
            tvTags = (TextView) itemView.findViewById(R.id.cell_story_tv_tags);
            btnLike = (LinearLayout) itemView.findViewById(R.id.cell_story_btn_like);
            btnBookmark = (LinearLayout) itemView.findViewById(R.id.cell_story_btn_bookmark);
            btnComments = (LinearLayout) itemView.findViewById(R.id.cell_story_btn_comments);
            btnDislike = (LinearLayout) itemView.findViewById(R.id.cell_story_btn_dislike);
            ivLike = (ImageView) itemView.findViewById(R.id.cell_story_iv_like);
            ivDislike = (ImageView) itemView.findViewById(R.id.cell_story_iv_dislike);
            ivComments = (ImageView) itemView.findViewById(R.id.cell_story_iv_comments);
            ivBookmark = (ImageView) itemView.findViewById(R.id.cell_story_iv_bookmark);


            tvNumberOfLikes = (TextView) itemView.findViewById(R.id.cell_number_of_likes);
            tvNumberofDislikes = (TextView) itemView.findViewById(R.id.cell_number_of_dislikes);
            tvNumberofDislikes.setVisibility(View.GONE);
            tvNumberOfLikes.setVisibility(View.GONE);

            viewPager = (ViewPager) itemView.findViewById(R.id.cell_story_view_pager);
            indicatorView = (PageIndicatorView) itemView.findViewById(R.id.cell_story_page_indicator);

            setListeners();
        }

        void setListeners() {
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
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up_filled);
                    ivLike.setImageDrawable(myDrawable);
                    myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down);
                    ivDislike.setImageDrawable(myDrawable);
                    tvNumberOfLikes.setText("1");
                    tvNumberofDislikes.setText("0");
                    tvNumberOfLikes.setVisibility(View.VISIBLE);
                    tvNumberofDislikes.setVisibility(View.VISIBLE);
                }
            });
            ivDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down_filled);
                    ivDislike.setImageDrawable(myDrawable);
                    myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up);
                    ivLike.setImageDrawable(myDrawable);
                    tvNumberOfLikes.setText("0");
                    tvNumberofDislikes.setText("1");
                    tvNumberOfLikes.setVisibility(View.VISIBLE);
                    tvNumberofDislikes.setVisibility(View.VISIBLE);
                }
            });

            ivBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (story.isBookmarked()) {
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

        void bindStory(OAStoryMultiChoiceImages story) {
            this.story = story;

            viewPager.setAdapter(new OAStoryImagesAdapter(story.getImages(), context));
            indicatorView.setViewPager(viewPager);
            viewPager.setCurrentItem(0);

            if (story.getOwner() != null) {
                Picasso.with(context).load(story.getOwner().getImagePath()).resize(100, 100).into(ivUserPhoto);
                tvUserName.setText(story.getOwner().getfName() + " " + story.getOwner().getlName());
                //tvUserUrl.setText("@" + story.getOwner().getUrl());
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            tvStoryTime.setText(dateFormat.format(story.getCreationDate()));

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

            if (!story.isBookmarked()) {
                ivBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark));
            }
            else {
                ivBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_filled));
            }
        }
    }

    private class OATextAdapter extends RecyclerView.ViewHolder {
        Context context;
        CircleImageView ivUserPhoto;
        TextView tvUserName, tvStoryTime, tvDescription, tvExpirationTime,tvExpirationText, tvTags,tvNumberOfLikes,tvNumberofDislikes;
        LinearLayout btnLike, btnDislike, btnComments, btnBookmark;
        ImageView ivLike, ivDislike, ivComments, ivBookmark;

        OAStoryTextOnly story;

        OATextAdapter(View itemView) {
            super(itemView);

            context = itemView.getContext();
            ivUserPhoto = (CircleImageView) itemView.findViewById(R.id.cell_story_iv_user_photo);
            tvUserName = (TextView) itemView.findViewById(R.id.cell_story_tv_user_name);
            //tvUserUrl = (TextView) itemView.findViewById(R.id.cell_story_tv_user_url);
            tvStoryTime = (TextView) itemView.findViewById(R.id.cell_story_tv_story_time);
            tvDescription = (TextView) itemView.findViewById(R.id.cell_story_tv_description);
            tvExpirationTime = (TextView) itemView.findViewById(R.id.cell_story_tv_expiration_time);
            tvExpirationText = (TextView) itemView.findViewById(R.id.cell_story_tv_expiration_text);
            tvTags = (TextView) itemView.findViewById(R.id.cell_story_tv_tags);
            btnLike = (LinearLayout) itemView.findViewById(R.id.cell_story_btn_like);
            btnBookmark = (LinearLayout) itemView.findViewById(R.id.cell_story_btn_bookmark);
            btnComments = (LinearLayout) itemView.findViewById(R.id.cell_story_btn_comments);
            btnDislike = (LinearLayout) itemView.findViewById(R.id.cell_story_btn_dislike);
            ivLike = (ImageView) itemView.findViewById(R.id.cell_story_iv_like);
            ivDislike = (ImageView) itemView.findViewById(R.id.cell_story_iv_dislike);
            ivComments = (ImageView) itemView.findViewById(R.id.cell_story_iv_comments);
            ivBookmark = (ImageView) itemView.findViewById(R.id.cell_story_iv_bookmark);

            tvNumberOfLikes = (TextView) itemView.findViewById(R.id.cell_number_of_likes);
            tvNumberofDislikes = (TextView) itemView.findViewById(R.id.cell_number_of_dislikes);
            tvNumberofDislikes.setVisibility(View.GONE);
            tvNumberOfLikes.setVisibility(View.GONE);

            setListeners();
        }

        void setListeners() {
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
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up_filled);
                    ivLike.setImageDrawable(myDrawable);
                    myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down);
                    ivDislike.setImageDrawable(myDrawable);
                    tvNumberOfLikes.setText("1");
                    tvNumberofDislikes.setText("0");
                    tvNumberOfLikes.setVisibility(View.VISIBLE);
                    tvNumberofDislikes.setVisibility(View.VISIBLE);
                }
            });
            ivDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down_filled);
                    ivDislike.setImageDrawable(myDrawable);
                    myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up);
                    ivLike.setImageDrawable(myDrawable);
                    tvNumberOfLikes.setText("0");
                    tvNumberofDislikes.setText("1");
                    tvNumberOfLikes.setVisibility(View.VISIBLE);
                    tvNumberofDislikes.setVisibility(View.VISIBLE);
                }
            });

            ivBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (story.isBookmarked()) {
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

        void bindStory(OAStoryTextOnly story) {
            this.story = story;

            if (story.getOwner() != null) {
                Picasso.with(context).load(story.getOwner().getImagePath()).resize(100, 100).into(ivUserPhoto);
                tvUserName.setText(story.getOwner().getfName() + " " + story.getOwner().getlName());
                //tvUserUrl.setText("@" + story.getOwner().getUrl());
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            tvStoryTime.setText(dateFormat.format(story.getCreationDate()));

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

            if (!story.isBookmarked()) {
                ivBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark));
            }
            else {
                ivBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_filled));
            }
        }
    }
}