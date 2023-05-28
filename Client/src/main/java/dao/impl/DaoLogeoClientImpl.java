package dao.impl;

import com.google.gson.Gson;
import dao.DaoLogeoClient;
import dao.retrofit.CacheAuthorization;
import dao.retrofit.calls.CallLogeoApi;
import dao.retrofit.produces.constants.ConstantsInterceptor;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Usuario;
import model.exception.ApiError;
import okhttp3.Credentials;
import okhttp3.MediaType;
import retrofit2.HttpException;

import java.util.Objects;

public class DaoLogeoClientImpl extends DaoGenerics implements DaoLogeoClient {

    private final CallLogeoApi myApi;
    private CacheAuthorization cacheAuthorization;

    @Inject
    private DaoLogeoClientImpl(CallLogeoApi myApi, CacheAuthorization cacheAuthorization) {
        this.myApi = myApi;
        this.cacheAuthorization = cacheAuthorization;
    }


    @Override
    public Single<Either<String, Boolean>> register(Usuario userRegisterDTO) {
        return safeSingleApicall(myApi.register(userRegisterDTO));
    }

    @Override
    public Single<Either<Object, Usuario>> login(String username, String password) {

        return myApi.login(username, password).map(response -> {
                    cacheAuthorization.setUser(username);
                    cacheAuthorization.setPassword(password);
                    String jwt = response.headers().get(ConstantsInterceptor.AUTHORIZATION);
                    cacheAuthorization.setJwt(jwt);
                    return Either.right(response.body());
                })
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<Object, Usuario> error = Either.left(new ApiError("Error de comunicacion").getMessage());
                    if (throwable instanceof HttpException) {
                        if (Objects.equals(((HttpException) throwable).response().errorBody().contentType(), MediaType.get("application/json"))) {
                            Gson g = new Gson();
                            ApiError apierror = g.fromJson(((HttpException) throwable).response().errorBody().string(), ApiError.class);
                            error = Either.left(apierror.getMessage());
                        } else {
                            error = Either.left(((HttpException) throwable).response().message());
                        }
                    }
                    return error;
                });

    }


    @Override
    public Single<Either<String, Boolean>> loginHeader(String username, String password) {
        return this.safeSingleApicall(myApi.loginHeader(Credentials.basic(username, password)));
    }
}
