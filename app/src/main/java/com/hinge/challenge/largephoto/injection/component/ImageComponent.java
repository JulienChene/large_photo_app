package com.hinge.challenge.largephoto.injection.component;

import com.hinge.challenge.largephoto.injection.PerActivity;
import com.hinge.challenge.largephoto.injection.module.ActivityModule;
import com.hinge.challenge.largephoto.injection.module.ImageModule;
import com.hinge.challenge.largephoto.ui.fragment.GalleryFragment;
import com.hinge.challenge.largephoto.ui.fragment.HomepageFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, ImageModule.class})
public interface ImageComponent
{
    void inject(HomepageFragment fragment);

    void inject(GalleryFragment fragment);
}
