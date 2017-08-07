package challenge.hinge.com.largephoto.model;

import java.util.List;

public class ImageResponse
{
    private List<ImageResult> images;

    public List<ImageResult> getImages()
    {
        return images;
    }

    public void setImages(List<ImageResult> images)
    {
        this.images = images;
    }
}
