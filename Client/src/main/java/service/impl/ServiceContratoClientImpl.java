package service.impl;

import dao.DaoContratoClient;
import dao.impl.DaoContratoClientImpl;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Contrato;
import service.ServiceContratoClient;

import java.util.List;

public class ServiceContratoClientImpl implements ServiceContratoClient {

    DaoContratoClient dao;

    @Inject
    public ServiceContratoClientImpl(DaoContratoClientImpl dao) {
        this.dao = dao;
    }

    @Override
    public Single<Either<String, List<Contrato>>> getAllContratos() {
        return dao.getAllContratos();
    }

    @Override
    public Single<Either<String, Contrato>> getContratoById(int id) {
        return dao.getContratoById(id);
    }

    @Override
    public Single<Either<String, Contrato>> postContrato(Contrato ingredient) {
        return dao.postContrato(ingredient);
    }

    @Override
    public Single<Either<String, Contrato>> putContrato(Contrato ingredient) {
        return dao.putContrato(ingredient);
    }

    @Override
    public Single<Either<String, String>> deleteContrato(int id) {
        return dao.deleteContrato(id);
    }
}
