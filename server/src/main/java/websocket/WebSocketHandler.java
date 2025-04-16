package websocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import service.Service;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.management.Notification;
import java.io.IOException;

public class WebSocketHandler {
    public Service service;

    private final ConnectionManager sessions = new ConnectionManager();



    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {

        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

        switch (command.getCommandType()) {
            case CONNECT -> connect(command.getAuthToken(), command.getGameID(), session);
            case MAKE_MOVE -> {
                MakeMoveCommand move = new Gson().fromJson(message, MakeMoveCommand.class);
                makeMove(move.getAuthToken(), move.getGameID(), move.getMove());
            }
            case LEAVE -> leave(command.getAuthToken(), command.getGameID());
            case RESIGN -> resign(command.getAuthToken(), command.getGameID());
        }



    }
    private void connect(String authToken, Integer gameID, Session session) throws IOException, DataAccessException {
        String username = service.getAuth(authToken).username();

        sessions.add(gameID, username, session);

        LoadGameMessage message = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, service.getGame(gameID));

        session.getRemote().sendString("WebSocket load game response: " + message);

        NotificationMessage notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        sessions.messageToAllButCurrent(session, notification.toString(), gameID);



    }
    private void makeMove(String authToken, Integer gameID, ChessMove move) throws IOException {
    }

    private void leave(String authToken, Integer gameID) throws IOException {
    }

    private void resign(String authToken, Integer gameID) throws IOException {
    }




    }