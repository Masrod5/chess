package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MySQLUserDAOTest {


    @BeforeEach
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void cleanSlate() throws DataAccessException {
        UserDAO dataAccess = new MySQLUserDAO();
        dataAccess.clear();

    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void clear(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();
//        dataAccess.clear();

        dataAccess.createUser(new UserData("bella", "password", "bella@gmail.com"));
//        dataAccess.createUser(new UserData("betty", "has", "email"));

        dataAccess.clear();

        var bella = dataAccess.getUser("bella");
//        var betty = dataAccess.getUser("bella");
        assertNull(bella);
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void createUserSuccessful(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();
//        dataAccess.clear();

        dataAccess.createUser(new UserData("bella", "password", "bella@gmail.com"));

        assertNotNull(dataAccess.getUser("bella"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void createUserBadRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();
        dataAccess.createUser(new UserData("bella", "", "bella@gmail.com"));

        assertThrows(DataAccessException.class, () -> {
            throw new DataAccessException("bad request");
        });
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void getUserGoodRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();
        dataAccess.createUser(new UserData("bella", "", "bella@gmail.com"));

        assertNotNull(dataAccess.getUser("bella"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void getUserBadRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();
        dataAccess.createUser(new UserData("bella", "", "bella@gmail.com"));

        assertNull(dataAccess.getUser("bell"));
    }
}