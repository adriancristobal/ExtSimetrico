package dao.impl;

import dao.DaoLogeo;
import dao.connections.DBConnectionPool;
import domain.exceptions.InternalServerException;
import domain.exceptions.NotFoundException;
import jakarta.inject.Inject;
import model.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import utils.ErrorConstants;
import utils.QueryConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DaoLogeoImpl implements DaoLogeo {

    private final DBConnectionPool pool;

    @Inject
    public DaoLogeoImpl(DBConnectionPool pool) {
        this.pool = pool;
    }


    @Override
    public Usuario getByUsername(String username) {
        Usuario result = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<Usuario> list = jtm.query(QueryConstants.GET_USER_BY_USERNAME, BeanPropertyRowMapper.newInstance(Usuario.class), username);
            if (list.size() == 0) {
                return null;
            }
            result = list.get(0);
        } catch (Exception e) {
            throw new NotFoundException(ErrorConstants.ERROR_FIND_USER_WITH_USERNAME);
        }
        return result;
    }

    @Override
    public Usuario getByUsernameAndPassword(String username, String password) {
        Usuario result = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<Usuario> list = jtm.query(QueryConstants.GET_USER_BY_USERNAME_AND_PASSWORD, BeanPropertyRowMapper.newInstance(Usuario.class), username, password);
            if (list.size() == 0) {
                return null;
            }
            result = list.get(0);
        } catch (Exception e) {
            throw new NotFoundException(ErrorConstants.ERROR_FIND_USER_WITH_USERNAME_AND_PASSWORD);
        }
        return result;
    }

    @Override
    public List<String> getRoles(int userId) {
        List<String>  result = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            List<String> list = jtm.query(QueryConstants.GET_ROLE_BY_ID, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, userId);
            if (list.size() == 0) {
                return null;
            }
            result = list;
        } catch (Exception e) {
            throw new NotFoundException(ErrorConstants.ERROR_FIND_ROLE_WITH_ID);
        }
        return result;
    }

    @Override
    public Usuario add(Usuario user) {
        Usuario result = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(Objects.requireNonNull(jtm.getDataSource()));
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
            jdbcTemplate.update(QueryConstants.ADD_USER, namedParameters);
            result = user;
        } catch (Exception e) {
            throw new InternalServerException(ErrorConstants.ERROR_INSERT_USER);
        }
        return result;
    }


}
