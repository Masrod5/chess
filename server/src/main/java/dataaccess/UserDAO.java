package dataaccess;

import record.UserData;

public interface UserDAO {
    void clear();
    UserData createUser(UserData user);
    UserData getUser(String username);
}
