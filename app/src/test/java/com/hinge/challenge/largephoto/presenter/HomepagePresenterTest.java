package com.hinge.challenge.largephoto.presenter;

import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.interactor.ImageListInteractorImpl;
import com.hinge.challenge.largephoto.ui.HomepageView;
import com.hinge.challenge.largephoto.ui.presenter.HomepagePresenter;
import com.hinge.challenge.largephoto.util.TestDisposableObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class HomepagePresenterTest
{
    private HomepagePresenter homepagePresenter;

    @Mock
    HomepageView homepageView;
    @Mock
    ImageListInteractorImpl imageListInteractor;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        this.homepagePresenter = new HomepagePresenter(this.imageListInteractor);
        this.homepagePresenter.setView(homepageView);
    }

    @Test
    public void testImageListPresenterInitialize()
    {
        homepagePresenter.initialize();

        verify(homepageView).hideRetry();
        verify(homepageView).showLoading();
    }
}
