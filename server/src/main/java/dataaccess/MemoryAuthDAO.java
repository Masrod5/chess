package dataaccess;

import model.AuthData;
import model.userData;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO {
    /**
     clear: A method for clearing all data from the database. This is used during testing.
     createUser: Create a new user.
     getUser: Retrieve a user with the given username.
     */
    final private ArrayList<userData> authTokens = new ArrayList<>();

    public AuthData createAuth(AuthData auth) {
        return auth;
    }

    public AuthData getAuth(AuthData auth) {
        return auth;
    }

    public AuthData deleteAuth(AuthData auth){
        authTokens.remove(auth);
        return auth;
    }

}