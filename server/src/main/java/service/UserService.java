package service;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.LoginRequest;
import model.AuthData;
import model.userData;

public class UserService {

    UserDAO userDAO;
    // constructer that uses a userDAO
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

//    public AuthData register(UserData user) {
//
//    }


    public AuthData login(LoginRequest user) throws DataAccessException {

        // get user by username
        userData info = userDAO.getUser(user.username());
        // check if the passwords match
        if (user.password() == info.password()){
            // create a new auth token
            // add the new auth token to the database somehow
            // return the auth token
        }




        return null;
    }
//    public void logout(AuthData auth) {
//
//    }

}
