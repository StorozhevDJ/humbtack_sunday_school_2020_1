package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.Admin;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.serverexception.ServerException;

public interface AdminDao {
    /**
     * Add new administrator
     *
     * @param admin - new admin
     * @return inserted admin
     */
    Admin insert(Admin admin) throws ServerException;

    /**
     * Get admin info by token from JAVASESSIONID cookie
     *
     * @param token
     * @return admin account
     */
    Admin getByToken(Session token) throws ServerException;

    /**
     * Get admin info by User ID
     *
     * @param id
     * @return
     */
    Admin getByUserId(int id) throws ServerException;

    /**
     * Get Admins count in DB
     *
     * @return int
     */
    int getCount() throws ServerException;
}
