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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import com.sibermediaabadi.wartaplus.adapter.CommentAdapter;
import com.sibermediaabadi.wartaplus.adapter.TagListAdapter;
import com.sibermediaabadi.wartaplus.app.AppController;
import com.sibermediaabadi.wartaplus.model.article;
import com.sibermediaabadi.wartaplus.model.comment;
import com.sibermediaabadi.wartaplus.util.DynamicHeightListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Probook 4341s on 5/26/2016.
 */
public class DetailArticle extends AppCompatActivity {

    private String id;

    private TextView ID, title, content, date, author, tagText;
    private String share_link, share_title;

    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private TagListAdapter adapter;

    private List<comment> commentList = new ArrayList<comment>();
    private ListView listViewComment;
    private CommentAdapter commentAdapter;

    private EditText commentName,commentEmail,commentText;
    private Button commentButton;
    String string_name,string_email,string_content;
    String error_text;
    Integer is_error = 0;


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


        listViewComment = (ListView) findViewById(R.id.comments);
        commentAdapter = new CommentAdapter(this, commentList);
        listViewComment.setAdapter(commentAdapter);
        listViewComment.setFocusable(false);

        commentName = (EditText)findViewById(R.id.comment_name);
        commentEmail = (EditText)findViewById(R.id.comment_email);
        commentText = (EditText)findViewById(R.id.comment_content);
        commentButton = (Button)findViewById(R.id.submitComment);


        ID = (TextView) findViewById(R.id.ID);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        date = (TextView) findViewById(R.id.date);
        author = (TextView) findViewById(R.id.author);
        tagText = (TextView) findViewById(R.id.tagText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }
        get_post();
        get_comments();
        insert_comment();

    }

    public void insert_comment()
    {
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DetailArticle.this, "Memproses .. ", Toast.LENGTH_SHORT).show();

                commentName.setEnabled(false);
                commentEmail.setEnabled(false);
                commentText.setEnabled(false);
                commentButton.setEnabled(false);

                string_name = commentName.getText().toString();
                string_email = commentEmail.getText().toString();
                string_content = commentText.getText().toString();

                error_text = "";
                is_error = 0;
                if(string_name.matches("")){
                    error_text += "Nama Tidak Boleh Kosong \n";
                    is_error   += 1 ;
                }
                if(string_email.matches("")){
                    error_text += "Email Tidak Boleh Kosong \n";
                    is_error   += 1 ;
                }
                if(string_content.matches("")){
                    error_text += "Pesan Tidak Boleh Kosong \n";
                    is_error   += 1 ;
                }

                if(is_error > 0){
                    Toast.makeText(DetailArticle.this, error_text, Toast.LENGTH_SHORT).show();
                    commentName.setEnabled(true);
                    commentEmail.setEnabled(true);
                    commentText.setEnabled(true);
                    commentButton.setEnabled(true);
                }else{
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", string_name);
                    params.put("email",string_email);
                    params.put("content",string_content);
                    JsonObjectRequest posting = new JsonObjectRequest(Request.Method.POST,
                            Config.main_url+"/comments/insert/"+id,new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("SN", response.toString());
                                    commentName.setEnabled(true);
                                    commentEmail.setEnabled(true);
                                    commentText.setEnabled(true);
                                    commentButton.setEnabled(true);
                                    commentName.setText("");
                                    commentEmail.setText("");
                                    commentText.setText("");
                                    Toast.makeText(DetailArticle.this, "Berhasil .. ", Toast.LENGTH_SHORT).show();
                                    commentList.clear();
                                    commentAdapter.notifyDataSetChanged();
                                    get_comments();
                                    Intent refresh = new Intent(getApplicationContext(), DetailArticle.class);
                                    refresh.putExtra("id", id);
                                    startActivity(refresh);
                                    finish();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            commentName.setEnabled(true);
                            commentEmail.setEnabled(true);
                            commentText.setEnabled(true);
                            commentButton.setEnabled(true);
                            Toast.makeText(DetailArticle.this, "Terjadi Kesalahan "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=UTF-8");
                            return headers;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(posting);

                }

            }
        });
    }


    public void get_post(){
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
                            date.setText(replacedDate + " WIB");


                            JSONObject featured_image = response.getJSONObject("featured_image");
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
                            if (response.has("terms")) {
                                JSONObject terms = response.getJSONObject("terms");
                                if (terms.has("post_tag")) {
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
                                } else {
                                    tagText.setVisibility(View.GONE);
                                }
                            }



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

    public void get_comments()
    {

        // Creating volley request obj
        JsonArrayRequest comment = new JsonArrayRequest(Config.main_url + "/posts/" + id + "/comments",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("SN", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject com = response.getJSONObject(i);
                                comment a = new comment();
                                JSONObject author_check = com.optJSONObject("author");
                                if (author_check instanceof JSONObject) {
                                    JSONObject author = com.getJSONObject("author");
                                    a.setName(author.getString("name"));
                                    a.setAvatar(author.getString("avatar"));
                                }else{
                                    a.setName("Admin");
                                    a.setAvatar("http://0.gravatar.com/avatar/64e1b8d34f425d19e1ee2ea7236d3028?s=96");
                                }
                                a.setContent(com.getString("content"));
                                a.setDate(com.getString("date"));
                                a.setID(com.getInt("ID"));
                                commentList.add(a);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("SN",e.toString());
                        }
                        commentAdapter.notifyDataSetChanged();
                        DynamicHeightListView.setListViewHeightBasedOnChildren(listViewComment);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.d("SN", error.toString());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(comment, "SN");

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

