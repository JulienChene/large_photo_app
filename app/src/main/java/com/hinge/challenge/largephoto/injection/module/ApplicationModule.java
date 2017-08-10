package com.hinge.challenge.largephoto.injection.module;

import android.app.Application;
import android.content.Context;
import com.hinge.challenge.largephoto.model.api.ImageService;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApplicationModule
{
    private final Application application;

    public ApplicationModule(Application application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext()
    {
        return this.application;
    }

    @Provides
    @Singleton
    ImageRepository provideImageRepository(ImageService imageService)
    {
        return new ImageRepository(imageService);
    }

//    @Provides
//    @Singleton
//    ImageRepository provideImageRepository(ImageRepository imageRepository)
//    {
//        return imageRepository;
//    }
}
