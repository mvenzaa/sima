package com.sibermediaabadi.wartaplus.fragment;

/**
 * Created by Probook 4341s on 7/26/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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


public class PopulerFragment extends Fragment {

    private View rootView;


    // Movies json url
    private Integer url_page_default = 0;
    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private ListAdapter adapter;
    private Spinner spinner;
    private String link_populer;

    ProgressBar bar;

    public PopulerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_populer, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new ListAdapter(getActivity(), articleList);
        listView.setAdapter(adapter);

        bar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);

        link_populer = Config.main_url + "/popular/week";

        url_page_default = 0;
        list("default", url_page_default);
        listView.setPadding(0, 10, 0, 0);

        ((PullAndLoadListView) listView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                    public void onRefresh() {
                        listView.setPadding(0, 10, 0, 0);
                        url_page_default = 0;
                        list("refresh", url_page_default);
                    }
                });

        ((PullAndLoadListView) listView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        listView.setPadding(0, 10, 0, 0);
                        url_page_default += 1;
                        list("loadmore", url_page_default);
                    }
                });



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

        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Hari ini");
        list.add("Minggu ini");
        list.add("Bulan ini");
        list.add("Tahun ini");
        list.add("Sepanjang Masa");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listView.setPadding(0, 10, 0, 0);
                url_page_default = 0;
                listView.setVisibility(View.GONE);
                bar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
                bar.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        link_populer = Config.main_url + "/popular/day";
                        break;
                    case 1:
                        link_populer = Config.main_url + "/popular/week";
                        break;
                    case 2:
                        link_populer = Config.main_url + "/popular/month";
                        break;
                    case 3:
                        link_populer = Config.main_url + "/popular/year";
                        break;
                    case 4:
                        link_populer = Config.main_url + "/popular/all";
                        break;
                }
                list("refresh", url_page_default);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });




        return rootView;
    }


    public void list(final String type,final int page) {

        noInternet();

        JsonArrayRequest movieReq = new JsonArrayRequest(link_populer+"?page=" + String.valueOf(page),

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SN",response.toString());

                        if (type == "refresh") {
                            articleList.clear();
                        }

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);

                                article b = new article();
                                b.setFeatured_image_Url(obj.getString("image"));
                                b.setID(obj.getInt("ID"));
                                b.setTitle(obj.getString("post_title"));
                                b.setDate(obj.getString("post_modified") + "WIB");
                                articleList.add(b);

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                        bar.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

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
