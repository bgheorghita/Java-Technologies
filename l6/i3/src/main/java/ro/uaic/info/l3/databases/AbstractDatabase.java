package ro.uaic.info.l3.databases;

import java.sql.Connection;

public interface AbstractDatabase {
    Connection getConnection();
    void closeConnection(Connection connection);
}
