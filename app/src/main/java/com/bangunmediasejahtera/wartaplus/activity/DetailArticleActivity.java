package com.bangunmediasejahtera.wartaplus.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.bangunmediasejahtera.wartaplus.Config;
import com.bangunmediasejahtera.wartaplus.R;
import com.bangunmediasejahtera.wartaplus.adapter.CommentAdapter;
import com.bangunmediasejahtera.wartaplus.adapter.RelatedAdapter;
import com.bangunmediasejahtera.wartaplus.adapter.TagListAdapter;
import com.bangunmediasejahtera.wartaplus.app.AppController;
import com.bangunmediasejahtera.wartaplus.model.article;
import com.bangunmediasejahtera.wartaplus.model.comment;
import com.bangunmediasejahtera.wartaplus.util.DynamicHeightListView;
import com.bangunmediasejahtera.wartaplus.util.getEmail;

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
public class DetailArticleActivity extends AppCompatActivity {

    private String id;

    private TextView ID, title, content, date, author, tagText, relatedText;
    private String share_link, share_title;

    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private TagListAdapter adapter;

    private List<article> relatedList = new ArrayList<article>();
    private ListView listViewRelated;
    private RelatedAdapter relatedAdapter;


    private List<comment> commentList = new ArrayList<comment>();
    private ListView listViewComment;
    private CommentAdapter commentAdapter;

    private EditText commentName, commentEmail, commentText;
    private Button commentButton;
    String string_name, string_email, string_content;
    String error_text;
    Integer is_error = 0;

    private String cat = "empty_category";

    ProgressBar bar;
    NestedScrollView content_artikel;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detail_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        content_artikel = (NestedScrollView) findViewById(R.id.content_artikel);
        bar = (ProgressBar) findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        content_artikel.setVisibility(View.GONE);
        content_artikel.smoothScrollTo(0, 0);

        listView = (ListView) findViewById(R.id.list);
        adapter = new TagListAdapter(this, articleList);
        listView.setAdapter(adapter);
        listView.setFocusableInTouchMode(false);
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


        listViewRelated = (ListView) findViewById(R.id.relateds);
        relatedAdapter = new RelatedAdapter(this, relatedList);
        listViewRelated.setAdapter(relatedAdapter);
        listViewRelated.setFocusableInTouchMode(false);
        listViewRelated.setFocusable(false);

        listViewRelated.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                TextView i = (TextView) v.findViewById(R.id.ID);
                String idrelated = i.getText().toString();

