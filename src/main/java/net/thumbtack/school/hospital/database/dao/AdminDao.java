package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.Admin;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;

public interface AdminDao {
    /**
     * Add new administrator
     *
     * @param admin - new admin
     * @return inserted admin
     */
    Admin insert(Admin admin);

    /**
     * Get admin info by token from JAVASESSIONID cookie
     *
     * @param token
     * @return admin account
     */
    Admin getByToken(Session token);

    /**
     * Get admin info by User ID
     *
     * @param id
     * @return
     */
    Admin getByUserId(int id);

    /**
     * Get Admins count in DB
     *
     * @return int
     */
    int getCount();
}
