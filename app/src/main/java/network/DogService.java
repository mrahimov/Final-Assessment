package network;

import models.DogImage;
import models.ModelResponce;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogService {

  @GET("/api/breed/{breed-name}/images/random")
  Call<DogImage> getDogImage(@Path("breed-name") String breed);

  @GET("/api/breed/{breed-name}/images")
  Call<ModelResponce> getDogThumbnails(@Path("breed-name") String breed);
}