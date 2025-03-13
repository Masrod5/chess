package dataaccess;

import model.GameData;

import java.sql.SQLException;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;


public class MySQLGameDAO implements GameDAO{

    private static int id;

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    private void configureDatabase() throws DataAccessException {
        MySQLUserDAO.createDatabase(createStatements);
    }



    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE GAME";
        executeUpdate(statement);
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {                          //try with resources
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) { // get the prepared statement
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];

                    // might switch this to the suggested switch statement
                    if (param instanceof String p) {
                        ps.setString(i + 1, p);//set all the parameters
                    }
                    else if (param instanceof Integer p){
                        ps.setInt(i + 1, p);
                    }
                    else if (param == null) {
                        ps.setNull(i + 1, NULL);
                    }
                    else {
                        throw new IllegalArgumentException("" + i + "is not supported");
                    }
                }
                ps.executeUpdate();

                var resultSet = ps.getGeneratedKeys();

                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    @Override
    public int createGame(GameData game) throws DataAccessException {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public List<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS GAME (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` text,

              PRIMARY KEY (`gameID`)
            
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


}
