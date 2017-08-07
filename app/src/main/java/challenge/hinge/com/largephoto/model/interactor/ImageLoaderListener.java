package challenge.hinge.com.largephoto.model.interactor;

import challenge.hinge.com.largephoto.model.ImageResult;

import java.util.List;

public interface ImageLoaderListener
{
    void onImagesLoaded(List<ImageResult> imageList);
    void onImageLoadFailed();
}
