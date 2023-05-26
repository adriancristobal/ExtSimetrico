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
    public Single<Either<String, Boolean>> login(String username, String password) {

        return myApi.login(username, password).map(response -> {
                    Either<String, Boolean> result = Either.left("Error de comunicacion");
                    if (response.isSuccessful()) {
                        cacheAuthorization.setUser(username);
                        cacheAuthorization.setPassword(password);
                        String jwt = response.headers().get(ConstantsInterceptor.AUTHORIZATION);
                        cacheAuthorization.setJwt(jwt);
                        result = Either.right(true);
                    } else if (Objects.equals(response.errorBody().contentType(), MediaType.get("application/json"))) {
                        Gson g = new Gson();
                        ApiError apierror = g.fromJson(response.errorBody().charStream(), ApiError.class);
                        result = Either.left(apierror.getMessage());
                    }
                    return result;
                })
                .subscribeOn(Schedulers.io());
    }


    @Override
    public Single<Either<String, Boolean>> loginHeader(String username, String password) {
        return this.safeSingleApicall(myApi.loginHeader(Credentials.basic(username, password)));
    }
}
