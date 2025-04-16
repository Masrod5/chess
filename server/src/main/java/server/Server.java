package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import spark.*;
import service.Service;
import websocket.WebSocketHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Server {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        try {
            userDAO = new MySQLUserDAO();
            gameDAO = new MySQLGameDAO();
            authDAO = new MySQLAuthDAO();

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }


        // Register your endpoints and handle exceptions here.

        Spark.webSocket("/ws", WebSocketHandler.class); //
        Spark.post("/user", this::register);
        Spark.delete("/db", this::clear);
        Spark.delete("/session", this::logout);
        Spark.post("/session", this::login);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        Spark.exception(DataAccessException.class, this::failerResponse);

        //This line initializes the server and can be removed once you have a functioning endpoint 
//        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private String joinGame(Request request, Response response) throws DataAccessException {
        Gson serialize = new Gson();
        JoinGameRequest joinRequest = serialize.fromJson(request.body(), JoinGameRequest.class);
        String auth = request.headers("authorization");

        new Service(userDAO, authDAO, gameDAO).joinGame(joinRequest, auth);

        return "{}";
    }

    private String createGame(Request request, Response response) throws DataAccessException {
        Gson serialize = new Gson();
        GameData game = serialize.fromJson(request.body(), GameData.class);

        GameData newGame = new Service(userDAO, authDAO, gameDAO).createGame(request.headers("authorization"), game);

        return serialize.toJson(newGame);
    }

    private String listGames(Request request, Response response) throws DataAccessException {
        Gson serialize = new Gson();
        String auth = request.headers("authorization");

        ListGames games = new Service(userDAO, authDAO, gameDAO).listGames(auth);

        return serialize.toJson(games);
    }

    private String login(Request request, Response response) throws DataAccessException {
        Gson serialize = new Gson();
        LoginRequest loginRequest = serialize.fromJson(request.body(), LoginRequest.class);

        AuthData auth = new Service(userDAO, authDAO, gameDAO).login(loginRequest);

        return serialize.toJson(auth);
    }

    private String logout(Request request, Response response) throws DataAccessException {
        Gson serialize = new Gson();
        String header = serialize.fromJson(request.headers("authorization"), String.class);
        new Service(userDAO, authDAO, gameDAO).logout(header);

        return "{}";
    }

    private String clear(Request request, Response response) throws DataAccessException {
        new Service(userDAO, authDAO, gameDAO).clear();
        return "{}";
    }

    private String failerResponse(DataAccessException exception, Request req, Response res){
        Gson serialize = new Gson();
        FailerResponse request = serialize.fromJson(req.body(), FailerResponse.class);

        if(Objects.equals(exception.getMessage(), "already taken")){
            res.status(403);
            request = new FailerResponse("Error: already taken");

            String json = serialize.toJson(request);

            res.body(json);
            return json;
        }
        if(Objects.equals(exception.getMessage(), "bad request")){
            res.status(400);
            request = new FailerResponse("Error: bad request");

            String json = serialize.toJson(request);

            res.body(json);
            return json;
        }
        if(Objects.equals(exception.getMessage(), "unauthorized")){
            res.status(401);
            request = new FailerResponse("Error: unauthorized");

            String json = serialize.toJson(request);

            res.body(json);
            return json;
        }
        if(Objects.equals(exception.getMessage(), "you are not logged in")){
            res.status(500);
            request = new FailerResponse("Error: you are not logged in");

            String json = serialize.toJson(request);

            res.body(json);
            return json;
        } else {
            res.status(500);
            request = new FailerResponse(exception.getMessage());

            String json = serialize.toJson(request);

            res.body(json);
            return json;
        }
    }

    private String register(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        UserData request = serialize.fromJson(req.body(), UserData.class);

        AuthData auth = new Service(userDAO, authDAO, gameDAO).register(request);

        return serialize.toJson(auth);
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
