package com.bangunmediasejahtera.wartaplus.activity;

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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bangunmediasejahtera.wartaplus.Config;
import com.bangunmediasejahtera.wartaplus.R;
import com.bangunmediasejahtera.wartaplus.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailPageActivity extends AppCompatActivity {

    private String id;

    private TextView ID, title, content, date, author, tagText, relatedText;
    private String share_link, share_title;



    ProgressBar bar;
    ScrollView content_artikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        content_artikel = (ScrollView) findViewById(R.id.content_artikel);
        bar = (ProgressBar) findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        content_artikel.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ID = (TextView) findViewById(R.id.ID);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        date = (TextView) findViewById(R.id.date);
        author = (TextView) findViewById(R.id.author);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }
        get_post();
    }



    public void get_post() {
        // Creating volley request obj
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET, Config.main_url + "/posts/" + id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // SHARE LINK
                            share_link = response.getString("link");
                            share_title = response.getString("title");

                            String dateSource = response.getString("date");
                            String replacedDate = dateSource.replace("T", " ");

                            title.setText(response.getString("title"));
                            date.setText(response.getString("date"));
                            content.setText(Html.fromHtml(response.getString("content")));
                            date.setText("Last Update : " + replacedDate + " WIB");
                            getSupportActionBar().setTitle(response.getString("title"));


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

