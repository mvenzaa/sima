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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;
import com.sibermediaabadi.wartaplus.Config;
import com.sibermediaabadi.wartaplus.R;
import com.sibermediaabadi.wartaplus.activity.TagListDetail;
import com.sibermediaabadi.wartaplus.adapter.TagListAdapter;
import com.sibermediaabadi.wartaplus.app.AppController;
import com.sibermediaabadi.wartaplus.model.article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TagWeekFragment extends Fragment {

    private View rootView;

    // Log tag

    // Movies json url
    //private static final String url = "http://stopnarkoba.id/service/artikels?page=";
    private Integer url_page_default;
    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private TagListAdapter adapter;

    ProgressBar bar;

    public TagWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tag, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new TagListAdapter(getActivity(), articleList);
        listView.setAdapter(adapter);

        bar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        url_page_default = 0;
        listView.setPadding(0, -70, 0, 0);
        list("default", url_page_default);

        ((PullAndLoadListView) listView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                    public void onRefresh() {
                        url_page_default = 0;
                        list("refresh", url_page_default);
                        listView.setPadding(0, 50, 0, 0);
                    }
                });
        ((PullAndLoadListView) listView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        url_page_default += 1;
                        list("loadmore", url_page_default);
                        listView.setPadding(0, 50, 0, 0);
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                TextView s = (TextView) v.findViewById(R.id.content);
                String tagSlug = s.getText().toString();

                TextView n = (TextView) v.findViewById(R.id.title);
                String tagName = n.getText().toString();

                Intent i = new Intent(getActivity().getApplicationContext(), TagListDetail.class);
                i.putExtra("slug", tagSlug);
                i.putExtra("name", tagName);
                startActivity(i);
            }
        });

        return rootView;
    }

    public void list(final String type, final Integer page) {
        // Creating volley request obj

        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(Config.main_url + "/tags/week?page=" + String.valueOf(page),


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (type == "refresh") {
                            articleList.clear();
                        }

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                article a = new article();
                                a.setContent(obj.getString("slug"));
                                a.setTitle("#" + obj.getString("name"));
                                articleList.add(a);

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
        }

        );

        AppController.getInstance().addToRequestQueue(movieReq, "SN");

    }
}