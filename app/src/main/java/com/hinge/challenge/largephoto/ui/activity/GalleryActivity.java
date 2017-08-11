package com.hinge.challenge.largephoto.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import butterknife.BindString;
import butterknife.ButterKnife;
import com.hinge.challenge.largephoto.R;
import com.hinge.challenge.largephoto.injection.component.DaggerImageComponent;
import com.hinge.challenge.largephoto.injection.component.ImageComponent;
import com.hinge.challenge.largephoto.ui.fragment.GalleryFragment;

public class GalleryActivity extends BaseActivity implements GalleryFragment.OnImageChangedListener
{
    private static final String INTENT_EXTRA_PARAM_POSITION = "com.hinge.INTENT_PARAM_POSITION";

    @BindString(R.string.activity_gallery_title)
    String titleFormat;

    private ImageComponent activityComponent;

    public static Intent getCallingIntent(Context context, int position)
    {
        Intent callingIntent = new Intent(context, GalleryActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_POSITION, position);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        ButterKnife.bind(this);

        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        this.initializeInjector();
        this.initializeActivity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeActivity()
    {
        int position = getIntent().getIntExtra(INTENT_EXTRA_PARAM_POSITION, -1);
        GalleryFragment fragment = GalleryFragment.forImage(position);

        addFragment(R.id.fragment_container, fragment);

        fragment.setOnImageChangedListener(this);
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
    public void onImageChanged(int position, int listSize)
    {
        String title = String.format(this.titleFormat, position, listSize);
        this.setTitle(title);
    }
}
