package dataaccess;

import com.google.gson.Gson;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MySQLAuthDAO implements AuthDAO{

    public MySQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    private void configureDatabase() throws DataAccessException {
        MySQLUserDAO.createDatabase(createStatements); // use the same method as in MySQLUserDAO
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE AUTH";
        executeUpdate(statement);
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {                          //try with resources
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) { // get the prepared statement
                for (var i = 0; i < params.length-1; i++) {
                    var param = params[i];
                    if (param instanceof String p) {
                        ps.setString(i + 1, p); //set all the parameters
                    }
                    else if (param instanceof Integer p){
                        ps.setInt(i + 1, p);
                    }
                }
                ps.executeUpdate();

            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        var statement = "INSERT INTO AUTH (authToken, username) VALUES (?, ?)";

        var json = new Gson().toJson(auth);
        executeUpdate(statement, auth.authToken(), auth.username(), json);
    }

    @Override
    public AuthData getAuth(String auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {

            var statement = "SELECT * FROM AUTH WHERE authToken= ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, auth);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {

                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        AuthData newAuth = new AuthData(authToken, username);

        return newAuth;
    }

    @Override
    public void deleteAuth(AuthData auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {                          //try with resources
            try (var preparedStatement = conn.prepareStatement("DELETE FROM AUTH WHERE authToken=?")) {
                preparedStatement.setString(1, auth.authToken());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  AUTH (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
