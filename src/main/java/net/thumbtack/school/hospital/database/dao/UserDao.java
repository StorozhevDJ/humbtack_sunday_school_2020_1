package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.User;

public interface UserDao {

    /**
     * Get user info by Login and password
     *
     * @param login
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
    User getByToken(User.Session token);

    /**
     * LogIn user (Insert token for sign in user)
     *
     * @param user model with login/password and new token
     * @return true if user login successful
     */
    boolean logIn(User user);

    /**
     * LogOut user (delete token)
     *
     * @param token to logOut user
     * @return true if user logout successful
     */
    boolean logOut(User.Session token);

}
