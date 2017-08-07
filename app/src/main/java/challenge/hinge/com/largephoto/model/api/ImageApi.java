package challenge.hinge.com.largephoto.model.api;

import challenge.hinge.com.largephoto.model.ImageResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ImageApi
{
    @GET("client/services/getImages.json")
    Observable<ImageResponse> getImages();
}
