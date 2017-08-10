package com.hinge.challenge.largephoto.injection.component;

import android.app.Activity;
import com.hinge.challenge.largephoto.injection.PerActivity;
import com.hinge.challenge.largephoto.injection.module.ActivityModule;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = ActivityModule.class)
public interface ActivityComponent
{
    Activity activity();
}
