package com.hinge.challenge.largephoto.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Layout manager to position views accordingly in the RecyclerView
 */
public class ImagesLayoutManager extends GridLayoutManager
{
    public ImagesLayoutManager(Context context)
    {
        super(context, 3, VERTICAL, false);
    }
}
