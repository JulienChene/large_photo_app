package challenge.hinge.com.largephoto.model.repository;

import android.support.annotation.NonNull;
import android.util.LruCache;
import challenge.hinge.com.largephoto.model.ImageResponse;
import challenge.hinge.com.largephoto.model.ImageResult;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

abstract class BaseRepository
{
    private LruCache<String, ImageResult> cache = this.createLruCache();
    private List<String> keyList = new ArrayList<>();

    @NonNull
    private LruCache<String, ImageResult> createLruCache()
    {
        return new LruCache<>(50);
    }

    Observable<ImageResponse> getImageResponseObservable()
    {
        return Observable.fromCallable(this::getImageResponse);
    }

    public void cacheImages(ImageResponse response)
    {
        List<ImageResult> imageResults = response.getImages();

        for (ImageResult result: imageResults) {
            this.addIntoCache(result.getUrl(), result);
        }
    }

    private ImageResponse getImageResponse()
    {
        if (this.keyList.isEmpty()) {
            return null;
        }

        List<ImageResult> imageList = new ArrayList<>();

        for (String key: keyList) {
            imageList.add(cache.get(key));
        }

        ImageResponse response = new ImageResponse();
        response.setImages(imageList);

        return response;
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
    public void removeCache(String url)
    {
        this.keyList.remove(url);
        this.cache.remove(url);
    }

    public void clearCache()
    {
        this.keyList.clear();
        this.cache.evictAll();
    }
}
