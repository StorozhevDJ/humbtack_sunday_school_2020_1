package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.User;

public interface UserDao {

    /**
     * Get user info by Login and password
     *
     * @param ligin
     * @param password
     * @return inserted user if login and password is correct
     */
    User getByLogin(String login, String password);

    /**
     * Get user info by token from JAVASESSIONID cookie
     *
     * @param token
     * @return inserted user if login and password is correct
     */
    User getByToken(String token);


}
