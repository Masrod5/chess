package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Objects;

public class MySQLUserDAO implements UserDAO{

    final private ArrayList<UserData> users = new ArrayList<>();


    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public UserData createUser(UserData user) throws DataAccessException {
        return null;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }
}
