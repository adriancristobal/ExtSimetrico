package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import model.SicarioContrato;

public interface DaoSicarioContratoClient {

    Single<Either<String, SicarioContrato>> sendContratoToSicario(SicarioContrato sicarioContrato);
}
