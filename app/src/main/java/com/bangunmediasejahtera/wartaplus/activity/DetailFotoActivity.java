package com.bangunmediasejahtera.wartaplus.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.bangunmediasejahtera.wartaplus.Config;
import com.bangunmediasejahtera.wartaplus.R;
import com.bangunmediasejahtera.wartaplus.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class DetailFotoActivity extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    private String id;
    private String share_link,share_title;
    private TextView ID, title, content, created_at, youtube_id, image_small;

    // Log tag
    private static final String TAG = DetailArticleActivity.class.getSimpleName();

    // Movies json url
    //private static final String url = "http://stopnarkoba.id/service/artikels/";
    NetworkImageView image;
    //private ArticleDetailAdapter adapter;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    ProgressBar bar;
    ParallaxScrollView content_artikel;

    private SliderLayout imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_foto);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        content_artikel = (ParallaxScrollView) findViewById(R.id.content_artikel);
        bar = (ProgressBar) findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        content_artikel.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Foto");

        ID = (TextView) findViewById(R.id.ID);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        created_at = (TextView) findViewById(R.id.created_at);
        imageSlider = (SliderLayout) findViewById(R.id.slider);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");

        }
        Log.d("SN", id);

        WebView top = (WebView) findViewById(R.id.ads_top);
        Ads.init(top, 0); // 0 top position
        WebView bottom = (WebView) findViewById(R.id.ads_bottom);
        Ads.init(bottom, 1); // 1 bottom position


        // creating HashMap


        // Creating volley request obj
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET, Config.main_url + "/wonderplugins/" + id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                         Log.d("SN", response.toString());

                        try {
                            HashMap<String, String> image_maps = new HashMap<String, String>();
                            JSONObject data = response.getJSONObject("data");
                            JSONArray slides = data.getJSONArray("slides");
                            String title_slide = "";

                            // SET SLIDE IMAGE
                            for (int i = 0; i < slides.length(); i++) {
                                JSONObject slide = slides.getJSONObject(i);
                                image_maps.put(slide.getString("description"), slide.getString("image"));
                            }

                            // SET TITLE DARI DATA ARRAY PERTAMA
                            for (int x = 0; x < 1; x++) {
                                JSONObject slide = slides.getJSONObject(x);
                                title_slide = slide.getString("title");
                            }
                            title.setText(title_slide);
                            String date = response.getString("time");
                            created_at.setText(date + " WIB");
                            content.setText("");
                            share_link  = "http://www.wartaplus.com/3958-2/";
                            share_title = "Galeri Foto Wartaplus";

                            for (String name : image_maps.keySet()) {
                                TextSliderView textSliderView = new TextSliderView(DetailFotoActivity.this);
                                // initialize a SliderLayout
                                textSliderView
                                        .description(name)
                                        .image(image_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(DetailFotoActivity.this);

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name);

                                imageSlider.addSlider(textSliderView);
                            }

                            imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            imageSlider.setCustomAnimation(new DescriptionAnimation());
                            imageSlider.setDuration(20000);
                            imageSlider.addOnPageChangeListener(DetailFotoActivity.this);

                            bar.setVisibility(View.GONE);
                            content_artikel.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Log.d("SN", response.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bar.setVisibility(View.GONE);
            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq, "SN");











    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider
        // before activity or fragment is destroyed
        imageSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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
                intent.putExtra(Intent.EXTRA_TEXT, share_link);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, share_title);
                startActivity(Intent.createChooser(intent, "Share"));
                return true;
            case R.id.action_visit:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(share_link));
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

