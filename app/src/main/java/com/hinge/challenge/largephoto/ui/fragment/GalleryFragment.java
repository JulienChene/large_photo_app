package com.hinge.challenge.largephoto.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.hinge.challenge.largephoto.R;
import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.ui.GalleryView;
import com.hinge.challenge.largephoto.ui.activity.GalleryActivity;
import com.hinge.challenge.largephoto.ui.presenter.GalleryPresenter;
import dagger.internal.Preconditions;

import javax.inject.Inject;

public class GalleryFragment extends Fragment implements GalleryView
{
    private static final String PARAM_POSITION = "param_position";

    @Inject
    GalleryPresenter galleryPresenter;

    @BindView(R.id.image_view_detail)
    ImageView imageView;

    private Unbinder unbinder;

    private OnImageChangedListener imageChangedListener;

    public static GalleryFragment forImage(int position)
    {
        final GalleryFragment galleryFragment = new GalleryFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_POSITION, position);
        galleryFragment.setArguments(arguments);

        return galleryFragment;
    }

    // Set listener to update actionbar's title
    public void setOnImageChangedListener(OnImageChangedListener listener)
    {
        this.imageChangedListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ((GalleryActivity) this.getActivity()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentView = inflater.inflate(R.layout.fragment_gallery, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);

        // Specify we have a menu item to add
        this.setHasOptionsMenu(true);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        this.galleryPresenter.setView(this);
        this.initializeImage();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        this.galleryPresenter.subscribe();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.galleryPresenter.unsubscribe();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        this.unbinder.unbind();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        this.galleryPresenter.destroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.gallery_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                galleryPresenter.deleteImage();
                return true;
        }
        return false;
    }

    @Override
    public void setImage(ImageResult image, int position, int listSize)
    {
        Glide.with(this)
                .load(image.getUrl())
                .into(imageView);

        this.imageChangedListener.onImageChanged(position, listSize);
    }

    @Override
    public void goBack()
    {
        this.getActivity().finish();
    }

    private void initializeImage()
    {
        this.galleryPresenter.initialize(this.currentPosition());
    }

    /**
     * Retrieve the position that was used to initialize the Fragment
     */
    private int currentPosition()
    {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
        return arguments.getInt(PARAM_POSITION);
    }

    public interface OnImageChangedListener
    {
        void onImageChanged(int position, int listSize);
    }
}
