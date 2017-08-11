package com.hinge.challenge.largephoto.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.hinge.challenge.largephoto.R;
import com.hinge.challenge.largephoto.injection.PerActivity;
import com.hinge.challenge.largephoto.model.ImageResult;
import com.hinge.challenge.largephoto.ui.HomepageView;
import com.hinge.challenge.largephoto.ui.activity.HomepageActivity;
import com.hinge.challenge.largephoto.ui.adapter.ImagesAdapter;
import com.hinge.challenge.largephoto.ui.adapter.ImagesLayoutManager;
import com.hinge.challenge.largephoto.ui.presenter.HomepagePresenter;

import javax.inject.Inject;
import java.util.List;

@PerActivity
public class HomepageFragment extends Fragment implements HomepageView
{
    /**
     * Interface for listening user list events.
     */
    public interface ImageListListener
    {
        void onImageClicked(final int position);
    }

    @Inject
    HomepagePresenter homepagePresenter;
    @Inject
    ImagesAdapter imagesAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_view)
    RelativeLayout progressView;
    @BindView(R.id.retry_view)
    RelativeLayout retryView;
    @BindView(R.id.button_retry)
    Button retryButton;

    private ImageListListener imageListListener;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ((HomepageActivity) this.getActivity()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentView = inflater.inflate(R.layout.fragment_homepage, container, false);
        this.unbinder = ButterKnife.bind(this, fragmentView);

        this.initializeRecyclerView();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        this.homepagePresenter.setView(this);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (this.getActivity() instanceof ImageListListener) {
            this.imageListListener = (ImageListListener) this.getActivity();
        }

        this.homepagePresenter.subscribe();

        this.loadUserList();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.homepagePresenter.unsubscribe();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        this.recyclerView.setAdapter(null);
        this.unbinder.unbind();
    }

    @Override
    public void showLoading()
    {
        this.progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
        this.progressView.setVisibility(View.GONE);
    }

    @Override
    public void showRetry()
    {
        this.retryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry()
    {
        this.retryView.setVisibility(View.GONE);
    }

    @Override
    public void viewImage(int position)
    {
        this.imageListListener.onImageClicked(position);
    }

    @Override
    public void showImageList(List<ImageResult> imageList)
    {
        if (imageList != null) {
            this.imagesAdapter.setImages(imageList);
        }
    }

    /**
     * Load all images
     */
    private void loadUserList()
    {
        this.homepagePresenter.initialize();
    }

    @OnClick(R.id.button_retry)
    void onButtonRetryClick()
    {
        this.homepagePresenter.onRetryClicked();
    }

    private void initializeRecyclerView()
    {
        this.imagesAdapter.setOnItemClickListener(onItemClickListener);
        this.recyclerView.setLayoutManager(new ImagesLayoutManager(this.getActivity()));
        this.recyclerView.setAdapter(this.imagesAdapter);
    }

    private ImagesAdapter.OnItemClickListener onItemClickListener = position -> {
        if (HomepageFragment.this.homepagePresenter != null) {
            HomepageFragment.this.homepagePresenter.onPictureClick(position);
        }
    };
}
