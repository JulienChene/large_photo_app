package com.hinge.challenge.largephoto.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hinge.challenge.largephoto.R;
import com.hinge.challenge.largephoto.model.ImageResult;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>
{
    private List<ImageResult> imageList;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    public ImagesAdapter(Context context)
    {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageList = Collections.emptyList();
    }

    public void setImages(List<ImageResult> imageList)
    {
        this.imageList = imageList;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = this.layoutInflater.inflate(R.layout.view_holder_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position)
    {
        ImageResult image = this.imageList.get(position);
        holder.setImageUrl(image.getUrl());
        holder.itemView.setOnClickListener(v -> {
            if (ImagesAdapter.this.onItemClickListener != null) {
                ImagesAdapter.this.onItemClickListener.onUserItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return this.imageList.size();
    }

    public interface OnItemClickListener
    {
        void onUserItemClicked(int position);
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.image_view)
        ImageView imageView;

        public ImageViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setImageUrl(String url)
        {
            Glide.with(itemView)
                    .load(url)
                    .apply(RequestOptions.centerCropTransform()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // Writes resources to disk after they've been decoded.
//                            .placeholder() // Set a
//                            .error() // Set a drawable if
                    )
                    .into(imageView);
        }
    }
}
