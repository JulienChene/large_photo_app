package com.hinge.challenge.largephoto.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import com.hinge.challenge.largephoto.R;
import com.hinge.challenge.largephoto.injection.component.DaggerImageComponent;
import com.hinge.challenge.largephoto.injection.component.ImageComponent;
import com.hinge.challenge.largephoto.ui.fragment.GalleryFragment;

public class GalleryActivity extends BaseActivity
{
    private static final String INTENT_EXTRA_PARAM_POSITION = "com.hinge.INTENT_PARAM_POSITION";

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

        this.setTitle("lol");
        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
    }

    private void initializeActivity(Bundle savedInstanceState)
    {
        int position;

        position = getIntent().getIntExtra(INTENT_EXTRA_PARAM_POSITION, -1);
        addFragment(R.id.fragment_container, GalleryFragment.forImage(position));
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
}
