package com.hinge.challenge.largephoto.interactor;

import com.hinge.challenge.largephoto.model.interactor.ImageInteractor;
import com.hinge.challenge.largephoto.model.interactor.ImageListInteractorImpl;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import com.hinge.challenge.largephoto.util.ModelUtil;
import com.hinge.challenge.largephoto.util.RxImmediateSchedulerRule;
import com.hinge.challenge.largephoto.util.TestDisposableObserver;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
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

/**
 * Tests the Interactor abstract class
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageInteractorTest
{
    private static final int VALUES_PASSED = 10;

    private ImageInteractorTestClass interactorTestClass;
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
        this.interactorTestClass = new ImageInteractorTestClass(imageRepository);
        this.testDisposableObserver = new TestDisposableObserver();

        given(imageRepository.getImagesObservable()).willReturn(Observable.just(ModelUtil.getImageList(VALUES_PASSED)));
    }

    @Test
    public void testLoadImageSuccessful()
    {
        interactorTestClass.execute(this.testDisposableObserver, "");

        assertThat(testDisposableObserver.isComplete());
    }

    @Test
    public void testSubscriptionWhenExecutingLoadImages()
    {
        interactorTestClass.execute(this.testDisposableObserver, null);
        interactorTestClass.dispose();

        assertThat(testDisposableObserver.isDisposed()).isTrue();
    }

    @Test
    public void testLoadImagesWithObserverNull()
    {
        expectedException.expect(NullPointerException.class);
        interactorTestClass.execute(null, null);
    }

    private static class ImageInteractorTestClass extends ImageInteractor<Object, String>
    {

        public ImageInteractorTestClass(ImageRepository imageRepository)
        {
            super(imageRepository);
        }

        @Override
        protected Observable<Object> buildInteractorObservable(String params)
        {
            return Observable.empty();
        }

        @Override
        public void execute(DisposableObserver<Object> observer, String params)
        {
            super.execute(observer, params);
        }
    }
}
