
//import dataaccess.UserDAO;

import chess.ChessGame;
import model.GameData;
import model.LoginRequest;
import model.UserData;

import java.util.ArrayList;
import java.util.List;

import static ui.ChessBoardPrint.drawBoard;

public class PreGameMenu {

    private final ServerFacade server;
    private final String serverURL;
    private State state = State.LOGOUT;
    private List<GameData> gameList = null;

    public PreGameMenu(String serverURL){
        server = new ServerFacade(serverURL);
        this.serverURL = serverURL;
    }

    public String eval(String input) throws Exception{
        var tokenstest = input.toLowerCase().split(" ");
        ArrayList<String> tokens = new ArrayList<>();
        for (String i : tokenstest) {
            if (!i.isEmpty()) {
                tokens.add(i);
            }
        }

        var command = (tokens.size() > 0) ? tokens.get(0) : "help";
//        var params = Arrays.copyOfRange(tokens, 1, tokens.size());

        var params = new ArrayList<String>();

        for (int i = 1; i < tokens.size(); i++) {
            params.add(tokens.get(i));
        }



        return switch (command) {
            case "register" -> register(params);
            case "logout" -> logout();
            case "login" -> login(params);
            case "list" -> listGames();
            case "create" -> createGame(params);
            case "quit" -> quit(params);
            case "join" -> joinGame(params);
            case "observe" -> observe(params);
            default -> help();
        };

    }

    private String quit(ArrayList<String> params){
        if (state == State.LOGIN){
            return "you need to log out before you quit";
        }
        return "quit";
    }

    private String observe(ArrayList<String> params) throws Exception{
        if (state == State.LOGOUT){
            return "you must log in to observe a game";
        }
        if (params.size() == 2){
            int gameID;
            try {
                gameID = Integer.parseInt(params.get(0));

            } catch (Exception e) {
                return "game to observe must be a number";
            }
            String color = params.get(1).toUpperCase();
            if (!(color.equals("BLACK") || color.equals("WHITE"))){
                return "team to observe must be typed \"black\" or \"white\"";
            }
            if (!(gameID > 0 && gameID <= gameList.size())){
                return "try running \"list\" to see what games you can observe";
            }else{
                gameID = gameList.get(gameID-1).gameID();
            }
            if (color.equals("WHITE")) {
                drawBoard(new ChessGame().getBoard(), false);
            }else{
                drawBoard(new ChessGame().getBoard(), true);
            }

            return "observing game: " + gameID + " as " + color;
        }else{
            return "incorrect number of parameters";
        }
    }

    private String joinGame(ArrayList<String> params) throws Exception{
        if (state == State.LOGOUT){
            return "you must log in to join a game";
        }
        if (params.size() == 2){
            int gameID = Integer.parseInt(params.get(0));
            String color = params.get(1).toUpperCase();
            if (!(color.equals("BLACK") || color.equals("WHITE"))){
                return "team to join as must be typed \"black\" or \"white\"";
            }
            if (gameList == null){
                return "you must run \"list\" before you can join a game";
            }
            if (!(gameID > 0 && gameID <= gameList.size())){
                return "try running \"list\" to see what games you can join";
            }else{
                gameID = gameList.get(gameID-1).gameID();
            }
            try {
                server.joinGame(gameID, color);
            } catch (Exception e) {
                return "already taken";
            }
            if (color.equals("WHITE")) {
                drawBoard(new ChessGame().getBoard(), false);
            }else{
                drawBoard(new ChessGame().getBoard(), true);
            }

            return "joined game: " + gameID + " as " + color;
        }else{
            return "incorrect number of parameters";
        }
    }

    private String createGame(ArrayList<String> params) throws Exception {
        if (state == State.LOGOUT){
            return "you are not logged in";
        }
        if (params.size() == 1) {
            String name = params.get(0);

            server.createGame(name);

        }else{
            return "incorrect number of parameters";
        }
        return "created game: " + params.get(0);
    }

    private String listGames() throws Exception{
        if (state == State.LOGIN){
            gameList = server.listGames().games();

            if (gameList.isEmpty()){
                return "you have no games. try to create a game by typing \"create\" <game name>";
            }
            for (int i = 0; i < gameList.size(); i++){
                String white = gameList.get(i).whiteUsername();
                String black = gameList.get(i).blackUsername();
                System.out.print("Game: " + (i+1) + " Name=" + gameList.get(i).gameName() + " White=" + white + " Black=" + black);
                System.out.println();
            }
        }else{
            return "you are not logged in";
        }
        return "";
    }

    public String login(ArrayList<String> params) throws Exception{
        if (state == State.LOGIN){
            return "you must logout before you can login again";
        }
        if (params.size() == 2) {

            state = State.LOGIN;
            String username = params.get(0);
            String password = params.get(1);
            UserData thing;
            try {
                server.login(new LoginRequest(username, password));
            } catch (Exception e) {
                return "incorrect username of password";
            }


            return String.format("You logged in as %s.", username);
        } else {
            return "incorrect number of parameters";
        }
    }

    public String logout() throws Exception{
        if (state == State.LOGOUT){
            return "you are already logged out";
        }
        state = State.LOGOUT;
        server.logout();
        return "you logged out";
    }

    public String register(ArrayList<String> params) throws Exception{
        if (state == State.LOGIN){
            return "you must log out before you register a new user";
        }
        if (params.size() == 3) {
            state = State.LOGIN;
            String username = params.get(0);
            String password = params.get(1);
            String email = params.get(2);
            UserData thing;
            try {
                server.register(new UserData(username, password, email));
            }catch (Exception e){
                return "username already taken";
            }


            return String.format("You registered as %s.", username);
        }
        return "incorrect number of parameters";
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
