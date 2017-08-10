package com.hinge.challenge.largephoto.ui;

import com.hinge.challenge.largephoto.model.ImageResult;

public interface GalleryView
{
    void showRetry();
    void hideRetry();
    void setImage(ImageResult image);
    void goBack();
}
