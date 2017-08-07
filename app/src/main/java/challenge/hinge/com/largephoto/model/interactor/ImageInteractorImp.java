package challenge.hinge.com.largephoto.model.interactor;

import challenge.hinge.com.largephoto.model.ImageResponse;
import challenge.hinge.com.largephoto.model.ImageResult;
import challenge.hinge.com.largephoto.model.repository.ImageRepository;
import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class ImageInteractorImp implements ImageInteractor
{
    private ImageRepository imageRepository;
    private CompositeDisposable disposables;

    @Inject
    public ImageInteractorImp(ImageRepository imageRepository)
    {
        this.imageRepository = imageRepository;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void loadImages(DisposableObserver<ImageResponse> observer)
    {
        Preconditions.checkNotNull(observer);
        final Observable<ImageResponse> observable = this.imageRepository
                .getImagesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        this.addDisposable(observable.subscribeWith(observer));
    }

    @Override
    public void dispose()
    {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    private void addDisposable(Disposable disposable)
    {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }
}
