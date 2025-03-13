package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
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

        dataAccess.createUser(new UserData("name", "password", "@gmail.com"));

        dataAccess.clear();

        var bella = dataAccess.getUser("name");

        assertNull(bella);
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void createUserSuccessful(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();

        dataAccess.createUser(new UserData("name", "password", "@gmail.com"));

        assertNotNull(dataAccess.getUser("name"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void createUserBadRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();

        assertThrows(DataAccessException.class, () -> {
            dataAccess.createUser(new UserData("bella", "password", "bella@gmail.com"));
            dataAccess.createUser(new UserData("bella", "password", "bella@gmail.com"));
        });
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void getUserGoodRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();
        dataAccess.createUser(new UserData("name", "password", "@gmail.com"));

        assertNotNull(dataAccess.getUser("name"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class, MemoryUserDAO.class})
    void getUserBadRequest(Class<? extends UserDAO> dbClass) throws Exception {

        UserDAO dataAccess = new MySQLUserDAO();
        dataAccess.createUser(new UserData("bella", "password", "@gmail.com"));

        assertNull(dataAccess.getUser("james"));
    }



}
