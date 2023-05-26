package dao.connections;

import dao.connections.commun.ConnectionConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    Connection myConnection = null;

    public Connection getConnection() {
        try {
            myConnection = DriverManager.getConnection(ConnectionConstants.URL, ConnectionConstants.USER, ConnectionConstants.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myConnection;
    }

    public void closeConnection(Connection myConnection) {
        if (myConnection != null) {
            try {
                myConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
