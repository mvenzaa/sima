package com.sibermediaabadi.wartaplus.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sibermediaabadi.wartaplus.R;
import com.sibermediaabadi.wartaplus.app.AppController;
import com.sibermediaabadi.wartaplus.model.article;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<article> articleItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListAdapter(Activity activity, List<article> articleItems) {
        this.activity = activity;
        this.articleItems = articleItems;
    }

    @Override
    public int getCount() {
        return articleItems.size();
    }

    @Override
    public Object getItem(int location) {
        return articleItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.article_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView featured_image = (NetworkImageView) convertView
                .findViewById(R.id.featured_image);

        TextView ID = (TextView) convertView.findViewById(R.id.ID);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        // getting video data for the row
        article m = articleItems.get(position);

        // image original
        featured_image.setImageUrl(m.getFeatured_image_Url(), imageLoader);

        // ID
        ID.setText(String.valueOf(m.getID()));

        // title
        title.setText("" + String.valueOf(m.getTitle()));

        // content
        content.setText(Html.fromHtml(String.valueOf(m.getContent())));

        // created at
        date.setText(String.valueOf(m.getDate()));


        return convertView;
    }
}
