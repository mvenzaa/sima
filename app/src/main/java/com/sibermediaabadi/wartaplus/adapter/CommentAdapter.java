package com.sibermediaabadi.wartaplus.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibermediaabadi.wartaplus.R;
import com.sibermediaabadi.wartaplus.model.comment;
import com.squareup.picasso.Picasso;

import java.util.List;



public class CommentAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<comment> commentItems;

    public CommentAdapter(Activity activity, List<comment> commentItems) {
        this.activity = activity;
        this.commentItems = commentItems;
    }

    @Override
    public int getCount() {
        return commentItems.size();
    }

    @Override
    public Object getItem(int location) {
        return commentItems.get(location);
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
            convertView = inflater.inflate(R.layout.item_comment, null);

        ImageView image = (ImageView) convertView.findViewById(R.id.avatar);

        TextView ID = (TextView) convertView.findViewById(R.id.ID);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        TextView date = (TextView) convertView.findViewById(R.id.date);


        comment m = commentItems.get(position);
        Picasso.with(this.activity).load(m.getAvatar())
                .placeholder( R.mipmap.icon_medium )
                .error( R.mipmap.icon_medium )
                .into(image);
        ID.setText(String.valueOf(m.getID()));
        name.setText("" + String.valueOf(m.getName()));
        content.setText(Html.fromHtml(m.getContent()));
        date.setText(String.valueOf(m.getDate()));


        return convertView;
    }
}
