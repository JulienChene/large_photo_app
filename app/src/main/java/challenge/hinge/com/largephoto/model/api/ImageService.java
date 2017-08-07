package challenge.hinge.com.largephoto.model.api;

import challenge.hinge.com.largephoto.model.ImageResponse;
import io.reactivex.Observable;

import javax.inject.Inject;

public class ImageService
{
    @Inject
    ImageApi imageApi;

    @Inject
    public ImageService()
    {
    }

    public Observable<ImageResponse> getImages()
    {
        return this.imageApi.getImages();
    }
}
