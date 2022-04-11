package net.motimaa.skyblockcore.storage.database.queries;

import net.motimaa.skyblockcore.storage.database.MySQLDB;

/**
 * A class that represents a database query.
 *
 * @param <T> The type of the result
 */
public interface Query<T> {

    T execute(MySQLDB db);

}
