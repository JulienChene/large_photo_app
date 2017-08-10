package com.hinge.challenge.largephoto;

import android.app.Application;
import com.hinge.challenge.largephoto.injection.component.ApplicationComponent;
import com.hinge.challenge.largephoto.injection.component.DaggerApplicationComponent;
import com.hinge.challenge.largephoto.injection.module.ApplicationModule;

public class LargePhotoApplication extends Application
{
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.initializeComponent();
    }

    private void initializeComponent()
    {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent()
    {
        return this.applicationComponent;
    }
}
