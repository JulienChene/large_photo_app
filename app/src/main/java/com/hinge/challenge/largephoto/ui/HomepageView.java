package com.hinge.challenge.largephoto.ui;

import com.hinge.challenge.largephoto.model.ImageResult;

import java.util.List;

public interface HomepageView
{
    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void viewImage(int position);

    void showImageList(List<ImageResult> imageList);
}
