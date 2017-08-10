package com.hinge.challenge.largephoto.model.repository;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import com.hinge.challenge.largephoto.model.ImageResult;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import java.util.ArrayList;
import java.util.List;

abstract class BaseRepository
{
    private LruCache<String, ImageResult> cache = this.createLruCache();
    private List<String> keyList = new ArrayList<>();
    private BehaviorSubject<Integer> cacheSizeObservable = BehaviorSubject.create();

    @NonNull
    private LruCache<String, ImageResult> createLruCache()
    {
        return new LruCache<>(50);
    }

    Observable<List<ImageResult>> getImageResultListObservable()
    {
        return Observable.fromCallable(this::getImageList);
    }

    public Observable<ImageResult> getImageResultObservable(int position)
    {
        return Observable.fromCallable(() -> this.getImage(position))
                .firstOrError()
                .toObservable();
    }

    public void cacheImages(List<ImageResult> imageList)
    {
        Log.d("Repository", "Caching images");
        for (ImageResult result: imageList) {
            this.addIntoCache(result.getUrl(), result);
        }
    }

    public Subject<Integer> getCacheSize()
    {
        try {
            return this.cacheSizeObservable;
        } finally {
                cacheSizeObservable.onNext(this.keyList.size());
        }
    }

    private ImageResult getImage(int position)
    {
        List<ImageResult> imageList = this.getImageList();

        if (imageList == null) {
            return null;
        }

        return imageList.get(position);
    }

    private List<ImageResult> getImageList()
    {
        if (this.keyList.isEmpty()) {
            return null;
        }

        List<ImageResult> imageList = new ArrayList<>();

        for (String key: keyList) {
            imageList.add(cache.get(key));
        }

        return imageList;
    }

    private void addIntoCache(String url, ImageResult imageRes)
    {
        this.keyList.add(url);
        this.cache.put(url, imageRes);
    }

    public boolean isCached(ImageResult image)
    {
        return this.cache.get(image.getUrl()) != null;
    }

    //remove cache for just one image
    public Observable<Void> removeCache(String url)
    {
        if (cache.get(url) == null) {
            return Completable
                    .error(new Throwable("Couldn't delete entry"))
                    .toObservable();
        }

        this.keyList.remove(url);
        this.cache.remove(url);

        if (this.keyList.isEmpty()) {
            this.cacheSizeObservable.onComplete(); // No more value to update with
        } else {
            this.cacheSizeObservable.onNext(this.keyList.size());
        }

        return Completable
                .complete()
                .toObservable();
    }
}
