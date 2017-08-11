package com.hinge.challenge.largephoto.model.interactor;

import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class ImageCacheSizeInteractorImpl extends ImageInteractor<Integer, Void>
{
    @Inject
    public ImageCacheSizeInteractorImpl(ImageRepository imageRepository)
    {
        super(imageRepository);
    }

    @Override
    protected Observable<Integer> buildInteractorObservable(Void aVoid)
    {
        return this.imageRepository.getCacheSize();
    }
}
