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
import com.sibermediaabadi.wartaplus.activity.DetailArticle;
import com.sibermediaabadi.wartaplus.adapter.ListAdapter;
import com.sibermediaabadi.wartaplus.model.article;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private View rootView;

    // Log tag
    private static final String TAG = HomeFragment.class.getSimpleName();

    // Movies json url
    //private static final String url = "http://stopnarkoba.id/service/artikels?page=";
    private Integer url_page_default = 1;
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
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_home, container, false);

    listView = (ListView) rootView.findViewById(R.id.list);
    adapter = new ListAdapter(getActivity(), articleList);
    listView.setAdapter(adapter);

    bar = (ProgressBar) rootView.findViewById(R.id.loading_progress);
    bar.setVisibility(View.VISIBLE);
    list("default");
        listView.setPadding(0, 140, 0, 0);


    ((PullAndLoadListView) listView)
            .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
                public void onRefresh() {
                    listView.setPadding(0, 140, 0, 0);
                    url_page_default = 1;
                    list("refresh");
                }
            });
    ((PullAndLoadListView) listView)
            .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {
                public void onLoadMore() {
                    listView.setPadding(0, 140, 0, 0);
                    url_page_default = url_page_default + 1;
                    list("loadmore");
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

        return rootView;
    }

    public void list(final String type) {
        // Creating volley request obj



        if (type == "refresh") {
            articleList.clear();
        }


        for (int i = 0; i < 20; i++) {

            article a = new article();
            a.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/571de00895761-rendering-honda-cbr250rr_663_382-660x330.jpg");
            a.setID(1);
            a.setTitle("Honda CBR250RR Meluncur di Indonesia 25 Juli");
            a.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(a);

            article b = new article();
            b.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/Ketua-Komisi-Pemilihan-Umum-KPU-Prov.Papua-Adam-Arisoi.-Foto-Icahd.-660x330.jpg");
            b.setID(2);
            b.setTitle("KPU Papua Akan Sidak ke Kabupaten Sarmi");
            b.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(b);

            article c = new article();
            c.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/157686_mengintip-keindahan-raja-ampat_663_382-660x330.jpg");
            c.setID(3);
            c.setTitle("3 Negara Ini Punya Keindahan Bawah Laut yang Memikat");
            c.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(c);

            article d = new article();
            d.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/planet-kepler_20150705_141444-660x330.jpg");
            d.setID(4);
            d.setTitle("NASA Temukan 2 Planet Kembaran Bumi");
            d.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(d);

            article e = new article();
            e.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/0136399lorenzo4780x390-660x330.jpg");
            e.setID(5);
            e.setTitle("Lorenzo Optimis Bisa Kalahkan Marquez");
            e.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(e);

            article f = new article();
            f.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/1739018zte-zmax780x390-660x330.jpg");
            f.setID(6);
            f.setTitle("ZTE Merilis Smartphone Berbasis Android dengan Harga Rp 1,3 Jutaan");
            f.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(f);

            article g = new article();
            g.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/download-e1468699137977.jpeg");
            g.setID(7);
            g.setTitle("Dukung Jokowi Nyapres Di 2019, Golkar Lakukan Yang Tidak Lazim");
            g.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(g);

            article h = new article();
            h.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/mantan-gubernur-gatot-dikirim-ke-lapas-tanjung-gusta-660x330.jpg");
            h.setID(8);
            h.setTitle("Mantan Gubernur Gatot Dikirim ke Lapas Tanjung Gusta");
            h.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(h);

            article j = new article();
            j.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/tirta-drive-terpesona-dan-kagum-dengan-kelembutan-terry-putri.jpg");
            j.setID(9);
            j.setTitle("Tirta Drive Terpesona dan Kagum Dengan Kelembutan Terry Putri");
            j.setCreated_at("15 Juli 2016 15:00 WIB");
            articleList.add(j);

            article k = new article();
            k.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/07/LB-330x330.jpg");
            k.setID(10);
            k.setTitle("LP3BH Desak Pemerintah dan DPRP ‘Advokasi’ Yogyakarta");
            k.setCreated_at("15 Juli 2016 15:00 WIB");
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
