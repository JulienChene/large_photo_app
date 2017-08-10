package com.hinge.challenge.largephoto.model.interactor;

import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Single;
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

    protected void addDisposable(Disposable disposable)
    {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }

    abstract Observable<T> buildInteractorObservable(Params params);

    public void execute(DisposableObserver<T> observer, Params params)
//    public void execute(Disposable<T> observer, Params params)
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
}
