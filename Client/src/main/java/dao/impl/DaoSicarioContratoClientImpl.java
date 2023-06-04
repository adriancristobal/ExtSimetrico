package dao.impl;

import dao.DaoSicarioContratoClient;
import dao.retrofit.calls.CallContratoApi;
import dao.retrofit.calls.CallSicarioContratoApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.SicarioContrato;

import java.util.List;

public class DaoSicarioContratoClientImpl extends DaoGenerics implements DaoSicarioContratoClient {

    private final CallSicarioContratoApi myApi;

    @Inject
    private DaoSicarioContratoClientImpl(CallSicarioContratoApi myApi) {
        this.myApi = myApi;
    }

    @Override
    public Single<Either<String, SicarioContrato>> sendContratoToSicario(SicarioContrato sicarioContrato) {
        return this.safeSingleApicall(myApi.postSicarioContrato(sicarioContrato));
    }

    @Override
    public Single<Either<String, List<SicarioContrato>>> getSicarioContratosBySicario(int sicarioId) {
        return this.safeSingleApicall(myApi.getSicarioContratosBySicario(sicarioId));
    }

    @Override
    public Single<Either<String, SicarioContrato>> updateEstado(SicarioContrato sicarioContrato) {
        return this.safeSingleApicall(myApi.putSicarioContrato(sicarioContrato));
    }
}
