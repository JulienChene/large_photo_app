package com.hinge.challenge.largephoto.ui;

import com.hinge.challenge.largephoto.model.ImageResult;

public interface GalleryView
{
    void setImage(ImageResult image, int position, int listSize);

    void goBack();
}
