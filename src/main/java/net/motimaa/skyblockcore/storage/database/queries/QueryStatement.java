package net.motimaa.skyblockcore.storage.database.queries;

import net.motimaa.skyblockcore.storage.database.MySQLDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class QueryStatement<T> implements Query<T> {

    private final String sql;
    private final int rowCount;

    protected QueryStatement(String sql) {
        this(sql, 10);
    }

    protected QueryStatement(String sql, int rowCount) {
        this.sql = sql;
        this.rowCount = rowCount;
    }

    @Override
    public T execute(MySQLDB db) {
        Connection connection = null;
        try {
            connection = db.getConnection();
            return executeWithConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.returnToPool(connection);
        }
    }

    public T executeWithConnection(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return executeQuery(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public T executeQuery(PreparedStatement statement) throws SQLException {
        try (statement) {
            statement.setFetchSize(rowCount);
            prepare(statement);
            try (ResultSet set = statement.executeQuery()) {
                return processResults(set);
            }
        }
    }

    public abstract void prepare(PreparedStatement statement) throws SQLException;

    public abstract T processResults(ResultSet set) throws SQLException;

    public String getSql() {
        return sql;
    }
}
