package dataaccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth(AuthData auth) throws DataAccessException;
    AuthData getAuth(String auth) throws DataAccessException;
    void deleteAuth(AuthData auth) throws DataAccessException;

}