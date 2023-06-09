package service.impl;

import dao.DaoSicarioContratoClient;
import dao.impl.DaoSicarioContratoClientImpl;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.SicarioContrato;
import service.ServiceSicarioContratoClient;

import java.util.List;

public class ServiceSicarioContratoClientImpl implements ServiceSicarioContratoClient {

    DaoSicarioContratoClient dao;

    @Inject
    public ServiceSicarioContratoClientImpl(DaoSicarioContratoClientImpl dao) {
        this.dao = dao;
    }


    @Override
    public Single<Either<String, SicarioContrato>> sendContratoToSicario(SicarioContrato sicarioContrato) {
        return dao.sendContratoToSicario(sicarioContrato);
    }

    @Override
    public Single<Either<String, List<SicarioContrato>>> getSicarioContratosBySicario(int idSicario) {
        return dao.getSicarioContratosBySicario(idSicario);
    }

    @Override
    public Single<Either<String, SicarioContrato>> updateEstado(SicarioContrato sicarioContrato) {
        return dao.updateEstado(sicarioContrato);
    }
}
