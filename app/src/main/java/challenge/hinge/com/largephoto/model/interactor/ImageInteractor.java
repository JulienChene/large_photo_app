package challenge.hinge.com.largephoto.model.interactor;

import challenge.hinge.com.largephoto.model.ImageResponse;
import io.reactivex.observers.DisposableObserver;

public interface ImageInteractor
{
    void loadImages(DisposableObserver<ImageResponse> listener);
    void dispose();
}
