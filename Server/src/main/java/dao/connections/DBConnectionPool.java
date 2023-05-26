package dao.connections;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.ConfigJDBCProperties;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class DBConnectionPool {
    private ConfigJDBCProperties config;
    private DataSource hikariDataSource;

    @Inject
    public DBConnectionPool(ConfigJDBCProperties config) {
        this.config = config;
        this.hikariDataSource = getHikariPool();
    }

    public DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(this.config.getRuta());
        hikariConfig.setUsername(this.config.getUser());
        hikariConfig.setPassword(this.config.getPassword());
        hikariConfig.setDriverClassName(this.config.getDriver());
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        return new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() {
        Connection con=null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    public DataSource getDataSource() {
        return hikariDataSource;
    }

    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }


}
