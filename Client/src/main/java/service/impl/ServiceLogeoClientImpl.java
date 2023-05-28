package service.impl;

import dao.DaoLogeoClient;
import dao.impl.DaoLogeoClientImpl;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Usuario;
import service.ServiceContratoClient;
import service.ServiceLogeoClient;

public class ServiceLogeoClientImpl implements ServiceLogeoClient {

    private DaoLogeoClient dao;

    @Inject
    public ServiceLogeoClientImpl(DaoLogeoClientImpl dao) {
        this.dao = dao;
    }


    @Override
    public Single<Either<String, Boolean>> register(Usuario userRegisterDTO) {
        return dao.register(userRegisterDTO);
    }

    @Override
    public Single<Either<String, Usuario>> login(String username, String password) {
        Single<Either<Object, Usuario>> either = dao.login(username, password);
        return either.map(either1 -> {
            if (either1.isRight()) {
                return Either.right(either1.get());
            } else {
                return Either.left(either1.getLeft().toString());
            }
        });
    }

    @Override
    public Single<Either<String, Boolean>> loginHeader(String username, String password) {
        return dao.loginHeader(username, password);
    }
}
