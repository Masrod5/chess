import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.LoginRequest;
import model.UserData;

import java.util.ArrayList;
import java.util.Arrays;

public class PreGameMenu {

    private final ServerFacade server;
    private final String serverURL;
    private State state = State.LOGOUT;

    public PreGameMenu(String serverURL){
        server = new ServerFacade(serverURL);
        this.serverURL = serverURL;
    }

    public String eval(String input) throws DataAccessException {
        var tokens = input.toLowerCase().split(" ");
        var command = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);

        return switch (command) {
            case "register" -> register(params);
            case "logout" -> logout();
            case "login" -> login(params);
            case "list" -> listGames();
            default -> help();
        };

    }

    private String listGames() throws DataAccessException {
        if (state == State.LOGIN){
            server.listGames();
        }else{
            throw new DataAccessException("unauthorized");
        }
    }

    public String login(String[] params) throws DataAccessException {
        if (params.length == 2) {

            state = State.LOGIN;
            String username = params[0];
            String password = params[1];
            UserData thing;
            server.login(new LoginRequest(username, password));


            return String.format("You logged in as %s.", username);
        }
        throw new DataAccessException("Expected: <yourname> <password> <email>");
    }

    public String logout() throws DataAccessException {
        state = State.LOGOUT;
        server.logout();
        return "you logged out";
    }

    public String register(String[] params) throws DataAccessException {
        if (params.length == 3) {
            state = State.LOGIN;
            String username = params[0];
            String password = params[1];
            String email = params[2];
            UserData thing;
            server.register(new UserData(username, password, email));


            return String.format("You registered as %s.", username);
        }
        throw new DataAccessException("Expected: <yourname> <password> <email>");
    }

    public String help() {
        if (state == State.LOGOUT) {
            return """
                    register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    login <USERNAME> <PASSWORD> - to play chess
                    quit - playing chess
                    help - with possible commands
                    """;
        }else {
            return """
                    create <NAME> - a game
                    list - games
                    join <ID> [WHITE|BLACK] - a game
                    observe <ID> - a game
                    logout - when you are done
                    quit - playing chess
                    help - with possible commands
                    """;
        }
    }

}
