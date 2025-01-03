
//import dataaccess.UserDAO;

import chess.*;
import model.GameData;
import model.LoginRequest;
import model.UserData;
import serverfacade.ServerFacade;
import serverfacade.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Math.abs;
import static ui.ChessBoardPrint.drawBoard;

public class PreGameMenu {

    private final ServerFacade server;
    private final String serverURL;
    private State state = State.LOGOUT;
    public List<GameData> gameList = null;

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
            case "h", "highlight" -> highlight(params);
            case "redraw" -> redraw();
            case "masonanimation" -> clear();
            default -> help();
        };

    }

    private String clear() throws Exception {
        server.clear();
        return "you cleared";
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
        if (params.size() == 1){
            int gameID;
            try {
                gameID = Integer.parseInt(params.get(0));

            } catch (Exception e) {
                return "game to observe must be a number";
            }
//            String color = params.get(1).toUpperCase();
//            if (!(color.equals("BLACK") || color.equals("WHITE"))){
//                return "team to observe must be typed \"black\" or \"white\"";
//            }
            if (gameList == null){
                return "you must run \"list\" before you can observe a game";
            }
            if (!(gameID > 0 && gameID <= gameList.size())){
                return "try running \"list\" to see what games you can observe";
            }else{
                gameID = gameList.get(gameID-1).gameID();
            }
//            if (color.equals("WHITE")) {
            drawBoard(new ChessGame().getBoard(), false, null);
//            }else{
//                drawBoard(new ChessGame().getBoard(), true);
//            }

            return "observing game: " + gameID;
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
                drawBoard(new ChessGame().getBoard(), false, null);
            }else{
                drawBoard(new ChessGame().getBoard(), true, null);
            }

            state = State.JOINED;
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


            String username = params.get(0);
            String password = params.get(1);
            UserData thing;
            try {
                server.login(new LoginRequest(username, password));
            } catch (Exception e) {
                return "incorrect username of password";
            }
            state = State.LOGIN;


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

            String username = params.get(0);
            String password = params.get(1);
            String email = params.get(2);
            UserData thing;
            try {
                server.register(new UserData(username, password, email));
            }catch (Exception e){
                return "username already taken";
            }
            state = State.LOGIN;


            return String.format("You registered as %s.", username);
        }
        return "incorrect number of parameters";
    }

    private int StringToNumber(String letter){
        return switch (letter){
            case "a" -> 1;
            case "b" -> 2;
            case "c" -> 3;
            case "d" -> 4;
            case "e" -> 5;
            case "f" -> 6;
            case "g" -> 7;
            case "h" -> 8;

            case "1" -> 8;
            case "2" -> 7;
            case "3" -> 6;
            case "4" -> 5;
            case "5" -> 4;
            case "6" -> 3;
            case "7" -> 2;
            case "8" -> 1;

            default -> 10;
        };
    }

    public String highlight(ArrayList<String> params){
        if (state == State.JOINED){
            if (params.size() == 1){
                String move = params.get(0);
                String[] test = move.split("");
                ChessBoard board = new ChessBoard();
                board.resetBoard();

                ChessPosition start = new ChessPosition(StringToNumber(test[1]), StringToNumber(test[0]));
                Collection<ChessMove> possible = board.getPiece(start).pieceMoves(board, start);

                ArrayList<ChessMove> newist = new ArrayList<>();
                newist.add(new ChessMove(start, start, null));
                newist.addAll(possible);
//                for (int i = 0; i < possible.size(); i++){
//                    newist.add(new ChessMove(start, start, null));
//                }
                possible.add(new ChessMove(start, start, null));
                Object[] highlight = possible.toArray();
                drawBoard(board, false, newist);
                drawBoard(board, true, newist);
            }else{
                return "need to add message";
            }
        }else{
            return "you must have joined or be observing a game to run this command";
        }
        return "didn't work";
    }

    public String redraw(){
        drawBoard();
    }

    public String help() {
        if (state == State.LOGOUT) {
            return """
                    register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    login <USERNAME> <PASSWORD> - to play chess
                    quit - playing chess
                    help - with possible commands
                    """;
        }else if (state == State.JOINED){
            return """
                    help - possible commands
                    Redraw - redraw the chess board
                    leave - leave the current game
                    move <Start Posistion><End Position> - make a move
                    resign - you forfeit the game
                    highlight <Piece Position> - highlight a piece possible moves
                    """;
        } else {
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
