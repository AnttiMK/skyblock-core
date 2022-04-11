package net.motimaa.skyblockcore.storage.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class MySQLDB implements Database {

    private DBState state;
    protected HikariDataSource dataSource;

    public MySQLDB() {
        this.state = DBState.CLOSED;
    }

    @Override
    public void initialize() {
        this.setState(DBState.MIGRATING);

        initDataSource();
    }

    public void initDataSource() {
        HikariConfig config = new HikariConfig();

        String host = "172.17.0.1";
        String port = "3306";
        String database = "s10_skyblock";

        config.setPoolName("sbcore-hikari");
        config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        config.addDataSourceProperty("url", "jdbc:mariadb://" + host + ":" + port + "/" + database);
        config.setUsername("u10_WaRGoe3djD");
        config.setPassword("crMuxQGHF=Fraxl!SwY1@CkN");

        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public DBState getState() {
        return this.state;
    }

    public void setState(DBState state) {
        this.state = state;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        if (!connection.isValid(5)) {
            connection.close();
            try {
                return getConnection();
            } catch (StackOverflowError e) {
                e.printStackTrace();
            }
        }
        if (connection.getAutoCommit()) connection.setAutoCommit(false);
        return connection;
    }

    public void returnToPool(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
