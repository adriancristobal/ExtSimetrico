package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import model.Contrato;
import model.SicarioContrato;
import model.Usuario;

import java.util.List;

public interface DaoContratoClient {

    Single<Either<String, List<Contrato>>> getAllContratos();

    Single<Either<String, List<Contrato>>> getContratosByIdContratista(Integer idContratista);

    Single<Either<String, Contrato>> getContratoById(int id);

    Single<Either<String, Contrato>> postContrato(Contrato contrato);

    Single<Either<String, Contrato>> putContrato(Contrato contrato);

    Single<Either<String, String>> deleteContrato(int id);

    Single<Either<String, List<Usuario>>> getSicariosByHabilityLevel(int habilityLevel);


}
