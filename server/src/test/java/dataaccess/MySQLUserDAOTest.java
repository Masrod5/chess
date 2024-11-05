package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MySQLUserDAOTest {

//    @Test
//    void clear() {
//
//    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void clear(Class<? extends UserDAO> dbClass) throws Exception {
        UserDAO dataAccess = new MySQLUserDAO();

        dataAccess.createUser(new UserData("bella", "password", "bella@gmail.com"));
//        dataAccess.createUser(new UserData("betty", "has", "email"));

        dataAccess.clear();

        var bella = dataAccess.getUser("bella");
//        var betty = dataAccess.getUser("bella");
        assertNull(bella);
    }

    @Test
    void createUser() {

    }

    @Test
    void getUser() {
    }
}