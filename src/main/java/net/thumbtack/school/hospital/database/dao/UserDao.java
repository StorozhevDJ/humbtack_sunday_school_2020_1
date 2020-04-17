package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.serverexception.ServerException;

public interface UserDao {

    /**
     * Get user info by Login and password
     *
     * @param login
     * @param password
     * @return inserted user if login and password is correct
     */
    User getByLogin(String login, String password) throws ServerException;

    /**
     * Get user info by token from JAVASESSIONID cookie
     *
     * @param token
     * @return inserted user if login and password is correct
     */
    User getByToken(Session token) throws ServerException;

    /**
     * LogIn user (Insert token for sign in user)
     *
     * @param user model with login/password and new token
     * @return true if user login successful
     */
    void logIn(User user) throws ServerException;

    /**
     * LogOut user (delete token)
     *
     * @param token to logOut user
     */
    void logOut(Session token) throws ServerException;

}
