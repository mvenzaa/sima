package com.sibermediaabadi.wartaplus.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    private String myUrl;
    public MyWebViewClient(String _myURL) {
        // TODO Auto-generated constructor stub
        myUrl=_myURL;

    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.i("page started", url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Log.i("disqus error", "failed: " + failingUrl + ", error code: " + errorCode + " [" + description + "]");
    }

    public void onPageFinished(WebView view, String url) {
        if(url.indexOf("logout")>-1 || url.indexOf("disqus.com/next/login-success")>-1 ){
            view.loadUrl(myUrl);

        }
        if(url.indexOf("disqus.com/_ax/twitter/complete")>-1||url.indexOf("disqus.com/_ax/facebook/complete")>-1||url.indexOf("disqus.com/_ax/google/complete")>-1){
            view.loadUrl(myUrl+"/login.php");

        }
        if(url.indexOf(myUrl+"/login.php")>-1){
            view.loadUrl(myUrl);
        }
    }
}