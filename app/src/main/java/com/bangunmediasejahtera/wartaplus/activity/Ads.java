package com.bangunmediasejahtera.wartaplus.activity;

import android.view.View;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bangunmediasejahtera.wartaplus.Config;
import com.bangunmediasejahtera.wartaplus.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Probook 4341s on 8/10/2016.
 */
public class Ads {


    public static void init(final WebView adsview,final int position) {

        // Creating volley request obj
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET, Config.ADS_URL+"count/position/"+String.valueOf(position),null,
                new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                // Parsing json
                try {

                    String count = response.getString("count");
                    if(Integer.valueOf(count) > 0) {
                        adsview.getSettings().setLoadsImagesAutomatically(true);
                        adsview.getSettings().setJavaScriptEnabled(true);
                        adsview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        adsview.loadUrl(Config.ADS_URL + "view/position/" + String.valueOf(position));
                        adsview.setVisibility(View.VISIBLE);
                    }
                    else if(Integer.valueOf(count) > 1) {
                        adsview.getSettings().setLoadsImagesAutomatically(true);
                        adsview.getSettings().setJavaScriptEnabled(true);
                        adsview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        adsview.loadUrl(Config.ADS_URL + "view/position/" + String.valueOf(position));
                        adsview.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);

    }

}