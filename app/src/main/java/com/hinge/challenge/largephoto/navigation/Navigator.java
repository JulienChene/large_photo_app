package com.hinge.challenge.largephoto.navigation;

import android.content.Context;
import android.content.Intent;
import com.hinge.challenge.largephoto.ui.activity.GalleryActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator
{
    @Inject
    public Navigator()
    {
    }

    public void navigateToGallery(Context context, int position)
    {
        if (context != null) {
            Intent intent = GalleryActivity.getCallingIntent(context, position);
            context.startActivity(intent);
        }
    }
}
