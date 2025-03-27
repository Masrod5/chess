package serverfacade;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public class ServerFacade {

    private final String serverUrl;
    private String authToken;
    private State state = State.LOGOUT;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public AuthData register(UserData user) throws Exception {
        var path = "/user";
        state = State.LOGIN;
        AuthData test = this.makeRequest("POST", path, user, AuthData.class);
        authToken = test.authToken();
        return test;
    }

    public int createGame(String name) throws Exception {
        var path = "/game";
        GameName test = new GameName(name);
        GameData newGame = this.makeRequest("POST", path, test, GameData.class);

        return newGame.gameID();
    }

    public AuthData login(LoginRequest loginRequest) throws Exception {
        var path = "/session";
        state = State.LOGIN;
        AuthData test = this.makeRequest("POST", path, loginRequest, AuthData.class);
        authToken = test.authToken();
        return test;
    }

    public ListGames listGames() throws Exception{
        var path = "/game";

        return this.makeRequest("GET", path, null, ListGames.class);
    }

    public void clear() throws Exception {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null);

    }

    public void joinGame(int gameID, String color) throws Exception {
        var path = "/game";
        JoinGameRequest joining = new JoinGameRequest(gameID, color);
        this.makeRequest("PUT", path, joining, null);
    }

    public void leaveGame(int gameID, String color) throws Exception {
        var path = "/game";
        JoinGameRequest joining = new JoinGameRequest(gameID, color);
        this.makeRequest("PUT", path, joining, null);
    }

    public void logout() throws Exception {
        var path = "/session";
        state = State.LOGOUT;
        this.makeRequest("DELETE", path, null, null);
        authToken = null;
    }


    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (authToken != null){
                writeHeaders(authToken, http);
            }
            if (request != null) {
                writeBody(request, http);
            }


            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {

            throw new Exception("500" + ex.getMessage());
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws Exception {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private static void writeBody(Object request, HttpURLConnection http) throws Exception {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());  //
            }
        }
    }

    private void writeHeaders(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("authorization", authToken);
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws Exception {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            String body = new String(http.getErrorStream().readAllBytes());
            String message = body;
            if (body.charAt(0) == '{') {
                message = (String) new Gson().fromJson(body, Map.class).get("message");
            }
            throw new Exception("failure: " + message);
        }
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }



}
