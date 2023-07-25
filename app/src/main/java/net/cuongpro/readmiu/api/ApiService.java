package net.cuongpro.readmiu.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.cuongpro.readmiu.model.model_api.GetComic;
import net.cuongpro.readmiu.model.model_api.GetComicOne;
import net.cuongpro.readmiu.model.model_api.InfoUser;
import net.cuongpro.readmiu.model.model_api.Login;
import net.cuongpro.readmiu.model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson  = new GsonBuilder().setLenient().create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(LinkApi.linkUrl+"/api/") // dung may ao adr
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    //user
    @POST("login")
    Call<Login> loginApp(@Query("username") String username, @Query("password") String password);
    @POST("reg")
    Call<User> regUser(@Body User user);
    @GET("info-user")
    Call<InfoUser> getInfoUser(@Query("username") String username);
    // truyện
    @GET("comic")
    Call<GetComic> getListComic();
    @GET("comic/{id}/detail")
    Call<GetComicOne> getComic(@Path("id") String id);


}
