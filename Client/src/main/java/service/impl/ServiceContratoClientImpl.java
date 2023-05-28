package service.impl;

import com.google.gson.Gson;
import dao.DaoContratoClient;
import dao.DaoSicarioContratoClient;
import dao.impl.DaoContratoClientImpl;
import dao.impl.DaoSicarioContratoClientImpl;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Contrato;
import model.SicarioContrato;
import model.Usuario;
import modelClient.Detalle;
import service.ServiceContratoClient;

import java.util.ArrayList;
import java.util.List;

public class ServiceContratoClientImpl implements ServiceContratoClient {

    DaoContratoClient dao;

    Gson gson = new Gson();

    @Inject
    public ServiceContratoClientImpl(DaoContratoClientImpl dao) {
        this.dao = dao;
    }

    @Override
    public Single<Either<String, List<Detalle>>> getAllContratos() {
        List<Detalle> detalleList = new ArrayList<>();
        Single<Either<String, List<Contrato>>> result = dao.getAllContratos();
        if (result == null) {
            return Single.just(Either.left("No hay contratos"));
        } else {
            List<Contrato> contratoListdao = result.blockingGet().get();
            for (Contrato contrato : contratoListdao) {
                Detalle detalle = gson.fromJson(contrato.getDetalle(), Detalle.class);
                detalleList.add(detalle);
            }
        }
        return Single.just(Either.right(detalleList));
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

    @Override
    public Single<Either<String, List<Usuario>>> getSicariosByHabilityLevel(int habilityLevel) {
        return dao.getSicariosByHabilityLevel(habilityLevel);
    }

}
