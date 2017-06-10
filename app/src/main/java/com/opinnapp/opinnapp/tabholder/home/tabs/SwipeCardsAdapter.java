package com.opinnapp.opinnapp.tabholder.home.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;

import java.util.List;

/**
 * Created by vzaffalon on 10/06/17.
 */

public class SwipeCardsAdapter extends BaseAdapter {

    private List<String> mData;
    private Context context;

    public SwipeCardsAdapter(List<String> data,Context context) {
        this.mData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.cell_story_text_only, parent, false);
        //TextView textViewCard = (TextView) convertView.findViewById(R.id.textViewCard);
        //textViewCard.setText(mData.get(position));

        return convertView;
    }
}
