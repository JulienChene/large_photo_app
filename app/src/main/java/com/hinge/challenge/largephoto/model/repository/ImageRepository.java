package com.hinge.challenge.largephoto.model.repository;

import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.api.ImageService;
import io.reactivex.Observable;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ImageRepository extends BaseRepository
{
    private final ImageService imageService;

    @Inject
    public ImageRepository(ImageService imageService)
    {
        this.imageService = imageService;
    }

    public Observable<List<ImageResult>> getImagesObservable()
    {
        Observable<List<ImageResult>> cacheStream = this.getImageResultListObservable();
        Observable<List<ImageResult>> serviceStream = this.fetchImages();

        // Return the first one available and
        return Observable
                .concat(cacheStream, serviceStream)
                .filter(imageResponse -> imageResponse != null)
                .firstOrError()
                .toObservable();
    }

    // Fetches images from network and caches result
    private Observable<List<ImageResult>> fetchImages()
    {
        return this.imageService
                .getImages()
                .doOnNext(this::cacheImages); // Cache images no matter what
    }
}
