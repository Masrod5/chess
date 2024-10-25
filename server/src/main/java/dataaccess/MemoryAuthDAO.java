package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO {
    /**
     clear: A method for clearing all data from the database. This is used during testing.
     createUser: Create a new user.
     getUser: Retrieve a user with the given username.
     */
    final private ArrayList<UserData> authTokens = new ArrayList<>();


    @Override
    public void createAuth(AuthData auth) {

    }

    @Override
    public AuthData getAuth(String auth) {

        return null;
    }

    public void deleteAuth(AuthData auth){
        authTokens.remove(auth);

    }

}