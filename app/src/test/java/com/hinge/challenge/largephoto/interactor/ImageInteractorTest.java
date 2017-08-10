package com.hinge.challenge.largephoto.interactor;

import com.hinge.challenge.largephoto.model.interactor.ImageListInteractorImpl;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import com.hinge.challenge.largephoto.util.ModelUtil;
import com.hinge.challenge.largephoto.util.RxImmediateSchedulerRule;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ImageInteractorTest
{
    private ImageListInteractorImpl imageInteractorImp;
    private TestDisposableObserver testDisposableObserver;

    @Mock
    private ImageRepository imageRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    // Solution to make AndroidSchedulers.mainThread() work in the interactor
    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Before
    public void setup()
    {
        this.imageInteractorImp = new ImageListInteractorImpl(imageRepository);
        this.testDisposableObserver = new TestDisposableObserver();

        given(imageRepository.getImagesObservable()).willReturn(Single.just(ModelUtil.getImageList(10)));
    }

    @Test
    public void testLoadImageSuccessful()
    {
        imageInteractorImp.loadImages(testDisposableObserver);

        assertThat(testDisposableObserver.valuesCount).isNotZero();
    }

    @Test
    public void testSubscriptionWhenExecutingLoadImages()
    {
        imageInteractorImp.loadImages(testDisposableObserver);
        imageInteractorImp.dispose();

        assertThat(testDisposableObserver.isDisposed()).isTrue();
    }

    @Test
    public void testLoadImagesWithObserverNull()
    {
        expectedException.expect(NullPointerException.class);
        imageInteractorImp.loadImages(null);
    }

    private static class TestDisposableObserver<T> extends DisposableSingleObserver<T>
    {
        private int valuesCount = 0;

        @Override
        public void onSuccess(T value)
        {
            valuesCount++;
        }

        @Override
        public void onError(Throwable e)
        {

        }
    }
}
