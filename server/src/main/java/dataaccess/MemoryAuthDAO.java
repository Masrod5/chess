package dataaccess;

import model.AuthData;
import model.UserData;

import java.util.ArrayList;
import java.util.Objects;

public class MemoryAuthDAO implements AuthDAO {
    /**
     clear: A method for clearing all data from the database. This is used during testing.
     createUser: Create a new user.
     getUser: Retrieve a user with the given username.
     */
    final private ArrayList<AuthData> authTokens = new ArrayList<>();


    @Override
    public void createAuth(AuthData auth) {
        authTokens.add(auth);

    }

    @Override
    public AuthData getAuth(String auth) {

        for (AuthData auths : authTokens){
            if (Objects.equals(auths.authToken(), auth)){
                return auths;
            }
        }
        return null;
    }

    public int getSize(){
        return authTokens.size();
    }

    public void deleteAuth(AuthData auth){
        authTokens.remove(auth);

    }

}