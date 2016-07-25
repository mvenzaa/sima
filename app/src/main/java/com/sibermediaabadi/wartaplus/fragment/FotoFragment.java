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
import com.sibermediaabadi.wartaplus.activity.DetailFoto;
import com.sibermediaabadi.wartaplus.adapter.FotoListAdapter;
import com.sibermediaabadi.wartaplus.app.AppController;
import com.sibermediaabadi.wartaplus.model.foto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Probook 4341s on 7/23/2016.
 */
public class FotoFragment extends Fragment {

    private View rootView;

    // Log tag
    private static final String TAG = FotoFragment.class.getSimpleName();

    // Movies json url
    //private static final String url = "http://stopnarkoba.id/service/artikels?page=";
    private Integer url_page_default = 0;
    private List<foto> fotoList = new ArrayList<foto>();
    private ListView listView;
    private FotoListAdapter adapter;

    ProgressBar bar;

    public FotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_foto, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new FotoListAdapter(getActivity(), fotoList);
        listView.setAdapter(adapter);

        bar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        listView.setPadding(0, 140, 0, 0);
        list("default",url_page_default);


        ((PullAndLoadListView) listView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                    public void onRefresh() {
                        listView.setPadding(0, 140, 0, 0);
                        url_page_default = 0;
                        list("refresh",url_page_default);
                    }
                });
        ((PullAndLoadListView) listView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        listView.setPadding(0, 140, 0, 0);
                        url_page_default = url_page_default + 1;
                        list("loadmore",url_page_default);
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                TextView c = (TextView) v.findViewById(R.id.ID);
                String articleID = c.getText().toString();
                Intent i = new Intent(getActivity().getApplicationContext(), DetailFoto.class);
                i.putExtra("id", articleID);
                startActivity(i);
            }
        });

        return rootView;

    }

    public void list(final String type,final int page) {
        // Creating volley request obj

        JsonArrayRequest movieReq = new JsonArrayRequest(Config.main_url+ "/wonderplugins?&page=" + String.valueOf(page),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {


                                JSONObject obj = response.getJSONObject(i);
                                JSONObject data = obj.getJSONObject("data");
                                JSONArray slides = data.getJSONArray("slides");
                                foto a = new foto();
                                a.setCreated_at(obj.getString("time"));
                                a.setID(obj.getInt("id"));
                                // load data array di sini gambar
                                // ganti x < 1 dengan x < 1 slides.length()
                                // pasang di detail foto
                                for (int x = 0; x < 1; x++) {
                                    JSONObject sld = slides.getJSONObject(x);
                                    a.setImage_small_Url(sld.getString("image"));
                                    a.setTitle(sld.getString("title"));
                                }
                                fotoList.add(a);


                                adapter.notifyDataSetChanged();
                                bar.setVisibility(View.GONE);



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
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(movieReq, "SN");


    }

}
