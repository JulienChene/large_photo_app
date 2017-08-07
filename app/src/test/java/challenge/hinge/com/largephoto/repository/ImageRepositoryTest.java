package challenge.hinge.com.largephoto.repository;

import challenge.hinge.com.largephoto.model.ImageResponse;
import challenge.hinge.com.largephoto.model.api.ImageService;
import challenge.hinge.com.largephoto.model.repository.ImageRepository;
import challenge.hinge.com.largephoto.util.ModelUtil;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        ImageResponse response = ModelUtil.getImageResponse(10);
        given(imageService.getImages()).willReturn(Observable.just(response));

        imageRepository.getImagesObservable();

        verify(imageService).getImages();
    }
}
