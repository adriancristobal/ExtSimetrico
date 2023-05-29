package dao.impl;

import dao.DaoContrato;
import dao.connections.DBConnectionPool;
import domain.exceptions.InternalServerException;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import model.Contrato;
import model.exception.ApiError;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import utils.ErrorConstants;
import utils.QueryConstants;

import java.util.List;
import java.util.Objects;

public class DaoContratoImpl implements DaoContrato {

    private final DBConnectionPool pool;

    @Inject
    public DaoContratoImpl(DBConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Either<ApiError, List<Contrato>> getAll() {
        Either<ApiError, List<Contrato>> result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<Contrato> list = jtm.query(QueryConstants.GET_ALL_CONTRATOS, BeanPropertyRowMapper.newInstance(Contrato.class));
            result = Either.right(list);
        } catch (Exception e) {
            result = Either.left(new ApiError(ErrorConstants.ERROR_FIND_CONTRATO_LIST));
        }
        return result;
    }

    @Override
    public Either<ApiError, List<Contrato>> getContratosByIdContratista(Integer idContratista) {
        Either<ApiError, List<Contrato>> result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<Contrato> list = jtm.query(QueryConstants.GET_CONTRATOS_BY_ID_CONTRATISTA, BeanPropertyRowMapper.newInstance(Contrato.class), idContratista);
            result = Either.right(list);
        } catch (Exception e) {
            result = Either.left(new ApiError(ErrorConstants.ERROR_FIND_CONTRATO_LIST));
        }
        return result;
    }

    @Override
    public Either<ApiError, Contrato> get(int id) {
        return null;
    }

    @Override
    public Contrato save(Contrato contrato) {
        Contrato result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(Objects.requireNonNull(jtm.getDataSource()));
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(contrato);
            jdbcTemplate.update(QueryConstants.ADD_CONTRATO, namedParameters);
            result = contrato;
        } catch (Exception e) {
            throw new NotFoundException(ErrorConstants.ERROR_INSERT_CONTRATO);
        }
        return result;
    }

    @Override
    public Contrato update(Contrato contrato) {
        Contrato result;
        try{
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            jtm.update(QueryConstants.UPDATE_CONTRATO, contrato.getDetalle(), contrato.getClave(), contrato.getId());
            result = contrato;
        } catch (NotFoundException e) {
            throw new NotFoundException(ErrorConstants.ERROR_UPDATE_CONTRATO_NOT_FOUND_ID);
        } catch (Exception e) {
            throw new InternalServerException(ErrorConstants.ERROR_UPDATE_CONTRATO_INTERNAL_SERVER);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        boolean result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(Objects.requireNonNull(transactionManager.getDataSource()));
            jtm.update(QueryConstants.DELETE_CONTRATO, id);
            transactionManager.commit(txStatus);
            result = true;
        } catch (NotFoundException e) {
            throw new NotFoundException(ErrorConstants.ERROR_DELETE_CONTRATO);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw new InternalServerException(ErrorConstants.ERROR_DELETE_CONTRATO_INTERNAL_SERVER);
        }
        return result;
    }
}
