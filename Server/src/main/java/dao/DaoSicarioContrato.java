package dao;

import io.vavr.control.Either;
import model.SicarioContrato;
import model.exception.ApiError;

import java.util.List;

public interface DaoSicarioContrato {

    Either<ApiError, List<SicarioContrato>> getAll();
    Either<ApiError, SicarioContrato> get(int id);
    SicarioContrato save(SicarioContrato sicarioContrato);
    SicarioContrato update(SicarioContrato sicarioContrato);
    boolean delete(int id_contrato, int id_sicario);
}
