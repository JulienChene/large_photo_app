package com.hinge.challenge.largephoto.ui.presenter;

import com.hinge.challenge.largephoto.injection.PerActivity;
import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.interactor.ImageListInteractorImpl;
import com.hinge.challenge.largephoto.ui.HomepageView;
import com.hinge.challenge.largephoto.util.espresso.EspressoIdlingResource;
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

    /**
     * Load all images from Repository
     */
    public void initialize()
    {
        this.homepageView.hideRetry();
        this.homepageView.showLoading();

        // Prevents Espresso from finishing the activity. Simulates the application is still processing
        EspressoIdlingResource.increment();

        this.imageInteractor.execute(new ImageListObserver(), null);
    }

    public void onRetryClicked()
    {
        this.initialize();
    }

    @Override
    public void destroy()
    {
        this.imageInteractor.dispose();
    }

    /**
     * Show image at position in separate Activity
     */
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
            if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement(); // Set app as idle.
            }

            e.printStackTrace();
            homepageView.hideLoading();
            homepageView.showRetry();
        }

        @Override
        public void onComplete()
        {
            if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement(); // Set app as idle.
            }
        }
    }
}
