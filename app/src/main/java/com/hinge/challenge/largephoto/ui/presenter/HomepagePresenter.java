package com.hinge.challenge.largephoto.ui.presenter;

import android.util.Log;
import com.hinge.challenge.largephoto.injection.PerActivity;
import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.interactor.ImageListInteractorImpl;
import com.hinge.challenge.largephoto.ui.HomepageView;
import io.reactivex.observers.DisposableObserver;

import javax.inject.Inject;
import java.util.List;

/**
 * {@link com.hinge.challenge.largephoto.ui.presenter.HomepagePresenter} that controls communication between views and model
 */
@PerActivity
public class HomepagePresenter implements Presenter
{
    private HomepageView homepageView;

    private final ImageListInteractorImpl imageInteractor;

    @Inject
    public HomepagePresenter(ImageListInteractorImpl interactor)
    {
        this.imageInteractor = interactor;
    }

    public void setView(HomepageView view)
    {
        this.homepageView = view;
    }

    @Override
    public void subscribe()
    {

    }

    @Override
    public void unsubscribe()
    {

    }

    public void initialize()
    {
        this.homepageView.hideRetry();
        this.homepageView.showLoading();
        this.imageInteractor.execute(new ImageListObserver(), null);
    }

    @Override
    public void destroy()
    {
        this.imageInteractor.dispose();
    }

    public void onPictureClick(int position)
    {
        this.homepageView.viewImage(position);
    }

    private final class ImageListObserver extends DisposableObserver<List<ImageResult>>
    {
        @Override
        public void onNext(List<ImageResult> value)
        {
            homepageView.hideLoading();
            homepageView.showImageList(value);
        }

        @Override
        public void onError(Throwable e)
        {
            homepageView.hideLoading();
            homepageView.showRetry();
        }

        @Override
        public void onComplete()
        {

        }
    }
}
