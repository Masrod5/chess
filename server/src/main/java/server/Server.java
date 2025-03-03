package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.*;
import spark.*;
import service.Service;

import java.util.Objects;

public class Server {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public int run(int desiredPort) throws DataAccessException {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");


        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        // Register your endpoints and handle exceptions here.

        Spark.post("/user", this::register);
        Spark.delete("/db", this::clear);
        Spark.delete("/session", this::logout);
        Spark.post("/session", this::login);
        Spark.exception(DataAccessException.class, this::failerResponse);



        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private String login(Request request, Response response) throws DataAccessException {
        Gson serialize = new Gson();
        LoginRequest loginRequest = serialize.fromJson(request.body(), LoginRequest.class);

        new Service(userDAO, authDAO, gameDAO).login(loginRequest);
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
        } //(Objects.equals(exception.getMessage(), "you are logged in"))
        else {
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
