package com.hinge.challenge.largephoto.model.repository;

import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.api.ImageService;
import io.reactivex.Observable;

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
        super();
        this.imageService = imageService;
    }

    /**
     * Returns the first available Observable in the order of the Observables passed through concat.
     * Such that if cacheStream answers first, it will be returned. And serviceStream will only be
     * invoked once we know cacheStream is not returning anything.
     */
    public Observable<List<ImageResult>> getImagesObservable()
    {
        Observable<List<ImageResult>> cacheStream = this.getImageResultListObservable();
        Observable<List<ImageResult>> serviceStream = this.fetchImages();

        return Observable
                .concat(cacheStream, serviceStream)
                .filter(imageResponse -> imageResponse != null)
                .firstOrError()
                .toObservable();
    }

    /**
     * Fetches images from network and caches result
     */
    private Observable<List<ImageResult>> fetchImages()
    {
        return this.imageService
                .getImages()
                .doOnNext(this::cacheImages); // Cache images no matter what
    }
}
