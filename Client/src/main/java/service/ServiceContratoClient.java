package service;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import model.Contrato;

import java.util.List;

public interface ServiceContratoClient {

    Single<Either<String, List<Contrato>>> getAllContratos();

    Single<Either<String, Contrato>> getContratoById(int id);

    Single<Either<String, Contrato>> postContrato(Contrato ingredient);

    Single<Either<String, Contrato>> putContrato(Contrato ingredient);

    Single<Either<String, String>> deleteContrato(int id);
}
