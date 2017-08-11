package com.hinge.challenge.largephoto.ui.activity;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import com.hinge.challenge.largephoto.R;
import com.hinge.challenge.largephoto.injection.component.DaggerImageComponent;
import com.hinge.challenge.largephoto.injection.component.ImageComponent;
import com.hinge.challenge.largephoto.ui.fragment.HomepageFragment;
import com.hinge.challenge.largephoto.util.espresso.EspressoIdlingResource;

public class HomepageActivity extends BaseActivity implements HomepageFragment.ImageListListener
{
    private ImageComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        this.addFragment(R.id.fragment_container, new HomepageFragment());
    }

    private void initializeInjector()
    {
        this.activityComponent = DaggerImageComponent.builder()
                .applicationComponent(this.getApplicationComponent())
                .activityModule(this.getActivityModule())
                .build();
    }

    public ImageComponent getComponent()
    {
        return this.activityComponent;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onImageClicked(int position)
    {
        this.navigator.navigateToGallery(this, position);
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource()
    {
        return EspressoIdlingResource.getIdlingResource();
    }
}
