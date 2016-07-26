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
import com.sibermediaabadi.wartaplus.activity.DetailFoto;
import com.sibermediaabadi.wartaplus.adapter.FotoListAdapter;
import com.sibermediaabadi.wartaplus.model.foto;

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
    private Integer url_page_default = 1;
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
        list("default");


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
                Intent i = new Intent(getActivity().getApplicationContext(), DetailFoto.class);
                i.putExtra("id", articleID);
                startActivity(i);
            }
        });

        return rootView;

    }

    public void list(final String type) {
        // Creating volley request obj


        if (type == "refresh") {
            fotoList.clear();
        }


        for (int i = 0; i < 20; i++) {

            foto a = new foto();
            a.setImage_small_Url("http://www.wartaplus.com/wp-content/uploads/2016/05/fais.jpg");
            a.setThumbnail1("http://www.wartaplus.com/wp-content/uploads/2016/05/fais-150x150.jpg");
            a.setThumbnail2("http://www.wartaplus.com/wp-content/uploads/2016/05/Komunitas-Action-Ensambel-Papua-4-150x150.jpg");
            a.setThumbnail3("http://www.wartaplus.com/wp-content/uploads/2016/05/Komunitas-Action-Ensambel-Papua-5-150x150.jpg");
            a.setThumbnail4("http://www.wartaplus.com/wp-content/uploads/2016/05/Komunitas-Action-Ensambel-Papua-6-150x150.jpg");
            a.setID(1);
            a.setTitle("Pementasan Musik Ensambel Papua Pertama Kali di Ujung Timur Indonesia");
            a.setCreated_at("15 Juli 2016 15:00 WIB");
            fotoList.add(a);
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
