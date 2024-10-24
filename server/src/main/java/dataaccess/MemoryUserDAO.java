package dataaccess;

import model.gameData;
import model.userData;

import java.util.HashMap;

public class MemoryDataAccess implements UserDAO {
    /** need to be able to
     * Create
     * Read
     * Update
     * Delete
     */

    final private HashMap<userData, gameData> database = new HashMap<>();

    public userData addUser(userData newUser) {

        return newUser;
    }
}
