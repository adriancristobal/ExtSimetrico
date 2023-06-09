package service;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import model.Usuario;

public interface ServiceLogeoClient {

    Single<Either<String, Boolean>> register(Usuario userRegisterDTO);

    Single<Either<String, Usuario>> login(String username, String password);

    Single<Either<String, Boolean>> loginHeader(String username, String password);
}
