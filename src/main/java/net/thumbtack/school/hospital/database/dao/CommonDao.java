package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.serverexception.ServerException;

public interface CommonDao {
    /**
     * Delete all records from all tables
     */
    void clear() throws ServerException;
}
