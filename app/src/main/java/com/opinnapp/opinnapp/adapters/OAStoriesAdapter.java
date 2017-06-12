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
import com.opinnapp.opinnapp.models.OAStory;
import com.opinnapp.opinnapp.models.OAStoryMultiChoiceImages;
import com.opinnapp.opinnapp.models.OAStoryTextOnly;
import com.opinnapp.opinnapp.tabholder.comments.CommentsActivity;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
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

            ((OAImageAdapter) holder).ivComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("storyId",stories.get(position).getId());
                    intent.putExtra("userId",stories.get(position).getOwnerID());
                    context.startActivity(intent);
                }
            });
            ((OAImageAdapter) holder).ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up_filled);
                    ((OAImageAdapter) holder).ivLike.setImageDrawable(myDrawable);
                    myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down);
                    ((OAImageAdapter) holder).ivDislike.setImageDrawable(myDrawable);
                    ((OAImageAdapter) holder).tvNumberOfLikes.setVisibility(View.VISIBLE);
                    ((OAImageAdapter) holder).tvNumberofDislikes.setVisibility(View.VISIBLE);
                }
            });
            ((OAImageAdapter) holder).ivDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down_fil);
                    ((OAImageAdapter) holder).ivDislike.setImageDrawable(myDrawable);
                    myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up);
                    ((OAImageAdapter) holder).ivLike.setImageDrawable(myDrawable);
                    ((OAImageAdapter) holder).tvNumberOfLikes.setVisibility(View.VISIBLE);
                    ((OAImageAdapter) holder).tvNumberofDislikes.setVisibility(View.VISIBLE);
                }
            });

            ((OAImageAdapter) holder).ivBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_bookmark_filled);
                    if (((OAImageAdapter) holder).ivBookmark.getDrawable() == myDrawable) {
                        myDrawable = context.getResources().getDrawable(R.drawable.ic_bookmark);
                    }else{
                        myDrawable = context.getResources().getDrawable(R.drawable.ic_bookmark_filled);
                    }
                    ((OAImageAdapter) holder).ivBookmark.setImageDrawable(myDrawable);
                }
            });

        }
        else{
            ((OATextAdapter) holder).bindStory((OAStoryTextOnly) stories.get(position));

            ((OATextAdapter) holder).ivComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("storyId",stories.get(position).getId());
                    intent.putExtra("userId",stories.get(position).getOwnerID());
                    context.startActivity(intent);
                }
            });
            ((OATextAdapter) holder).ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up_filled);
                    ((OATextAdapter) holder).ivLike.setImageDrawable(myDrawable);
                    ((OATextAdapter) holder).tvNumberOfLikes.setVisibility(View.VISIBLE);
                    ((OATextAdapter) holder).tvNumberofDislikes.setVisibility(View.VISIBLE);

                    myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down);
                    ((OATextAdapter) holder).ivDislike.setImageDrawable(myDrawable);
                }
            });
            ((OATextAdapter) holder).ivDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_down_fil);
                    ((OATextAdapter) holder).ivDislike.setImageDrawable(myDrawable);
                    myDrawable = context.getResources().getDrawable(R.drawable.ic_thumbs_up);
                    ((OATextAdapter) holder).ivLike.setImageDrawable(myDrawable);
                    ((OATextAdapter) holder).tvNumberOfLikes.setVisibility(View.VISIBLE);
                    ((OATextAdapter) holder).tvNumberofDislikes.setVisibility(View.VISIBLE);
                }
            });

            ((OATextAdapter) holder).ivBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_bookmark_filled);
                    ((OATextAdapter) holder).ivBookmark.setImageDrawable(myDrawable);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }


    private class OAImageAdapter extends RecyclerView.ViewHolder {
        Context context;
        CircleImageView ivUserPhoto;
        TextView tvUserName, tvUserUrl, tvStoryTime, tvDescription, tvExpirationTime, tvTags,tvNumberOfLikes,tvNumberofDislikes;
        LinearLayout btnLike, btnDislike, btnComments, btnBookmark;
        ImageView ivLike, ivDislike, ivComments, ivBookmark;

        ViewPager viewPager;
        PageIndicatorView indicatorView;

        OAImageAdapter(View itemView) {
            super(itemView);

            context = itemView.getContext();
            ivUserPhoto = (CircleImageView) itemView.findViewById(R.id.cell_story_iv_user_photo);
            tvUserName = (TextView) itemView.findViewById(R.id.cell_story_tv_user_name);
            tvUserUrl = (TextView) itemView.findViewById(R.id.cell_story_tv_user_url);
            tvStoryTime = (TextView) itemView.findViewById(R.id.cell_story_tv_story_time);
            tvDescription = (TextView) itemView.findViewById(R.id.cell_story_tv_description);
            tvExpirationTime = (TextView) itemView.findViewById(R.id.cell_story_tv_expiration_time);
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

        }

        void bindStory(OAStoryMultiChoiceImages story) {
            viewPager.setAdapter(new OAStoryImagesAdapter(story.getImages(), context));
            indicatorView.setViewPager(viewPager);


            //todo dar bind das coisas
        }
    }

    private class OATextAdapter extends RecyclerView.ViewHolder {
        Context context;
        CircleImageView ivUserPhoto;
        TextView tvUserName, tvUserUrl, tvStoryTime, tvDescription, tvExpirationTime, tvTags,tvNumberOfLikes,tvNumberofDislikes;
        LinearLayout btnLike, btnDislike, btnComments, btnBookmark;
        ImageView ivLike, ivDislike, ivComments, ivBookmark;

        OATextAdapter(View itemView) {
            super(itemView);

            context = itemView.getContext();
            ivUserPhoto = (CircleImageView) itemView.findViewById(R.id.cell_story_iv_user_photo);
            tvUserName = (TextView) itemView.findViewById(R.id.cell_story_tv_user_name);
            tvUserUrl = (TextView) itemView.findViewById(R.id.cell_story_tv_user_url);
            tvStoryTime = (TextView) itemView.findViewById(R.id.cell_story_tv_story_time);
            tvDescription = (TextView) itemView.findViewById(R.id.cell_story_tv_description);
            tvExpirationTime = (TextView) itemView.findViewById(R.id.cell_story_tv_expiration_time);
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

            //todo onclicklisteners nos buttons

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
}