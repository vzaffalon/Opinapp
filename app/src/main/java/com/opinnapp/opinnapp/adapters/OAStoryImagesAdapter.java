package com.opinnapp.opinnapp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OADatabase;
import com.opinnapp.opinnapp.models.OAImageOption;
import com.opinnapp.opinnapp.models.OAUtil;
import com.opinnapp.opinnapp.tabholder.OAApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

public class OAStoryImagesAdapter extends PagerAdapter {
    private Context context;
    private List<OAImageOption> images;
    private boolean shouldShowQtds = false;

    public OAStoryImagesAdapter(List<OAImageOption> images, Context context) {
        this.images = images;
        this.context = context;
        checkIfShouldShowQtds();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.cell_image_and_like, container, false);

        ImageView photo = (ImageView) view.findViewById(R.id.cell_image_and_like_iv_photo);
        LinearLayout btnLike = (LinearLayout) view.findViewById(R.id.cell_image_and_like_btn_like);
        final ImageView like = (ImageView) view.findViewById(R.id.cell_image_and_like_iv_like);
        final TextView numberOfLikes = (TextView) view.findViewById(R.id.cell_image_number_of_likes_on_image);

        Picasso.with(context).load(images.get(position).getImagePath()).into(photo);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shouldShowQtds = true;

                if (images.get(position).isLiked) {
                    OADatabase.likeOption(false, images.get(position), OAApplication.getUser());
                    like.setImageResource(R.drawable.ic_thumbs_up);
                    OAUtil.remove(images.get(position).getUsersIdThatLiked(), OAApplication.getUser().getId());
                }
                else {
                    OADatabase.likeOption(true, images.get(position), OAApplication.getUser());
                    like.setImageResource(R.drawable.ic_thumbs_up_filled);
                    OAUtil.add(images.get(position).getUsersIdThatLiked(), OAApplication.getUser().getId());
                }

                numberOfLikes.setVisibility(View.VISIBLE);
                numberOfLikes.setText(String.valueOf(images.get(position).getUsersIdThatLiked().size()));
            }
        });

        if (shouldShowQtds) {
            numberOfLikes.setVisibility(View.VISIBLE);
            numberOfLikes.setText(String.valueOf(images.get(position).getUsersIdThatLiked().size()));
        }

        if (images.get(position).isLiked)
            like.setImageResource(R.drawable.ic_thumbs_up_filled);
        else
            like.setImageResource(R.drawable.ic_thumbs_up);


        container.addView(view);
        return view;
    }

    private void checkIfShouldShowQtds() {
        for (OAImageOption imageOption : images) {
            if (imageOption.isLiked)
                shouldShowQtds = true;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}