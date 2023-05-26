package dao.impl;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import model.exception.ApiError;
import okhttp3.MediaType;
import retrofit2.HttpException;
import retrofit2.Response;

import java.util.Objects;

abstract class DaoGenerics {

    public <T> Single<Either<String, T>> safeSingleApicall(Single<T> call) {
        return call.map(t -> Either.right(t).mapLeft(Object::toString))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    Either<String, T> error = Either.left(new ApiError("Error de comunicacion").getMessage());
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

    public Single<Either<String, String>> safeSingleVoidApicall(Single<Response<Void>> call) {
        return call.map(response -> {
                    var retorno = Either.right("OK").mapLeft(Object::toString);
                    if (!response.isSuccessful()) {
                        retorno = Either.left(response.message());
                    }
                    return retorno;
                })
                .subscribeOn(Schedulers.io());
    }

    public Single<Either<String, Boolean>> safeSingleVoidOtraOpcionApicall(Single<Response<Void>> call) {
        return call.map(objectResponse -> {
                    Either<String, Boolean> result = Either.left("Error de comunicacion");
                    if (objectResponse.isSuccessful()) {
                        result = Either.right(true);
                    } else if (Objects.equals(objectResponse.errorBody().contentType(), MediaType.get("application/json"))) {
                        Gson g = new Gson();
                        ApiError apierror = g.fromJson(objectResponse.errorBody().charStream(), ApiError.class);
                        result = Either.left(apierror.getMessage());
                    }
                    return result;
                })
                .subscribeOn(Schedulers.io());
    }

}



