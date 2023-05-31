package domain.service.impl;

import dao.DaoContrato;
import domain.service.ServiceContrato;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Contrato;
import model.exception.ApiError;

import java.util.List;

public class ServiceContratoImpl implements ServiceContrato {

    private DaoContrato daoContrato;

    @Inject
    public ServiceContratoImpl(DaoContrato daoContrato) {
        this.daoContrato = daoContrato;
    }

    @Override
    public Either<ApiError, List<Contrato>> getAll() {
        return daoContrato.getAll();
    }

    @Override
    public Either<ApiError, List<Contrato>> getContratosByIdContratista(Integer idContratista) {
        return daoContrato.getContratosByIdContratista(idContratista);
    }

    @Override
    public Either<ApiError, List<Contrato>> getContratosByIdSicario(Integer idSicario) {
        return daoContrato.getContratosByIdSicario(idSicario);
    }

    @Override
    public Either<ApiError, Contrato> get(int id) {
        return daoContrato.get(id);
    }

    @Override
    public Contrato add(Contrato contrato) {
        return daoContrato.save(contrato);
    }

    @Override
    public Contrato update(Contrato contrato) {
        return daoContrato.update(contrato);
    }

    @Override
    public boolean delete(int id) {
        return daoContrato.delete(id);
    }
}
