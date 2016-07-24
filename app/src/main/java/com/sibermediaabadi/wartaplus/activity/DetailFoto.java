package com.sibermediaabadi.wartaplus.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.sibermediaabadi.wartaplus.R;
import com.sibermediaabadi.wartaplus.app.AppController;

import java.util.HashMap;


public class DetailFoto extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

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

    private SliderLayout imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_foto);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        content_artikel = (ParallaxScrollView) findViewById(R.id.content_artikel);
        bar = (ProgressBar) findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        content_artikel.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Foto");

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

        // creating HashMap
        HashMap<String, String> image_maps = new HashMap<String, String>();
        image_maps.put("Seorang penari memerankan Roh dalam cerita dalam pentas musik Ensambel Papua berjudul RUR dan NIN (Roh dan Bayangan)/foto: Indra",
                "http://www.wartaplus.com/wp-content/uploads/2016/05/fais.jpg");
        image_maps.put("Seorang pemain memainkan Pikon salah satu alat musik tradisional asal pegunungan tengah Papua dalam pentas musik Ensambel Papua berjudul RUR dan NIN (Roh dan Bayangan) di halaman SMA Negeri 1 Wamena, Kabupaten Jayawijaya/foto: indra",
                "http://www.wartaplus.com/wp-content/uploads/2016/05/Komunitas-Action-Ensambel-Papua-4.jpg");
        image_maps.put("Pemain meniup FUU (kerang laut dan bambu) yang berasal dari pesisir utara Papua dan pesisir selatan Papua Barat pentas musik Ensembel Papua berjudul RUR dan NIN (Roh dan Bayangan) di halaman SMA Negeri 1 Wamena, Kabupaten Jayawijaya/foto: indra(1)",
                "http://www.wartaplus.com/wp-content/uploads/2016/05/Komunitas-Action-Ensambel-Papua-5.jpg");
        image_maps.put("Pemain meniup FUU (kerang laut dan bambu) yang berasal dari pesisir utara Papua dan pesisir selatan Papua Barat pentas musik Ensembel Papua berjudul RUR dan NIN (Roh dan Bayangan) di halaman SMA Negeri 1 Wamena, Kabupaten Jayawijaya/foto: indra(2)",
                "http://www.wartaplus.com/wp-content/uploads/2016/05/Komunitas-Action-Ensambel-Papua-6.jpg");

        for (String name : image_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(image_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

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
        imageSlider.addOnPageChangeListener(this);



        for (int i = 0; i < 20; i++) {

            title.setText("Pementasan Musik Ensambel Papua Pertama Kali di Ujung Timur Indonesia");
            created_at.setText("15 Juli 2016 15:00 WIB");
            content.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque efficitur quis ante ut viverra. Quisque iaculis nisl nec erat iaculis, nec facilisis massa dapibus. Sed sodales est at eros fringilla, lobortis venenatis libero dictum. Donec et eleifend dui. Nulla ac augue sit amet nibh consequat condimentum. Praesent venenatis purus quam, eget pretium dolor dictum nec. Aliquam at libero pharetra, pulvinar diam sit amet, maximus sapien. Phasellus nec vehicula quam. Curabitur quam nisi, vulputate vel tincidunt pretium, laoreet quis sem. Proin finibus sagittis sollicitudin. Praesent pretium, est sed maximus congue, sapien purus tincidunt purus, et semper eros risus ac urna. Pellentesque interdum, nulla ut pretium commodo, sapien velit mattis lacus, id fermentum felis urna nec lorem. Duis dignissim lorem vitae libero efficitur, sodales dapibus justo placerat.");

            bar.setVisibility(View.GONE);
            content_artikel.setVisibility(View.VISIBLE);


        }

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

