package net.motimaa.skyblockcore.storage.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLDB implements Database {

    private DBState state;

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

        HikariDataSource ds = new HikariDataSource(config);
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
}
