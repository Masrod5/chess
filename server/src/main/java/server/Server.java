package server;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import dataaccess.*;
import com.google.gson.Gson;

import model.*;
import service.Service;
import spark.*;

public class Server {

    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");


        // Register your endpoints and handle exceptions here.


        Spark.post("/session", this::login);
        Spark.post("/user", this::register);
        Spark.delete("session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        Spark.delete("/db", this::clear);
        Spark.exception(DataAccessException.class, this::FailerResponse);



        //This line initializes the server and can be removed once you have a functioning endpoint 
//        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }


    private String FailerResponse(DataAccessException exception, Request req, Response res){
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
        }
        if(Objects.equals(exception.getMessage(), "you are logged in")){
            res.status(500);
            request = new FailerResponse("Error: you are logged in");

            String json = serialize.toJson(request);

            res.body(json);
            return json;
        }
        if(Objects.equals(exception.getMessage(), "Game ID already exists")){
            res.status(500);
            request = new FailerResponse("Error: Game ID already exists");

            String json = serialize.toJson(request);

            res.body(json);
            return json;
        }


        return null;


    }

    private String clear(Request req, Response res) throws DataAccessException{
        Gson serialize = new Gson();
        new Service(userDAO, authDAO, gameDAO).clear();



        return "{}";
    }


    private String joinGame(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        JoinGameRequest request = serialize.fromJson(req.body(), JoinGameRequest.class);
        String auth = serialize.fromJson(req.headers("authorization"), String.class);

        new Service(userDAO, authDAO, gameDAO).joinGame(request, auth);



        return "{}";
    }

    private String createGame(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        GameData game = serialize.fromJson(req.body(), GameData.class);

        GameData newGame = new Service(userDAO, authDAO, gameDAO).createGame(req.headers("authorization"), game);



        return serialize.toJson(newGame);
    }


    private String listGames(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        String games = serialize.fromJson(req.headers("authorization"), String.class);


//        String body = serialize.toJson(games);

        List<GameData> list = new Service(userDAO, authDAO, gameDAO).listGames(games);

//        String thing = serialize.toJson(list);

        return serialize.toJson(list);
    }

    private String logout(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        String thing = serialize.fromJson(req.headers("authorization"), String.class);
//        String request = serialize.fromJson(req.body(), String.class);

        new Service(userDAO, authDAO, gameDAO).logout(thing);

        return "{}";
    }

    private String register(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        UserData request = serialize.fromJson(req.body(), UserData.class);

        AuthData auth = new Service(userDAO, authDAO, gameDAO).register(request);

        return serialize.toJson(auth);
    }

    private String login(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        LoginRequest request = serialize.fromJson(req.body(), LoginRequest.class);

        String something = authDAO.toString();

        AuthData auth = new Service(userDAO, authDAO, gameDAO).login(request);


        return serialize.toJson(auth);
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
