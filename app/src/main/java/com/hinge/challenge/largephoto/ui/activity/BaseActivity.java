package com.hinge.challenge.largephoto.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.hinge.challenge.largephoto.LargePhotoApplication;
import com.hinge.challenge.largephoto.injection.component.ApplicationComponent;
import com.hinge.challenge.largephoto.injection.module.ActivityModule;
import com.hinge.challenge.largephoto.navigation.Navigator;

import javax.inject.Inject;

public class BaseActivity extends Activity
{
    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getApplicationComponent().inject(this);
    }

    void addFragment(int containerViewId, Fragment fragment)
    {
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected ApplicationComponent getApplicationComponent()
    {
        return ((LargePhotoApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule()
    {
        return new ActivityModule(this);
    }
}
