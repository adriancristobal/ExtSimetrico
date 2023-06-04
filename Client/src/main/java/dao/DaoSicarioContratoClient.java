package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import model.SicarioContrato;

import java.util.List;

public interface DaoSicarioContratoClient {

    Single<Either<String, SicarioContrato>> sendContratoToSicario(SicarioContrato sicarioContrato);
    Single<Either<String, List<SicarioContrato>>> getSicarioContratosBySicario(int sicarioId);
    Single<Either<String, SicarioContrato>> updateEstado(SicarioContrato sicarioContrato);
}
