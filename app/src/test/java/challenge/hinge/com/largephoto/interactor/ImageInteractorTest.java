package challenge.hinge.com.largephoto.interactor;

import challenge.hinge.com.largephoto.model.api.ImageService;
import challenge.hinge.com.largephoto.model.interactor.ImageInteractorImp;
import challenge.hinge.com.largephoto.model.repository.ImageRepository;
import challenge.hinge.com.largephoto.util.ModelUtil;
import challenge.hinge.com.largephoto.util.RxImmediateSchedulerRule;
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

@RunWith(MockitoJUnitRunner.class)
public class ImageInteractorTest
{
    private ImageInteractorImp imageInteractorImp;
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
        this.imageInteractorImp = new ImageInteractorImp(imageRepository);
        this.testDisposableObserver = new TestDisposableObserver();

        given(imageRepository.getImagesObservable()).willReturn(Observable.just(ModelUtil.getImageResponse(10)));
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

    private static class TestDisposableObserver<T> extends DisposableObserver<T>
    {
        private int valuesCount = 0;

        @Override
        public void onNext(T value)
        {
            valuesCount++;
        }

        @Override
        public void onError(Throwable e)
        {

        }

        @Override
        public void onComplete()
        {

        }
    }
}
