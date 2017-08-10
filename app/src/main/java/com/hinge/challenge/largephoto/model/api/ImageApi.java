package com.hinge.challenge.largephoto.model.api;

import com.hinge.challenge.largephoto.model.ImageResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;

public interface ImageApi
{
    @GET("client/services/homework.json")
    Observable<List<ImageResult>> getImages();
}
