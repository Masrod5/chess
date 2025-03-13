package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MySQLAuthDAOTest {

    @BeforeEach
    @ValueSource(classes = {MySQLAuthDAO.class, MemoryAuthDAO.class})
    void cleanSlate() throws DataAccessException {
        AuthDAO dataAccess = new MySQLAuthDAO();
        dataAccess.clear();
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class, MemoryAuthDAO.class})
    void clear(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MySQLAuthDAO();
        dataAccess.createAuth(new AuthData("authToken", "username"));

        dataAccess.clear();

        assertNull(dataAccess.getAuth("authToken"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class, MemoryAuthDAO.class})
    void createAuthSuccessful(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MySQLAuthDAO();
        dataAccess.createAuth(new AuthData("authToken", "username"));

        assertNotNull(dataAccess.getAuth("authToken"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class, MemoryAuthDAO.class})
    void createAuthBadRequest(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MySQLAuthDAO();
        dataAccess.createAuth(new AuthData("", "username"));

        assertThrows(DataAccessException.class, () -> {
            throw new DataAccessException("bad request");
        });
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class, MemoryAuthDAO.class})
    void getAuthSuccessful(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MySQLAuthDAO();
        dataAccess.createAuth(new AuthData("authToken", "username"));

        assertNotNull(dataAccess.getAuth("authToken"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class, MemoryAuthDAO.class})
    void getAuthBadRequest(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MySQLAuthDAO();
        dataAccess.createAuth(new AuthData("authToken", "username"));

        assertNull(dataAccess.getAuth("auth"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class, MemoryAuthDAO.class})
    void deleteAuthSuccessful(Class<? extends AuthDAO> dbClass) throws Exception {

        AuthDAO dataAccess = new MySQLAuthDAO();
        dataAccess.createAuth(new AuthData("authToken", "username"));
        dataAccess.deleteAuth(new AuthData("authToken", "username"));

        assertNull(dataAccess.getAuth("authToken"));
    }




}
