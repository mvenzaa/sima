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
import com.sibermediaabadi.wartaplus.model.foto;

import java.util.List;

/**
 * Created by Probook 4341s on 7/23/2016.
 */
public class FotoListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<foto> fotoItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FotoListAdapter(Activity activity, List<foto> fotoItems){
        this.activity = activity;
        this.fotoItems = fotoItems;
    }

    @Override
    public int getCount() {
        return fotoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return fotoItems.get(location);
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
            convertView = inflater.inflate(R.layout.foto_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView image_small = (NetworkImageView) convertView
                .findViewById(R.id.image_small);

        NetworkImageView thumbnail1 = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail1);

        NetworkImageView thumbnail2 = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail2);

        NetworkImageView thumbnail3 = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail3);

        NetworkImageView thumbnail4 = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail4);


        TextView ID = (TextView) convertView.findViewById(R.id.ID);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        TextView created_at = (TextView) convertView.findViewById(R.id.created_at);

        foto m = fotoItems.get(position);

        // image original
        image_small.setImageUrl(m.getImage_small_Url(), imageLoader);

        // thumbnail1
        thumbnail1.setImageUrl(m.getThumbnail1(), imageLoader);

        // thumbnail2
        thumbnail2.setImageUrl(m.getThumbnail2(), imageLoader);

        // thumbnail3
        thumbnail3.setImageUrl(m.getThumbnail3(), imageLoader);

        // thumbnail4
        thumbnail4.setImageUrl(m.getThumbnail4(), imageLoader);

        // ID
        ID.setText(String.valueOf(m.getID()));

        // title
        title.setText("" + String.valueOf(m.getTitle()));

        // content
        content.setText(Html.fromHtml(String.valueOf(m.getContent())));

        // created at
        created_at.setText(String.valueOf(m.getCreated_at()));


        return convertView;
    }
}
