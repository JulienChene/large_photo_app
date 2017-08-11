package com.hinge.challenge.largephoto.ui.presenter;

import android.util.Log;
import com.hinge.challenge.largephoto.injection.PerActivity;
import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.interactor.ImageCacheSizeInteractorImpl;
import com.hinge.challenge.largephoto.model.interactor.ImageInteractorImpl;
import com.hinge.challenge.largephoto.model.interactor.RemoveImageInteractorImpl;
import com.hinge.challenge.largephoto.ui.GalleryView;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@PerActivity
public class GalleryPresenter implements Presenter
{
    private GalleryView galleryView;

    private final ImageInteractorImpl imageInteractor;
    private final RemoveImageInteractorImpl removeImageInteractor;
    private final ImageCacheSizeInteractorImpl cacheSizeInteractor;
    private ImageCarouselObserver carouselObserver;

    private int imagePosition = 0;
    private int cacheSize = 0;

    @Inject
    public GalleryPresenter(ImageInteractorImpl imageInteractor,
                            RemoveImageInteractorImpl removeImageInteractor,
                            ImageCacheSizeInteractorImpl cacheSizeInteractor)
    {
        this.imageInteractor = imageInteractor;
        this.removeImageInteractor = removeImageInteractor;
        this.cacheSizeInteractor = cacheSizeInteractor;
    }

    public void setView(GalleryView view)
    {
        this.galleryView = view;
    }

    @Override
    public void subscribe()
    {
        this.startCarousel(false);
    }

    @Override
    public void unsubscribe()
    {
        stopCarousel();
    }

    public void initialize(int position)
    {
        // Start listening to the cache size
        this.cacheSizeInteractor.execute(new CacheSizeObserver(), null);

        this.imagePosition = position;
        this.loadImage(position);
    }

    @Override
    public void destroy()
    {
        this.imageInteractor.dispose();
        this.removeImageInteractor.dispose();
        this.cacheSizeInteractor.dispose();
    }

    public void startCarousel(boolean immediate)
    {
        this.carouselObserver = new ImageCarouselObserver();
        int delay = immediate ? 0 : 2;

        Observable.interval(delay, 2, TimeUnit.SECONDS)
                .subscribe(this.carouselObserver);

    }

    public void stopCarousel()
    {
        if (carouselObserver != null && !carouselObserver.isDisposed()) {
            carouselObserver.dispose();
        }
    }

    private void setImagePositionByIncrement(int increment)
    {
        this.imagePosition = (imagePosition + increment);
        if (cacheSize > 0) {
            GalleryPresenter.this.imagePosition = imagePosition % cacheSize;
        }
    }

    public void deleteImage()
    {
        this.removeImageInteractor.execute(new RemoveImageObserver(), this.imagePosition);
    }

    public void onRetryClicked()
    {
        this.loadImage(this.imagePosition);
    }

    private void loadImage(int position)
    {
        this.imageInteractor.execute(new GetImageObserver(), position);
    }

    private final class GetImageObserver extends DisposableObserver<ImageResult>
    {
        @Override
        public void onNext(ImageResult imageResult)
        {
            GalleryPresenter.this.galleryView.setImage(imageResult, imagePosition + 1, cacheSize);
        }

        @Override
        public void onError(Throwable e)
        {
        }

        @Override
        public void onComplete()
        {
        }
    }

    /**
     * Observe on carousel for interval
     */
    private final class ImageCarouselObserver extends DisposableObserver<Long>
    {
        @Override
        public void onNext(Long imageResult)
        {
            if (cacheSize == 1) {
                GalleryPresenter.this.stopCarousel();
            }

            GalleryPresenter.this.setImagePositionByIncrement(1);
            GalleryPresenter.this.loadImage(imagePosition);
        }

        @Override
        public void onError(Throwable throwable)
        {
            GalleryPresenter.this.stopCarousel();
        }

        @Override
        public void onComplete()
        {
        }
    }

    private final class CacheSizeObserver extends DisposableObserver<Integer>
    {
        @Override
        public void onNext(Integer cacheSize)
        {
            Log.d("GalleryPresenter", "CacheSizeObserver: " + cacheSize);
            GalleryPresenter.this.cacheSize = cacheSize;
        }

        @Override
        public void onError(Throwable e)
        {
            GalleryPresenter.this.cacheSize = 0;
        }

        @Override
        public void onComplete()
        {
            // Leave the gallery, there isn't any image to show anymore
            GalleryPresenter.this.galleryView.goBack();
        }
    }

    private final class RemoveImageObserver extends DisposableObserver<Void>
    {
        @Override
        public void onNext(Void value)
        {
        }

        @Override
        public void onError(Throwable e)
        {
            // Couldn't delete image
        }

        @Override
        public void onComplete()
        {
            // Image deleted successfully
            stopCarousel();
//            GalleryPresenter.this.setImagePositionByIncrement(-1); //
            startCarousel(true);
        }
    }
}
