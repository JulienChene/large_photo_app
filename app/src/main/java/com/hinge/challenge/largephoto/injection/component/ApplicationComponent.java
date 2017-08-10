package com.hinge.challenge.largephoto.injection.component;

import android.content.Context;
import com.hinge.challenge.largephoto.injection.module.ApplicationModule;
import com.hinge.challenge.largephoto.injection.module.NetworkModule;
import com.hinge.challenge.largephoto.model.api.ImageService;
import com.hinge.challenge.largephoto.model.repository.ImageRepository;
import com.hinge.challenge.largephoto.ui.activity.BaseActivity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {NetworkModule.class, ApplicationModule.class})
public interface ApplicationComponent
{
    void inject(BaseActivity baseActivity);

    // Exposed to sub-graphs.
    Context context();
    ImageRepository imageRepository();
}
