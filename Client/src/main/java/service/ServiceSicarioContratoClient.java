package service;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import model.SicarioContrato;

public interface ServiceSicarioContratoClient {

    Single<Either<String, SicarioContrato>> sendContratoToSicario(SicarioContrato sicarioContrato);
}
