package service;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import model.Contrato;
import model.SicarioContrato;
import model.Usuario;
import modelClient.Detalle;

import java.util.List;

public interface ServiceContratoClient {

    Single<Either<String, List<Detalle>>> getAllContratos();

    Single<Either<String, List<Detalle>>> getContratosByIdContratista(Integer idContratista);
    Single<Either<String, List<Detalle>>> getContratosByIdSicario(int idSicario);

    Single<Either<String, Contrato>> getContratoById(int id);

    Single<Either<String, Contrato>> postContrato(Contrato ingredient);

    Single<Either<String, Contrato>> putContrato(Contrato ingredient);

    Single<Either<String, String>> deleteContrato(int id);

    Single<Either<String, List<Usuario>>> getSicariosByHabilityLevel(int habilityLevel);

}
