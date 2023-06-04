package dao.impl;

import dao.DaoSicarioContrato;
import dao.connections.DBConnectionPool;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import model.SicarioContrato;
import model.Usuario;
import model.exception.ApiError;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import utils.ErrorConstants;
import utils.QueryConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DaoSicarioContratoImpl implements DaoSicarioContrato {

    private DBConnectionPool pool;

    @Inject
    public DaoSicarioContratoImpl(DBConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Either<ApiError, List<SicarioContrato>> getAll() {
        Either<ApiError, List<SicarioContrato>> result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<SicarioContrato> list = jtm.query(QueryConstants.GET_ALL_SICARIOS_CONTRATOS, BeanPropertyRowMapper.newInstance(SicarioContrato.class));
            result = Either.right(list);
        } catch (Exception e) {
            result = Either.left(new ApiError(ErrorConstants.ERROR_FIND_SICARIOS_CONTRATOS_LIST));
        }
        return result;
    }

    @Override
    public Either<ApiError, List<SicarioContrato>> getSicariosContratosBySicario(int idSicario) {
        Either<ApiError, List<SicarioContrato>> result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<SicarioContrato> list = jtm.query(QueryConstants.GET_SICARIOS_CONTRATOS_BY_SICARIO, BeanPropertyRowMapper.newInstance(SicarioContrato.class), idSicario);
            result = Either.right(list);
        } catch (Exception e) {
            result = Either.left(new ApiError(ErrorConstants.ERROR_FIND_SICARIOS_CONTRATOS_LIST));
        }
        return result;
    }

    @Override
    public Either<ApiError, List<Usuario>> getSicariosByHabilityLevel(int parseInt) {
        Either<ApiError, List<Usuario>> result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<Usuario> list = jtm.query(QueryConstants.GET_SICARIOS_CONTRATOS_BY_HABILITY_LEVEL, BeanPropertyRowMapper.newInstance(Usuario.class), parseInt);
            result = Either.right(list);
        } catch (Exception e) {
            result = Either.left(new ApiError(ErrorConstants.ERROR_FIND_SICARIOS_CONTRATOS_LIST));
        }
        return result;
    }

    @Override
    public Either<ApiError, SicarioContrato> get(int id) {
        return null;
    }

    @Override
    public SicarioContrato save(SicarioContrato sicarioContrato) {
        SicarioContrato result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(Objects.requireNonNull(jtm.getDataSource()));

            Map<String, Object> namedParameters = new HashMap<>();
            namedParameters.put("estado", sicarioContrato.getEstado().name());
            namedParameters.put("id_contrato", sicarioContrato.getId_contrato());
            namedParameters.put("id_sicario", sicarioContrato.getId_sicario());

            jdbcTemplate.update(QueryConstants.ADD_SICARIO_CONTRATO, namedParameters);
            result = sicarioContrato;
        } catch (Exception e) {
            throw new NotFoundException(ErrorConstants.ERROR_INSERT_SICARIO_CONTRATO);
        }
        return result;
    }


    @Override
    public SicarioContrato update(SicarioContrato sicarioContrato) {
        SicarioContrato result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(Objects.requireNonNull(jtm.getDataSource()));

            Map<String, Object> namedParameters = new HashMap<>();
            namedParameters.put("estado", sicarioContrato.getEstado().name());
            namedParameters.put("id_contrato", sicarioContrato.getId_contrato());
            namedParameters.put("id_sicario", sicarioContrato.getId_sicario());

            jdbcTemplate.update(QueryConstants.UPDATE_SICARIO_CONTRATO, namedParameters);
            result = sicarioContrato;
        } catch (Exception e) {
            throw new NotFoundException(ErrorConstants.ERROR_UPDATE_SICARIO_CONTRATO);
        }
        return result;
    }


    @Override
    public boolean delete(int id_contrato, int id_sicario) {
        boolean result;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(Objects.requireNonNull(jtm.getDataSource()));

            Map<String, Object> namedParameters = new HashMap<>();
            namedParameters.put("id_contrato", id_contrato);
            namedParameters.put("id_sicario", id_sicario);

            jdbcTemplate.update(QueryConstants.DELETE_SICARIO_CONTRATO, namedParameters);
            result = true;
        } catch (Exception e) {
            throw new NotFoundException(ErrorConstants.ERROR_DELETE_SICARIO_CONTRATO);
        }
        return result;
    }
}
