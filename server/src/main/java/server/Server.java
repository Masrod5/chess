package server;
import java.util.Objects;
import java.util.UUID;


import dataaccess.*;
import com.google.gson.Gson;

import model.AuthData;
import model.FailerResponse;
import model.LoginRequest;
import model.UserData;
import service.UserService;
import spark.*;

public class Server {

    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");


        // Register your endpoints and handle exceptions here.


        Spark.post("/session", this::login);
        Spark.post("/user", this::register);
        Spark.delete("session", this::logout);
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
            request = new FailerResponse("already taken");

            String json = serialize.toJson(request);

            res.body(json);
            return json;
        }
        if(Objects.equals(exception.getMessage(), "bad request")){
            res.status(400);
            request = new FailerResponse("bad request");

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


        return null;


    }

    private String logout(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        String thing = serialize.fromJson(req.headers("authorization"), String.class);
        String request = serialize.fromJson(req.body(), String.class);

        new UserService(userDAO, authDAO).logout(thing);

        return "{}";
    }

    private String register(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        UserData request = serialize.fromJson(req.body(), UserData.class);

        AuthData auth = new UserService(userDAO, authDAO).register(request);

        return serialize.toJson(auth);
    }

    private String login(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        LoginRequest request = serialize.fromJson(req.body(), LoginRequest.class);

        AuthData auth = new UserService(userDAO, authDAO).login(request);

        return serialize.toJson(auth);
    }

//    private String listGames(Request req, Response res) {
//        Gson serialize = new Gson();
//
//    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
