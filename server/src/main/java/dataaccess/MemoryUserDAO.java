package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {
    /**
     clear: A method for clearing all data from the database. This is used during testing.
     createUser: Create a new user.
     getUser: Retrieve a user with the given username.
     */
    final private ArrayList<UserData> users = new ArrayList<>();

    public void clear() {
        users.clear();
    }

    public void createUser(UserData user) {
//        if (users.contains(user)) {
//            throw DataAccessException;
//        }
        users.add(user);
//        return user;
    }

    public UserData getUser(String user){
        for (UserData users : users) {
            if (users.userName() == user){
                return users;
            }
        }
        return null;
    }


}
