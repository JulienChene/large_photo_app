package com.hinge.challenge.largephoto.model.interactor;

import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class ImageInteractorImpl extends ImageInteractor<ImageResult, Integer>
{
    @Inject
    public ImageInteractorImpl(ImageRepository imageRepository)
    {
        super(imageRepository);
    }

    @Override
    Observable<ImageResult> buildInteractorObservable(Integer position)
    {
        return this.imageRepository.getImageResultObservable(position);
    }
}
