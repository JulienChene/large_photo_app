package com.hinge.challenge.largephoto.model.interactor;

import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import io.reactivex.Observable;

import javax.inject.Inject;

public class RemoveImageInteractorImpl extends ImageInteractor<Void, Integer>
{
    @Inject
    public RemoveImageInteractorImpl(ImageRepository imageRepository)
    {
        super(imageRepository);
    }

    @Override
    protected Observable<Void> buildInteractorObservable(Integer position)
    {
        return this.imageRepository.removeCache(position);
    }
}
