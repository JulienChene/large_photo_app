package com.hinge.challenge.largephoto.interactor;

import com.hinge.challenge.largephoto.model.interactor.RemoveImageInteractorImpl;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import com.hinge.challenge.largephoto.util.RxImmediateSchedulerRule;
import com.hinge.challenge.largephoto.util.TestDisposableObserver;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

public class RemoveImageInteractorTest
{
    private static final int POSITION = 0;

    private RemoveImageInteractorImpl removeImageInteractor;
    private TestDisposableObserver<Void> testDisposableObserver;

    @Mock
    private ImageRepository imageRepository;

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        this.removeImageInteractor = new RemoveImageInteractorImpl(this.imageRepository);
        this.testDisposableObserver = new TestDisposableObserver<>();
    }

    @Test
    public void testSubscriptionWhenExecutingRemoveImage()
    {
        removeImageInteractor.execute(testDisposableObserver, POSITION);

        assertThat(testDisposableObserver.hasReturned()).isTrue();
    }
}
