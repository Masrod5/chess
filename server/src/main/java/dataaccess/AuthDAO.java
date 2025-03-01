package dataaccess;

import record.AuthData;

public interface AuthDAO {
    void clear();
    void createAuth(AuthData authdata);
    AuthData getAuth(String auth);
    void deleteAuth(AuthData auth);


}
