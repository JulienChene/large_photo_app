package com.hinge.challenge.largephoto.model.interactor;

import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class ImageInteractor<T, Params>
{
    protected final ImageRepository imageRepository;
    private CompositeDisposable disposables;

    public ImageInteractor(ImageRepository imageRepository)
    {
        this.imageRepository = imageRepository;
        this.disposables = new CompositeDisposable();
    }

    protected abstract Observable<T> buildInteractorObservable(Params params);

    /**
     * Makes sure that the Subscriber does its work on a separate thread and that the result is
     * returned on the main thread
     */
    public void execute(DisposableObserver<T> observer, Params params)
    {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable = buildInteractorObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        this.addDisposable(observable.subscribeWith(observer));
    }

    public void dispose()
    {
        if (!this.disposables.isDisposed()) {
            this.disposables.dispose();
        }
    }

    protected void addDisposable(Disposable disposable)
    {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }
}
