package com.hinge.challenge.largephoto.model.interactor;

import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class RemoveImageInteractorImpl extends ImageInteractor<Void, String>
{
    @Inject
    public RemoveImageInteractorImpl(ImageRepository imageRepository)
    {
        super(imageRepository);
    }

    @Override
    Observable<Void> buildInteractorObservable(String url)
    {
        return this.imageRepository.removeCache(url);
    }
}
