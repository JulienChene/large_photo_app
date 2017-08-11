package com.hinge.challenge.largephoto.repository;

import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.api.ImageService;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import com.hinge.challenge.largephoto.util.ModelUtil;
import com.hinge.challenge.largephoto.util.TestDisposableObserver;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ImageRepositoryTest
{
    private ImageRepository imageRepository;

    @Mock
    private ImageService imageService;

    @Before
    public void setup()
    {
        imageRepository = new ImageRepository(imageService);
    }

    @Test
    public void testGetImage()
    {
        TestDisposableObserver<List<ImageResult>> disposableObserver = new TestDisposableObserver<>();

        this.initCache(disposableObserver);

        verify(imageService).getImages();
        assertThat(disposableObserver.hasReturned()).isTrue(); // This could be an Error
    }

    private void initCache(TestDisposableObserver<List<ImageResult>> disposableObserver)
    {
        List<ImageResult> response = ModelUtil.getImageList(10);
        given(imageService.getImages()).willReturn(Observable.just(response));

        imageRepository.getImagesObservable()
                .subscribe(disposableObserver);
    }
}
