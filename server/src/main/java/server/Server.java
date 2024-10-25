package server;
import java.util.UUID;


import dataaccess.*;
import com.google.gson.Gson;

import model.AuthData;
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



        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
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

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
