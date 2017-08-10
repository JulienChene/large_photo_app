package com.hinge.challenge.largephoto.util;

import com.hinge.challenge.largephoto.model.ImageResponse;
import com.hinge.challenge.largephoto.model.ImageResult;

import java.util.ArrayList;
import java.util.List;

public class ModelUtil
{
    public static List<ImageResult> getImageList(int count)
    {
        List<ImageResult> imageList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            imageList.add(new ImageResult());
        }

        return imageList;
    }
}
