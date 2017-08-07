package challenge.hinge.com.largephoto.util;

import challenge.hinge.com.largephoto.model.ImageResponse;
import challenge.hinge.com.largephoto.model.ImageResult;

import java.util.ArrayList;
import java.util.List;

public class ModelUtil
{
    public static ImageResponse getImageResponse(int count)
    {
        ImageResponse response = new ImageResponse();
        List<ImageResult> imageList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            imageList.add(new ImageResult());
        }

        response.setImages(imageList);

        return response;
    }
}
