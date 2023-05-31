package domain.service;

import io.vavr.Value;
import io.vavr.control.Either;
import model.Contrato;
import model.exception.ApiError;

import java.util.List;

public interface ServiceContrato {

    Either<ApiError, List<Contrato>> getAll();
    Either<ApiError, List<Contrato>> getContratosByIdContratista(Integer idContratista);
    Either<ApiError, List<Contrato>> getContratosByIdSicario(Integer idSicario);
    Either<ApiError, Contrato> get(int id);

    Contrato add(Contrato contrato);

    Contrato update(Contrato contrato);

    boolean delete(int id);


}
