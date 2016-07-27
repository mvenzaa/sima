package com.sibermediaabadi.wartaplus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import android.widget.Toast;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
=======

>>>>>>> upstream/master
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
<<<<<<< HEAD
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
=======
>>>>>>> upstream/master
=======

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
>>>>>>> upstream/master
=======

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
>>>>>>> upstream/master
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;
import com.sibermediaabadi.wartaplus.Config;
import com.sibermediaabadi.wartaplus.R;
import com.sibermediaabadi.wartaplus.activity.DetailArticle;
import com.sibermediaabadi.wartaplus.adapter.ListAdapter;
import com.sibermediaabadi.wartaplus.app.AppController;
import com.sibermediaabadi.wartaplus.model.article;
import com.sibermediaabadi.wartaplus.util.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private View rootView;

    // Log tag
    private static final String TAG = HomeFragment.class.getSimpleName();

    // Movies json url
    private Integer url_page_default = 0;
    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private ListAdapter adapter;

    ProgressBar bar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> upstream/master
=======

>>>>>>> upstream/master
=======

>>>>>>> upstream/master

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new ListAdapter(getActivity(), articleList);
        listView.setAdapter(adapter);

        bar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        url_page_default = 0;
        list("default", url_page_default);


        ((PullAndLoadListView) listView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                    public void onRefresh() {
                        listView.setPadding(0, 140, 0, 0);
                        url_page_default = 0;
                        list("refresh", url_page_default);
                    }
                });
        ((PullAndLoadListView) listView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        listView.setPadding(0, 140, 0, 0);
                        url_page_default +=  1;
                        list("loadmore", url_page_default);

                    }
                });
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> upstream/master
=======
>>>>>>> upstream/master

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new ListAdapter(getActivity(), articleList);
        listView.setAdapter(adapter);

        bar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        url_page_default = 1;
        list("default", url_page_default);
        listView.setPadding(0, 70, 0, 0);


        ((PullAndLoadListView) listView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                    public void onRefresh() {
                        listView.setPadding(0, 180, 0, 0);
                        url_page_default = 1;
                        list("refresh", url_page_default);
                    }
                });
        ((PullAndLoadListView) listView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        listView.setPadding(0, 180, 0, 0);
                        url_page_default = url_page_default + 1;
                        list("loadmore", url_page_default);
                    }
                });

<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> upstream/master
=======
>>>>>>> upstream/master
=======
>>>>>>> upstream/master

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                TextView c = (TextView) v.findViewById(R.id.ID);
                String articleID = c.getText().toString();
                Intent i = new Intent(getActivity().getApplicationContext(), DetailArticle.class);
                i.putExtra("id", articleID);
                startActivity(i);
            }
        });

        return rootView;
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public void list(final String type,final int page) {
=======
    public void list(final String type, final int page) {

>>>>>>> upstream/master
        // Creating volley request obj
        noInternet();


<<<<<<< HEAD
        JsonArrayRequest movieReq = new JsonArrayRequest(Config.main_url+ "/posts?filter[posts_per_page]=10&page=" + String.valueOf(page),
=======

        JsonArrayRequest movieReq = new JsonArrayRequest(Config.main_url + "/posts?filter[posts_per_page]=10&page=" + String.valueOf(page),
>>>>>>> upstream/master
=======
=======
>>>>>>> upstream/master
    public void list(final String type, final int page) {

        // Creating volley request obj


        JsonArrayRequest movieReq = new JsonArrayRequest(Config.main_url + "/posts?filter[posts_per_page]=10&page=" + String.valueOf(page),
<<<<<<< HEAD
>>>>>>> upstream/master
=======
>>>>>>> upstream/master
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (type == "refresh") {
                            articleList.clear();
                        }
<<<<<<< HEAD
<<<<<<< HEAD


<<<<<<< HEAD
                                JSONObject obj = response.getJSONObject(i);
                                JSONObject featured_image = obj.getJSONObject("featured_image");
                                JSONObject attachment_meta = featured_image.getJSONObject("attachment_meta");
                                JSONObject sizes = attachment_meta.getJSONObject("sizes");
                                JSONObject medium = sizes.getJSONObject("medium");

=======
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                JSONObject featured_image = obj.getJSONObject("featured_image");
//                                JSONObject attachment_meta = featured_image.getJSONObject("attachment_meta");
//                                JSONObject sizes = attachment_meta.getJSONObject("sizes");
//                                JSONObject medium = sizes.getJSONObject("medium");
>>>>>>> upstream/master
=======


                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                JSONObject featured_image = obj.getJSONObject("featured_image");
//                                JSONObject attachment_meta = featured_image.getJSONObject("attachment_meta");
//                                JSONObject sizes = attachment_meta.getJSONObject("sizes");
//                                JSONObject medium = sizes.getJSONObject("medium");
>>>>>>> upstream/master
=======


                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                JSONObject featured_image = obj.getJSONObject("featured_image");
//                                JSONObject attachment_meta = featured_image.getJSONObject("attachment_meta");
//                                JSONObject sizes = attachment_meta.getJSONObject("sizes");
//                                JSONObject medium = sizes.getJSONObject("medium");
>>>>>>> upstream/master

                                article b = new article();
                                b.setFeatured_image_Url(featured_image.getString("source"));
                                b.setID(obj.getInt("ID"));
                                b.setTitle(obj.getString("title"));
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
                                b.setDate(obj.getString("date"));


                                articleList.add(b);

                            } catch (JSONException e) {
=======
                                String date = obj.getString("date");
                                String replacedDate = date.replace("T", " ");
                                b.setDate(replacedDate + " WIB");
>>>>>>> upstream/master
=======
                                String date = obj.getString("date");
                                String replacedDate = date.replace("T", " ");
                                b.setDate(replacedDate + " WIB");
>>>>>>> upstream/master
=======
                                String date = obj.getString("date");
                                String replacedDate = date.replace("T", " ");
                                b.setDate(replacedDate + " WIB");
>>>>>>> upstream/master

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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> upstream/master
    }

    public void noInternet()
    {
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toast.makeText(getActivity(), "Please connect to working Internet connection",
                    Toast.LENGTH_LONG).show();
            // stop executing code by return

            return;
        }
=======
>>>>>>> upstream/master
=======
>>>>>>> upstream/master
    }

}

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> upstream/master
=======

>>>>>>> upstream/master
=======

>>>>>>> upstream/master

