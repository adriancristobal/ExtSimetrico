package dao;

import io.vavr.control.Either;
import model.Contrato;
import model.exception.ApiError;

import java.util.List;

public interface DaoContrato {

    Either<ApiError, List<Contrato>> getAll();
    Either<ApiError, List<Contrato>> getContratosByIdContratista(Integer idContratista);
    Either<ApiError, Contrato> get(int id);

    Contrato save(Contrato contrato);

    Contrato update(Contrato contrato);

    boolean delete(int id);


}
