package dataaccess;

import model.AuthData;

public interface AuthDAO {

    AuthData createAuth(AuthData auth) throws DataAccessException;
    AuthData getAuth(AuthData auth) throws DataAccessException;
    AuthData deleteAuth(AuthData auth) throws DataAccessException;

}
