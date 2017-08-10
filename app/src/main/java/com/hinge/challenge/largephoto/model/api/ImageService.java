package com.hinge.challenge.largephoto.model.api;

import com.hinge.challenge.largephoto.model.ImageResult;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.List;

public class ImageService
{
    @Inject
    ImageApi imageApi;

    @Inject
    public ImageService()
    {
    }

    public Observable<List<ImageResult>> getImages()
    {
        return this.imageApi.getImages();
    }
}
