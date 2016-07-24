package com.sibermediaabadi.wartaplus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.sibermediaabadi.wartaplus.R;
import com.sibermediaabadi.wartaplus.app.AppController;
import com.sibermediaabadi.wartaplus.model.article;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Probook 4341s on 5/26/2016.
 */
public class DetailArticle extends AppCompatActivity {

    private String id;

    private TextView ID, title, content, created_at, youtube_id, image_small;

    // Log tag
    private static final String TAG = DetailArticle.class.getSimpleName();

    // Movies json url
    //private static final String url = "http://stopnarkoba.id/service/artikels/";
    NetworkImageView image;
    //private ArticleDetailAdapter adapter;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    ProgressBar bar;
    ParallaxScrollView content_artikel;

    @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        content_artikel = (ParallaxScrollView) findViewById(R.id.content_artikel);
        bar = (ProgressBar) findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        content_artikel.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Berita");

        ID = (TextView)findViewById(R.id.ID);
        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);
        created_at = (TextView)findViewById(R.id.created_at);
        image = (NetworkImageView)findViewById(R.id.image_small);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");

        }
        Log.d("SN", id);


        for (int i = 0; i < 20; i++) {

            title.setText("KPU Papua Akan Sidak ke Kabupaten Sarmi");
            created_at.setText("15 Juli 2016 15:00 WIB");
            content.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque efficitur quis ante ut viverra. Quisque iaculis nisl nec erat iaculis, nec facilisis massa dapibus. Sed sodales est at eros fringilla, lobortis venenatis libero dictum. Donec et eleifend dui. Nulla ac augue sit amet nibh consequat condimentum. Praesent venenatis purus quam, eget pretium dolor dictum nec. Aliquam at libero pharetra, pulvinar diam sit amet, maximus sapien. Phasellus nec vehicula quam. Curabitur quam nisi, vulputate vel tincidunt pretium, laoreet quis sem. Proin finibus sagittis sollicitudin. Praesent pretium, est sed maximus congue, sapien purus tincidunt purus, et semper eros risus ac urna. Pellentesque interdum, nulla ut pretium commodo, sapien velit mattis lacus, id fermentum felis urna nec lorem. Duis dignissim lorem vitae libero efficitur, sodales dapibus justo placerat.");
            image.setImageUrl(("http://www.wartaplus.com/wp-content/uploads/2016/07/Ketua-Komisi-Pemilihan-Umum-KPU-Prov.Papua-Adam-Arisoi.-Foto-Icahd.-660x330.jpg"), imageLoader);

            bar.setVisibility(View.GONE);
            content_artikel.setVisibility(View.VISIBLE);

        }
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "http://www.wartaplus.com/");
                startActivity(Intent.createChooser(intent, "Share"));
                return true;
            case R.id.action_visit:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wartaplus.com/"));
                startActivity(browserIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}

