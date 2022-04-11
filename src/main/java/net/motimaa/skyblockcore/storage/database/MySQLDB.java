package net.motimaa.skyblockcore.storage.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.motimaa.skyblockcore.SkyblockCore;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Singleton
public class MySQLDB implements Database {

    private final SkyblockCore plugin;
    private final FileConfiguration config;
    private DBState state;
    protected HikariDataSource dataSource;

    public MySQLDB(SkyblockCore plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.state = DBState.CLOSED;
    }

    @Override
    public void initialize() {
        this.setState(DBState.MIGRATING);

        initDataSource();
        initDatabase();
    }

    public void initDataSource() {
        HikariConfig config = new HikariConfig();

        String host = this.config.getString("database.host");
        String port = this.config.getString("database.port");
        String database = this.config.getString("database.database");

        config.setPoolName("sbcore-hikari");
        config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        config.addDataSourceProperty("url", "jdbc:mariadb://" + host + ":" + port + "/" + database);
        config.setUsername(this.config.getString("database.username"));
        config.setPassword(this.config.getString("database.password"));

        try {
            config.setMaximumPoolSize(this.config.getInt("database.max-connections"));
        } catch (IllegalArgumentException e) {
            plugin.getLogger().log(Level.WARNING, "Invalid value for database.max-connections in config.yml", e);
            config.setMaximumPoolSize(1);
        }

        config.setMaxLifetime(TimeUnit.MINUTES.toMillis(25L));
        config.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(29L));

        this.dataSource = new HikariDataSource(config);
    }

    private void initDatabase() {
        // tähän semmonen hauska query joka luo tablet tietokantaan
    }

    @Override
    public void shutdown() {
        if (getState() == DBState.OPEN) setState(DBState.CLOSING);
        setState(DBState.CLOSED);
        if (dataSource != null) {
            dataSource.close();
        }
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
