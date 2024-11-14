import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

import java.util.ArrayList;
import java.util.Arrays;

public class PreGameMenu {

    private final ServerFacade server;
    private final String serverURL;

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
            default -> help();
        };

    }

    public String logout() throws DataAccessException {
        server.logout();
        return "you logged out";
    }

    public String register(String[] params) throws DataAccessException {
        if (params.length == 3) {

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
        return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD> - to play chess
                quit - playing chess
                help - with possible commands
                """;
    }

}
