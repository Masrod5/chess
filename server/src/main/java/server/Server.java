package server;

import com.google.gson.Gson;
import dataaccess.*;
import record.*;
import spark.*;

public class Server {

    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.post("/user", this::register);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private String register(Request req, Response res) {
        Gson serialize = new Gson();
        UserData request = serialize.fromJson(req.body(), UserData.class);

        AuthData auth = Service(userDAO, )

        return null;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
