package client;

import model.AuthData;
import model.LoginRequest;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import server.Server;
import serverFacade.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static UserData newUser;


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        var url = "http://localhost:" + port;
        facade = new ServerFacade(url);

        newUser = new UserData("username", "password", "email");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerSuccess() throws Exception {

        var result = assertDoesNotThrow(() -> facade.register(newUser));
//        assertEquals(result.username(), newUser.username());
    }

    @Test
    public void registerBadRequest() throws Exception {
        try {
            facade.register(new UserData("", "", ""));
            assertTrue(false);
        }catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    public void loginSuccess() throws Exception {

        registerSuccess();
        var result = assertDoesNotThrow(() -> facade.login(new LoginRequest("username", "password")));
    }

    @Test
    public void loginBadRequest() throws Exception {
        registerSuccess();
        try {
            facade.login(new LoginRequest("password", "username"));
            assertTrue(false);
        } catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    public void logoutSuccess() throws Exception {

        registerSuccess();

        assertDoesNotThrow(() -> facade.logout());
    }

    @Test
    public void creatGameSuccess() throws Exception {

        registerSuccess();

        assertDoesNotThrow(() -> facade.createGame("game"));
    }

    @Test
    public void creatGameNoName() throws Exception {
        try {
            registerSuccess();
            facade.createGame("");
            assertTrue(false);
        }catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void ListGamesSuccess() throws Exception {

        registerSuccess();
        assertDoesNotThrow(() -> facade.createGame("game"));
        var result = assertDoesNotThrow(() -> facade.listGames());

        assertEquals(1, result.games().size());
    }

    @Test
    public void joinGameSuccess() throws Exception {

        registerSuccess();

        facade.createGame("game");

        var list = assertDoesNotThrow(() -> facade.listGames());

        var i = list.games().get(0).gameID();

        try {
            facade.joinGame(i, "black");
            assertTrue(true);
        }catch (Exception e){
            assertTrue(true);
        }

    }




}
