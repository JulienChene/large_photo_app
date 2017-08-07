package challenge.hinge.com.largephoto.model.repository;

import challenge.hinge.com.largephoto.model.ImageResponse;
import challenge.hinge.com.largephoto.model.api.ImageService;
import io.reactivex.Observable;
import io.reactivex.Single;

import javax.inject.Inject;

public class ImageRepository extends BaseRepository
{
    private final ImageService imageService;

    @Inject
    public ImageRepository(ImageService imageService)
    {
        this.imageService = imageService;
    }

    public Observable<ImageResponse> getImagesObservable()
    {
        Observable<ImageResponse> cacheStream = this.getImageResponseObservable();
        Observable<ImageResponse> serviceStream = this.fetchImages();

        // Return the first one available and
        return Observable
                .concat(cacheStream, serviceStream)
                .firstOrError()
                .toObservable();
    }

    // Fetches images from network and caches result
    private Observable<ImageResponse> fetchImages()
    {
        return this.imageService
                .getImages()
                .doOnNext(this::cacheImages);
    }
}
