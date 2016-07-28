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

import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;
import com.sibermediaabadi.wartaplus.R;
import com.sibermediaabadi.wartaplus.activity.TagListDetail;
import com.sibermediaabadi.wartaplus.adapter.TagListAdapter;
import com.sibermediaabadi.wartaplus.model.article;

import java.util.ArrayList;
import java.util.List;


public class TagPopularFragment extends Fragment{

    private View rootView;

    // Log tag
    private static final String TAG = TagPopularFragment.class.getSimpleName();

    // Movies json url
    //private static final String url = "http://stopnarkoba.id/service/artikels?page=";
    private Integer url_page_default = 1;
    private List<article> articleList = new ArrayList<article>();
    private ListView listView;
    private TagListAdapter adapter;

    ProgressBar bar;

    public TagPopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tag_popular, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new TagListAdapter(getActivity(), articleList);
        listView.setAdapter(adapter);

        bar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        bar.setVisibility(View.VISIBLE);
        list("default");

        ((PullAndLoadListView) listView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                    public void onRefresh() {
                        //listView.setPadding(0, 10, 0, 0);
                        url_page_default = 1;
                        list("refresh");
                    }
                });
        ((PullAndLoadListView) listView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                    public void onLoadMore() {
                        //listView.setPadding(0, 10, 0, 0);
                        url_page_default = url_page_default + 1;
                        list("loadmore");
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                TextView c = (TextView) v.findViewById(R.id.ID);
                String articleID = c.getText().toString();
                Intent i = new Intent(getActivity().getApplicationContext(), TagListDetail.class);
                i.putExtra("id", articleID);
                startActivity(i);
            }
        });

        return rootView;
    }

    public void list(final String type) {
        // Creating volley request obj



        if (type == "refresh") {
            articleList.clear();
        }


        for (int i = 0; i < 20; i++) {

            article a = new article();
            a.setID(1);
            a.setTitle("#Tag Popular di sini");
            articleList.add(a);

            article b = new article();
            b.setID(1);
            b.setTitle("#Tag Popular di sini");
            articleList.add(b);

            article c = new article();
            c.setID(1);
            c.setTitle("#Tag Popular di sini");
            articleList.add(c);

            article d = new article();
            d.setID(1);
            d.setTitle("#Tag Popular di sini");
            articleList.add(d);

            article e = new article();
            e.setID(1);
            e.setTitle("#Tag Popular di sini");
            articleList.add(e);

            article f = new article();
            f.setID(1);
            f.setTitle("#Tag Popular di sini");
            articleList.add(f);

            article g = new article();
            g.setID(1);
            g.setTitle("#Tag Popular di sini");
            articleList.add(g);

            article h = new article();
            h.setID(1);
            h.setTitle("#Tag Popular di sini");
            articleList.add(h);

            article j = new article();
            j.setID(1);
            j.setTitle("#Tag Popular di sini");
            articleList.add(j);

            article k = new article();
            k.setID(1);
            k.setTitle("#Tag Popular di sini");
            articleList.add(k);

        }

        adapter.notifyDataSetChanged();
        bar.setVisibility(View.GONE);


        if (type == "refresh") {
            ((PullAndLoadListView) listView).onRefreshComplete();
        } else {

            ((PullAndLoadListView) listView).onLoadMoreComplete();

        }


        // AppController.getInstance().addToRequestQueue(movieReq, "SN");

    }
}