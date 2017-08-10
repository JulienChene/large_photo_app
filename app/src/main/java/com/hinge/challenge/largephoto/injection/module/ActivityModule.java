package com.hinge.challenge.largephoto.injection.module;

import android.app.Activity;
import com.hinge.challenge.largephoto.injection.PerActivity;
import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class ActivityModule
{
    private final Activity activity;

    public ActivityModule(Activity activity)
    {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    Activity provideActivity()
    {
        return this.activity;
    }
}
