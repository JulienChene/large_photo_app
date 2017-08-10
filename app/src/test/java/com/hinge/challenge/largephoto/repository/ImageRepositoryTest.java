package com.hinge.challenge.largephoto.repository;

import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.api.ImageService;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import com.hinge.challenge.largephoto.util.ModelUtil;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
        List<ImageResult> response = ModelUtil.getImageList(10);
        given(imageService.getImages()).willReturn(Observable.just(response));

        imageRepository.getImagesObservable();

        verify(imageService).getImages();
    }
}
