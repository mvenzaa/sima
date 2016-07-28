package com.sibermediaabadi.wartaplus.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sibermediaabadi.wartaplus.Config;
import com.sibermediaabadi.wartaplus.R;
import com.sibermediaabadi.wartaplus.adapter.TagListAdapter;
import com.sibermediaabadi.wartaplus.app.AppController;
import com.sibermediaabadi.wartaplus.model.article;
import com.sibermediaabadi.wartaplus.util.DynamicHeightListView;
import com.sibermediaabadi.wartaplus.util.MyWebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Probook 4341s on 5/26/2016.
 */
public class DetailArticle extends AppCompatActivity {

    private String id;

    private TextView ID, title, content, date, author,tagText;
    private String share_link,share_title;

    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private TagListAdapter adapter;


    ProgressBar bar;
    ParallaxScrollView content_artikel;

    @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        content_artikel = (ParallaxScrollView) findViewById(R.id.content_artikel);
        bar = (ProgressBar) findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        content_artikel.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");

        listView = (ListView) findViewById(R.id.list);
        adapter = new TagListAdapter(this, articleList);
        listView.setAdapter(adapter);
        listView.setFocusable(false);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                TextView s = (TextView) v.findViewById(R.id.content);
                String tagSlug = s.getText().toString();

                TextView n = (TextView) v.findViewById(R.id.title);
                String tagName = n.getText().toString();

                Intent i = new Intent(getApplicationContext(), TagListDetail.class);
                i.putExtra("slug", tagSlug);
                i.putExtra("name", tagName);
                startActivity(i);
            }
        });






        ID = (TextView)findViewById(R.id.ID);
        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);
        date = (TextView)findViewById(R.id.date);

        author = (TextView)findViewById(R.id.author);
        tagText = (TextView)findViewById(R.id.tagText);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");

        }
        Log.d("SN", id);


        String uuid = UUID.randomUUID().toString();
        String url = "http://wartaplus.com/wp-content/plugins/json-rest-api/disqus.php?disqus_id="+id+"&cache="+uuid;
        WebView webDisqus = (WebView) findViewById(R.id.disqus);
        WebSettings webSettings2 = webDisqus.getSettings();
        webSettings2.setJavaScriptEnabled(true);
        webSettings2.setBuiltInZoomControls(false);
        webSettings2.setDomStorageEnabled(true);
        webDisqus.requestFocusFromTouch();
        webDisqus.setWebViewClient(new MyWebViewClient(url));
        webDisqus.setWebChromeClient(new WebChromeClient());
        webDisqus.loadUrl(url);



        // Creating volley request obj
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET,Config.main_url+"/posts/"+id,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("SN", response.toString());

                        try {


                            title.setText(response.getString("title"));
                            date.setText(response.getString("date"));

                            content.setText(Html.fromHtml(response.getString("content")));

                            if(response.has("terms")) {
                                JSONObject terms = response.getJSONObject("terms");
                                if(terms.has("post_tag")) {
                                    JSONArray post_tag = terms.getJSONArray("post_tag");
                                    for (int i = 0; i < post_tag.length(); i++) {
                                        JSONObject cat = post_tag.getJSONObject(i);
                                        article a = new article();
                                        a.setContent(cat.getString("slug"));
                                        a.setTitle("#" + cat.getString("name"));
                                        articleList.add(a);
                                    }
                                    adapter.notifyDataSetChanged();
                                    DynamicHeightListView.setListViewHeightBasedOnChildren(listView);
                                }else{
                                    tagText.setVisibility(View.GONE);
                                }
                            }

                            JSONObject featured_image = response.getJSONObject("featured_image");


                            title.setText(response.getString("title"));

                            String dateSource = response.getString("date");
                            String replacedDate = dateSource.replace("T", " ");
                            date.setText(replacedDate + " WIB");
                            content.setText(Html.fromHtml(response.getString("content")));

                            // SHARE LINK
                            share_link = response.getString("link");
                            share_title =  response.getString("title");

                            final KenBurnsView image_large = (KenBurnsView) findViewById(R.id.featured_image);



                            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(DetailArticle.this).build();
                            ImageLoader.getInstance().init(config);
                            ImageLoader imageLoader = ImageLoader.getInstance();

                            DisplayImageOptions options = new DisplayImageOptions.Builder()
                                    .resetViewBeforeLoading(false)  // default
                                    .delayBeforeLoading(0)
                                    .cacheInMemory(false) // default
                                    .cacheOnDisk(false) // default
                                    .considerExifParams(false) // default
                                    .imageScaleType(ImageScaleType.EXACTLY) // default
                                    .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                                    .build();

                            ImageSize targetSize = new ImageSize(600, 500); // result Bitmap will be fit to this size
                            imageLoader.loadImage(featured_image.getString("source"), targetSize, options, new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                                    image_large.setImageBitmap(loadedImage);
//                                    image_large.setOnClickListener(new View.OnClickListener() {
//                                        @Override
////                                        public void onClick(View view) {
////                                            Intent i = new Intent(getApplicationContext(), DetailArticleFoto.class);
////                                            i.putExtra("detail", detail);
////                                            i.putExtra("image_url", image_url);
////                                            startActivity(i);
////
////                                        }
//                                    });
                                }
                            });

                            bar.setVisibility(View.GONE);
                            content_artikel.setVisibility(View.VISIBLE);
                            content_artikel.fullScroll(ScrollView.FOCUS_UP);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("SN", e.toString());
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

