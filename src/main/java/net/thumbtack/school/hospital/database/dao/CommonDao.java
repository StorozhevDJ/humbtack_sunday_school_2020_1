package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.serverexception.ServerException;

public interface CommonDao {
    /**
     * удаляет все записи из всех таблиц, иными словами, очищает базу данных
     */
    void clear() throws ServerException;
}
