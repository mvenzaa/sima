package com.sibermediaabadi.wartaplus.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class DynamicHeightListView {

    public static void setListViewHeightBasedOnChildren(ListView listView) {

        if (listView.getAdapter() == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            View listItem = listView.getAdapter().getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1)) + 80;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
