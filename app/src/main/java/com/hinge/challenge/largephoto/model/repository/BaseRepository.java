package com.hinge.challenge.largephoto.model.repository;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.LruCache;
import com.hinge.challenge.largephoto.model.ImageResult;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Memory cache of the application. keyList lets us have an ordered list that the Presenter can rely upon
 */
abstract class BaseRepository
{
    private LruCache<String, ImageResult> cache;
    private ArrayList<String> keyList;
    private BehaviorSubject<Integer> cacheSizeObservable;

    public BaseRepository()
    {
        cache = this.createLruCache();
        cacheSizeObservable = BehaviorSubject.create();
    }

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
        // Only initialize keyList before first cache
        if (this.keyList == null) {
            keyList = new ArrayList<>();
        }

        for (ImageResult result : imageList) {
            this.addIntoCache(result.getUrl(), result);
        }

        this.updateCacheSize();
    }

    /**
     * Returns an Observable of the cache size
     */
    public Subject<Integer> getCacheSize()
    {
        return this.cacheSizeObservable;
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
        if (this.keyList == null) {
            return null;
        }

        List<ImageResult> imageList = new ArrayList<>();

        for (String key : keyList) {
            imageList.add(cache.get(key));
        }

        return imageList;
    }

    private void addIntoCache(String url, ImageResult imageRes)
    {
        this.keyList.add(url);
        this.cache.put(url, imageRes);
    }

    public boolean isCached(String url)
    {
        return url == null || keyList == null || cache.get(url) == null;
    }

    /**
     * Updates any subscriber to the CacheSize
     */
    private void updateCacheSize()
    {
        if (this.keyList.isEmpty()) {
            this.cacheSizeObservable.onComplete(); // No more value to update with. Call onComplete
        } else {
            this.cacheSizeObservable.onNext(this.keyList.size());
        }
    }

    public Observable<Void> removeCache(int position)
    {
        String url;

        try {
            url = this.keyList.get(position);
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            url = null;
        }

        return this.removeCache(url);
    }

    /**
     * Remove image with url from cache.
     */
    private Observable<Void> removeCache(String url)
    {
        // Return error if no such url is cached.
        if (this.isCached(url)) {
            return Completable
                    .error(new Throwable("Couldn't delete entry"))
                    .toObservable();
        }

        this.keyList.remove(url);
        this.cache.remove(url);

        this.updateCacheSize();

        return Completable
                .complete()
                .toObservable();
    }
}
