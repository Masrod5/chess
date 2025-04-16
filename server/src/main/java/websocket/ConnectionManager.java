package websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Set<Session>> connections = new ConcurrentHashMap<>();

    public void add(Integer gameID, String username, Session session) {

        if (connections.get(gameID) == null){
            connections.putIfAbsent(gameID, ConcurrentHashMap.newKeySet());
        }
        connections.get(gameID).add(session);
    }

    public void remove(Integer gameID, String username, Session session) throws IOException {
        connections.get(gameID).remove(session);
    }

    public void messageToRoot(Session session, String message) throws IOException {
        session.getRemote().sendString("WebSocket response: " + message);
    }

    public void messageToAll(Session session, String message, Integer gameID) throws IOException {
        var sessions = connections.get(gameID);
        for (var s : sessions){
            s.getRemote().sendString("WebSocket response: " + message);
        }
    }

    public void messageToAllButCurrent(Session session, String message, Integer gameID) throws IOException {
        var sessions = connections.get(gameID);
        for (var s : sessions){
            if(!s.equals(session)) {
                s.getRemote().sendString("WebSocket response: " + message);
            }
        }
    }



}
