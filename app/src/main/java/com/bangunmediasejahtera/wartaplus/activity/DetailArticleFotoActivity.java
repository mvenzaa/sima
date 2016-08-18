package com.bangunmediasejahtera.wartaplus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bangunmediasejahtera.wartaplus.R;
import com.bangunmediasejahtera.wartaplus.app.AppController;

import uk.co.senab.photoview.PhotoViewAttacher;


public class DetailArticleFotoActivity extends AppCompatActivity {

    private String image_url;
    private String detail;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article_foto);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Foto");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            image_url = extras.getString("image_url");
            detail = extras.getString("detail");

        }
        parseData();

    }


    private void parseData() {

        try {

            TextView detail_text = (TextView) findViewById(R.id.detail);
            detail_text.setText(detail);

            final NetworkImageView image_network_image = (NetworkImageView) findViewById(R.id.image_url);
            imageLoader.get(image_url,
                    ImageLoader.getImageListener(image_network_image,
                            R.mipmap.icon_medium, R.mipmap.icon_medium));
            image_network_image.setImageUrl(image_url, imageLoader);
            PhotoViewAttacher mAttacher = new PhotoViewAttacher(image_network_image);
            mAttacher.update();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
