package com.opinnapp.opinnapp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAImageOption;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cayke on 09/05/17.
 */

public class OAStoryImagesAdapter extends PagerAdapter {
    private Context context;
    private List<OAImageOption> images;

    public OAStoryImagesAdapter(List<OAImageOption> images, Context context) {
        this.images = images;
        this.context = context;
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
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.cell_image_and_like, container, false);

        ImageView photo = (ImageView) view.findViewById(R.id.cell_image_and_like_iv_photo);
        LinearLayout btnLike = (LinearLayout) view.findViewById(R.id.cell_image_and_like_btn_like);
        ImageView like = (ImageView) view.findViewById(R.id.cell_image_and_like_iv_like);

        Picasso.with(context).load(images.get(position).getImagePath()).into(photo);

        //todo bind values

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}