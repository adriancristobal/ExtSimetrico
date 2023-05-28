package dao.retrofit.calls;

import io.reactivex.rxjava3.core.Single;
import model.Usuario;
import retrofit2.Response;
import retrofit2.http.*;

public interface CallLogeoApi {

    @POST("register")
    Single<Boolean> register(@Body Usuario userRegisterDTO);
    /////////////////////////
    @GET("login")
    Single<Response<Usuario>> login(@Query("username") String username, @Query("password") String password);
    @GET("login")
    Single<Boolean> loginHeader(@Header("Authorization") String basicAuthorization);
}
