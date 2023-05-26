package domain.service.impl;

import dao.DaoSicarioContrato;
import domain.service.ServiceSicarioContrato;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.SicarioContrato;
import model.exception.ApiError;

import java.util.List;

public class ServiceSicarioContratoImpl implements ServiceSicarioContrato {

    private DaoSicarioContrato dao;

    @Inject
    public ServiceSicarioContratoImpl(DaoSicarioContrato dao) {
        this.dao = dao;
    }

    @Override
    public Either<ApiError, List<SicarioContrato>> getAll() {
        return dao.getAll();
    }

    @Override
    public Either<ApiError, SicarioContrato> get(int id) {
        return dao.get(id);
    }

    @Override
    public SicarioContrato add(SicarioContrato sicarioContrato) {
        return dao.save(sicarioContrato);
    }

    @Override
    public SicarioContrato update(SicarioContrato sicarioContrato) {
        return dao.update(sicarioContrato);
    }

    @Override
    public boolean delete(int id_contrato, int id_sicario) {
        return dao.delete(id_contrato, id_sicario);
    }
}
