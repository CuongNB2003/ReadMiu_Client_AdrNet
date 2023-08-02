package net.cuongpro.readmiu.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.cuongpro.readmiu.model.Comment;
import net.cuongpro.readmiu.model.Favorite;
import net.cuongpro.readmiu.model.model_api.GetComic;
import net.cuongpro.readmiu.model.model_api.GetComicOne;
import net.cuongpro.readmiu.model.model_api.GetComment;
import net.cuongpro.readmiu.model.model_api.GetFavorite;
import net.cuongpro.readmiu.model.model_api.GetListPhoto;
import net.cuongpro.readmiu.model.model_api.GetOneFavorite;
import net.cuongpro.readmiu.model.model_api.InfoUser;
import net.cuongpro.readmiu.model.model_api.Login;
import net.cuongpro.readmiu.model.User;
import net.cuongpro.readmiu.model.model_api.MsgCallApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson  = new GsonBuilder().setLenient().create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(LinkApi.linkUrl+"/api/") // dung may ao adr
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("login")
    Call<Login> loginApp(@Query("username") String username, @Query("password") String password);
    @POST("reg")
    Call<Login> regUser(@Body User user, @Query("re_pass") String rePass);
    @GET("info-user")
    Call<InfoUser> getInfoUser(@Query("username") String username);
    @GET("comic")
    Call<GetComic> getListComic(@Query("page") int page, @Query("limit") int limit);
    @GET("comic")
    Call<GetComic> getListPhoto(@Query("page") int page, @Query("limit") int limit);
    @GET("comic/{id}/detail")
    Call<GetComicOne> getComic(@Path("id") String id);
    @GET("comic/{id}/read")
    Call<GetListPhoto> getListPhoto(@Path("id") String id);
    @POST("comment")
    Call<MsgCallApi> postComment(@Body Comment comment);
    @GET("comment")
    Call<GetComment> getCommetInComic(@Query("id") String id);
    @PUT("comment/{id}")
    Call<MsgCallApi> putComment(@Path("id") String id, @Query("cmt_new") String cmt);
    @DELETE("comment/{id}")
    Call<MsgCallApi> deletComment(@Path("id") String id);
    @GET("favorite/{id}")
    Call<GetFavorite> getFavorite (@Path("id") String id);
    @POST("favorite")
    Call<MsgCallApi> postFavorite (@Body Favorite favorite);
    @DELETE("favorite/{id}")
    Call<MsgCallApi> deleteFavorite (@Path("id") String id);
    @GET("favorite")
    Call<GetOneFavorite> getOneFavorite(@Query("iduser") String iduser, @Query("idcomic") String idcomic);
}
