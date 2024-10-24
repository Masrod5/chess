package dataaccess;

import model.gameData;
import model.userData;
import dataaccess.UserDAO;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    /**
     clear: A method for clearing all data from the database. This is used during testing.
     createUser: Create a new user.
     getUser: Retrieve a user with the given username.
     */
    final private ArrayList<userData> users = new ArrayList<>();

    public void clear() {
        users.clear();
    }

    public void createUser(userData user) {
//        if (users.contains(user)) {
//            throw DataAccessException;
//        }
        users.add(user);
//        return user;
    }

    public userData getUser(String user){
        for (userData users : users) {
            if (users.userName() == user){
                return users;
            }
        }
        return null;
    }


}
