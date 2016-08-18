package com.bangunmediasejahtera.wartaplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;
import com.bangunmediasejahtera.wartaplus.Config;
import com.bangunmediasejahtera.wartaplus.R;
import com.bangunmediasejahtera.wartaplus.adapter.ListAdapter;
import com.bangunmediasejahtera.wartaplus.app.AppController;
import com.bangunmediasejahtera.wartaplus.model.article;
import com.bangunmediasejahtera.wartaplus.util.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Probook 4341s on 7/27/2016.
 */
public class SearchActivity extends AppCompatActivity {

    // Log tag
    private static final String TAG = TagListDetail.class.getSimpleName();

    // Movies json url
    private Integer url_page_default = 0;
    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private ListAdapter adapter;
    private String key;
    ProgressBar bar;

    Toolbar toolbar = null;

    private TextView title_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");

        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(key);

        listView = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this, articleList);
        listView.setAdapter(adapter);

        bar = (ProgressBar) findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        url_page_default = 1;

        list("default", url_page_default);
        listView.setPadding(0, 50, 0, 0);
        ((PullAndLoadListView) listView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                    public void onRefresh() {
                        listView.setPadding(0, 80, 0, 0);
                        url_page_default = 1;
                        list("refresh", url_page_default);

                    }
                });
        ((PullAndLoadListView) listView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        url_page_default += 1;
                        list("loadmore", url_page_default);
                        listView.setPadding(0, 80, 0, 0);

                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                TextView c = (TextView) v.findViewById(R.id.ID);
                String articleID = c.getText().toString();
                Intent i = new Intent(getApplicationContext(), DetailArticleActivity.class);
                i.putExtra("id", articleID);
                startActivity(i);
            }
        });

        //WebView top = (WebView) findViewById(R.id.ads_top);
        //Ads.init(top, 0); // 0 top position
        WebView bottom = (WebView) findViewById(R.id.ads_bottom);
        Ads.init(bottom, 1); // 1 bottom position

    }

    public void list(final String type, final int page) {
        noInternet();

        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(Config.main_url + "/posts?filter[s]=" + key + "&filter[posts_per_page]=10&page=" + String.valueOf(page),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (type == "refresh") {
                            articleList.clear();
                        }

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                JSONObject featured_image = obj.getJSONObject("featured_image");


                                article b = new article();
                                b.setFeatured_image_Url(featured_image.getString("source"));
                                b.setID(obj.getInt("ID"));
                                b.setTitle(obj.getString("title"));


                                String date = obj.getString("date");
                                String replacedDate = date.replace("T", " ");
                                b.setDate(replacedDate + " WIB");


                                articleList.add(b);
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                        bar.setVisibility(View.GONE);


                        if (type == "refresh") {
                            ((PullAndLoadListView) listView).onRefreshComplete();
                        } else {

                            ((PullAndLoadListView) listView).onLoadMoreComplete();

                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(movieReq, "SN");


    }


    public void noInternet() {
        ConnectionDetector cd = new ConnectionDetector(this);
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toast.makeText(this, "Please connect to working Internet connection",
                    Toast.LENGTH_LONG).show();
            // stop executing code by return

            return;
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
