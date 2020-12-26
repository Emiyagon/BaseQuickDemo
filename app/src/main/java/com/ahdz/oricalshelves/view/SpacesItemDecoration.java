package com.ahdz.oricalshelves.view;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.ahdz.oricalshelves.MyApplication;
import com.ahdz.oricalshelves.util.screen.RudenessScreenHelper;


public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = (int) RudenessScreenHelper.pt2px(MyApplication.getInstance(),space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        //  outRect.left = space;
        // outRect.right = space;
        outRect.bottom = space;
        // Add top margin only for the first item to avoid double space between items
        /*if (parent.getChildPosition(view) == 0)
            outRect.top = space;*/
    }
}
