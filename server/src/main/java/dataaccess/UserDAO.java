package dataaccess;

import model.UserData;

public interface UserDAO {
    /**
    clear: A method for clearing all data from the database. This is used during testing.
            createUser: Create a new user.
            getUser: Retrieve a user with the given username.
     */
    void clear() throws DataAccessException;
    UserData createUser(UserData user) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;

}