                Intent x = new Intent(getApplicationContext(), DetailArticleActivity.class);
                x.putExtra("id", idrelated);
                startActivity(x);
            }
        });


        listViewComment = (ListView) findViewById(R.id.comments);
        commentAdapter = new CommentAdapter(this, commentList);
        listViewComment.setAdapter(commentAdapter);
        listViewComment.setFocusable(false);
        listViewComment.setFocusableInTouchMode(false);

        commentName = (EditText) findViewById(R.id.comment_name);
        commentEmail = (EditText) findViewById(R.id.comment_email);
        commentText = (EditText) findViewById(R.id.comment_content);
        commentButton = (Button) findViewById(R.id.submitComment);
        String email = getEmail.primary(this);
        String[] parts = email.split("@");
        String name = parts[0];
        commentEmail.setText(email);
        commentName.setText(name);


        ID = (TextView) findViewById(R.id.ID);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        date = (TextView) findViewById(R.id.date);
        author = (TextView) findViewById(R.id.author);
        tagText = (TextView) findViewById(R.id.tagText);
        relatedText = (TextView) findViewById(R.id.relatedText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }
        get_post();
        get_comments();
        insert_comment();

        WebView top = (WebView) findViewById(R.id.ads_top);
        Ads.init(top, 0); // 0 top position
        WebView bottom = (WebView) findViewById(R.id.ads_bottom);
        Ads.init(bottom, 1); // 1 bottom position


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
                            date.setText(replacedDate + " WIB");
                            title.setText(Html.fromHtml(response.getString("title")));
                            content.setText(Html.fromHtml(response.getString("content")));
                            //collapsingToolbarLayout.setTitle(Html.fromHtml(response.getString("title")));
                            JSONObject authorObj = response.getJSONObject("author");
                            author.setText("Oleh : "+ authorObj.getString("first_name") + " " + authorObj.getString("last_name"));


                            JSONObject featured_image = response.getJSONObject("featured_image");
                            final String image_url = featured_image.getString("source");
                            final String image_detail = featured_image.getString("title");
                            final KenBurnsView image_large = (KenBurnsView ) findViewById(R.id.featured_image);


                            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(DetailArticleActivity.this).build();
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
                                    image_large.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(getApplicationContext(), DetailArticleFotoActivity.class);
                                            i.putExtra("detail", image_detail);
                                            i.putExtra("image_url", image_url);
                                            startActivity(i);

                                        }
                                    });
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

                                if (terms.has("category")) {
                                    JSONArray category = terms.getJSONArray("category");
                                    for (int i = 0; i < 1; i++) {
                                        JSONObject ctg = category.getJSONObject(i);
                                        String related_by_cat = ctg.getString("ID");
                                        get_related(related_by_cat);
                                    }
                                } else {
                                    relatedText.setVisibility(View.GONE);
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

    public void get_related(String related_by_cat) {
        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(Config.main_url + "/posts?filter[posts_per_page]=5&filter[cat]=" + related_by_cat,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String sameID =  String.valueOf(obj.getInt("ID"));
                                if(!sameID.equals(id)) {
                                    JSONObject featured_image = obj.getJSONObject("featured_image");
                                    JSONObject attachment_meta = featured_image.getJSONObject("attachment_meta");
                                    JSONObject sizes = attachment_meta.getJSONObject("sizes");
                                    JSONObject medium = sizes.getJSONObject("medium");
                                    article related = new article();
                                    related.setFeatured_image_Url(medium.getString("url"));
                                    related.setID(obj.getInt("ID"));
                                    related.setTitle(obj.getString("title"));
                                    String date = obj.getString("date");
                                    String replacedDate = date.replace("T", " ");
                                    related.setDate(replacedDate + " WIB");
                                    relatedList.add(related);
                                }
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        relatedAdapter.notifyDataSetChanged();
                        DynamicHeightListView.setListViewHeightBasedOnChildren(listViewRelated);


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(movieReq, "SN");
    }


    public void get_comments() {

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
                                } else {
                                    a.setName("Admin");
                                    a.setAvatar("http://0.gravatar.com/avatar/64e1b8d34f425d19e1ee2ea7236d3028?s=96");
                                }
                                a.setContent(com.getString("content"));

                                String dateSource = com.getString("date");
                                String replacedDate = dateSource.replace("T", " ");
                                a.setDate(replacedDate + " WIB");

                                a.setID(com.getInt("ID"));
                                commentList.add(a);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("SN", e.toString());
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

    public void insert_comment() {
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DetailArticleActivity.this, "Mengirim Komentar .. ", Toast.LENGTH_LONG).show();

                commentName.setEnabled(false);
                commentEmail.setEnabled(false);
                commentText.setEnabled(false);
                commentButton.setEnabled(false);

                string_name = commentName.getText().toString();
                string_email = commentEmail.getText().toString();
                string_content = commentText.getText().toString();

                error_text = "";
                is_error = 0;
                if (string_name.matches("")) {
                    error_text += "Nama Tidak Boleh Kosong \n";
                    is_error += 1;
                }
                if (string_email.matches("")) {
                    error_text += "Email Tidak Boleh Kosong \n";
                    is_error += 1;
                }
                if (string_content.matches("")) {
                    error_text += "Pesan Tidak Boleh Kosong";
                    is_error += 1;
                }

                if (is_error > 0) {
                    Toast.makeText(DetailArticleActivity.this, error_text, Toast.LENGTH_SHORT).show();
                    commentName.setEnabled(true);
                    commentEmail.setEnabled(true);
                    commentText.setEnabled(true);
                    commentButton.setEnabled(true);
                } else {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", string_name);
                    params.put("email", string_email);
                    params.put("content", string_content);
                    JsonObjectRequest posting = new JsonObjectRequest(Request.Method.POST,
                            Config.main_url + "/comments/insert/" + id, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("SN", response.toString());
                                    commentName.setEnabled(true);
                                    commentEmail.setEnabled(true);
                                    commentText.setEnabled(true);
                                    commentButton.setEnabled(true);
                                    commentText.setText("");
                                    Toast.makeText(DetailArticleActivity.this, "Berhasil .. ", Toast.LENGTH_SHORT).show();
                                    commentList.clear();
                                    commentAdapter.notifyDataSetChanged();
                                    get_comments();
//                                    Intent refresh = new Intent(getApplicationContext(), DetailArticleActivity.class);
//                                    refresh.putExtra("id", id);
//                                    startActivity(refresh);
//                                    finish();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            commentName.setEnabled(true);
                            commentEmail.setEnabled(true);
                            commentText.setEnabled(true);
                            commentButton.setEnabled(true);
                            Toast.makeText(DetailArticleActivity.this, "Terjadi Kesalahan " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=UTF-8");
                            return headers;
                        }
                    };
                    posting.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addToRequestQueue(posting);

                }

            }
        });
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

