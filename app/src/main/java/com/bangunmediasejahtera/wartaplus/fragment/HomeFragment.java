package com.bangunmediasejahtera.wartaplus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bangunmediasejahtera.wartaplus.activity.DetailArticleActivity;
import com.bangunmediasejahtera.wartaplus.adapter.ListAdapter;
import com.bangunmediasejahtera.wartaplus.app.AppController;
import com.bangunmediasejahtera.wartaplus.model.article;
import com.bangunmediasejahtera.wartaplus.util.ConnectionDetector;

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
    private Integer url_page_default;
    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private ListAdapter adapter;

    ProgressBar bar;

    private WebView WebView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
                        listView.setPadding(0, 130, 0, 0);
                        url_page_default = 1;
                        list("refresh", url_page_default);
                        Log.d("SN", "refresh");
                    }
                });

        ((PullAndLoadListView) listView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        listView.setPadding(0, 130, 0, 0);
                        url_page_default += 1;
                        list("loadmore", url_page_default);
                        Log.d("SN", "loadmore");
                    }
                });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                TextView c = (TextView) v.findViewById(R.id.ID);
                String articleID = c.getText().toString();
                Intent i = new Intent(getActivity().getApplicationContext(), DetailArticleActivity.class);
                i.putExtra("id", articleID);
                startActivity(i);
            }
        });

        return rootView;
    }


    public void list(final String type,final int page) {


        noInternet();

        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(Config.main_url + "/posts?filter[posts_per_page]=10&page=" + String.valueOf(page),


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SN", response.toString());

                        if (type == "refresh") {
                            articleList.clear();
                        }

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                JSONObject featured_image = obj.getJSONObject("featured_image");

                                /*JSONObject attachment_meta = featured_image.getJSONObject("attachment_meta");
                                JSONObject sizes = attachment_meta.getJSONObject("sizes");
                                JSONObject large = sizes.getJSONObject("tie-large");*/



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
                            Log.d("SN", e.toString());
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

    }

}
