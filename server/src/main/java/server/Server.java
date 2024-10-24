package server;
import java.util.UUID;


import dataaccess.AuthDAO;
import com.google.gson.Gson;

import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import model.LoginRequest;
import service.UserService;
import spark.*;

public class Server {

    UserDAO userDAO = new MemoryUserDAO();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");


        // Register your endpoints and handle exceptions here.


        Spark.post("/session", this::login);



        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private String login(Request req, Response res) throws DataAccessException {
        Gson serialize = new Gson();
        LoginRequest request = serialize.fromJson(req.body(), LoginRequest.class);

        String username = request.username();
        new UserService(userDAO).login(request);



        return null;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
