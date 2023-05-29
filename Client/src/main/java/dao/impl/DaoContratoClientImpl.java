package dao.impl;

import dao.DaoContratoClient;
import dao.retrofit.calls.CallContratoApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Contrato;
import model.SicarioContrato;
import model.Usuario;

import java.util.List;

public class DaoContratoClientImpl extends DaoGenerics implements DaoContratoClient {


    private final CallContratoApi myApi;

    @Inject
    private DaoContratoClientImpl(CallContratoApi myApi) {
        this.myApi = myApi;
    }

    @Override
    public Single<Either<String, List<Contrato>>> getAllContratos() {
        return this.safeSingleApicall(myApi.getAllContratos());
    }
    @Override
    public Single<Either<String, List<Contrato>>> getContratosByIdContratista(Integer idContratista) {
        return this.safeSingleApicall(myApi.getContratosByIdContratista(idContratista));
    }
    @Override
    public Single<Either<String, Contrato>> getContratoById(int id) {
        return this.safeSingleApicall(myApi.getContratoById(id));
    }
    @Override
    public Single<Either<String, Contrato>> postContrato(Contrato contrato) {
        return this.safeSingleApicall(myApi.postContrato(contrato));
    }

    @Override
    public Single<Either<String, Contrato>> putContrato(Contrato contrato) {
        return this.safeSingleApicall(myApi.putContrato(contrato));
    }

    @Override
    public Single<Either<String, String>> deleteContrato(int id) {
        return this.safeSingleVoidApicall(myApi.deleteContrato(id));
    }

    @Override
    public Single<Either<String, List<Usuario>>> getSicariosByHabilityLevel(int habilityLevel) {
        return this.safeSingleApicall(myApi.getSicariosByHabilityLevel(habilityLevel));
    }



}
