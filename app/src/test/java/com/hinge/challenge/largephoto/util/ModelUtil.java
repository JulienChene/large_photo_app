package com.hinge.challenge.largephoto.util;

import com.hinge.challenge.largephoto.model.ImageResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelUtil
{
    public static List<ImageResult> getImageList(int count)
    {
        List<ImageResult> imageList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            imageList.add(getImage());
        }

        return imageList;
    }

    public static ImageResult getImage()
    {
        ImageResult imageResult = new ImageResult();
        String url = "http://hinge.com/" + (int) (Math.random() * 50 + 1);
        imageResult.setUrl(url);

        return imageResult;
    }
}
