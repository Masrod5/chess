package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import service.Service;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import java.io.IOException;

public class WebSocketHandler {
    public Service service;

    private final ConnectionManager sessions = new ConnectionManager();



    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {

        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

        switch (command.getCommandType()) {
            case CONNECT -> connect(command.getAuthToken(), command.getGameID());
            case MAKE_MOVE -> {
                MakeMoveCommand move = new Gson().fromJson(message, MakeMoveCommand.class);
                makeMove(move.getAuthToken(), move.getGameID(), move.getMove());
            }
            case LEAVE -> leave(command.getAuthToken(), command.getGameID());
            case RESIGN -> resign(command.getAuthToken(), command.getGameID());
        }



    }
    private void connect(String authToken, Integer gameID) throws IOException, DataAccessException {

    }
    private void makeMove(String authToken, Integer gameID, ChessMove move) throws IOException {
    }

    private void leave(String authToken, Integer gameID) throws IOException {
    }

    private void resign(String authToken, Integer gameID) throws IOException {
    }




    }