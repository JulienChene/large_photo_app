package com.hinge.challenge.largephoto.model.interactor;

import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.List;

public class ImageListInteractorImpl extends ImageInteractor<List<ImageResult>, Void>
{
    @Inject
    public ImageListInteractorImpl(ImageRepository imageRepository)
    {
        super(imageRepository);
    }

    @Override
    protected Observable<List<ImageResult>> buildInteractorObservable(Void aVoid)
    {
        return this.imageRepository.getImagesObservable();
    }
}
