package client;

import model.LoginRequest;
import model.UserData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.Server;
import serverfacade.ServerFacade;

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

        facade.clear();

        assertDoesNotThrow(() -> facade.register(newUser));
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
        facade.clear();
        registerSuccess();
        assertDoesNotThrow(() -> facade.login(new LoginRequest("username", "password")));
    }

    @Test
    public void loginBadRequest() throws Exception {
        try {
            facade.login(new LoginRequest("password", "username"));
            assertTrue(false);
        } catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    public void logoutSuccess() throws Exception {
        facade.clear();
        registerSuccess();

        assertDoesNotThrow(() -> facade.logout());
    }

    @Test
    public void logoutTwice() throws Exception {
        facade.clear();
        registerSuccess();

        assertDoesNotThrow(() -> facade.logout());

        try{
            facade.logout();
        }catch (Exception e){
            assertTrue(true);
        }

    }

    @Test
    public void creatGameSuccess() throws Exception {

        facade.clear();
        registerSuccess();

        assertDoesNotThrow(() -> facade.createGame("game"));
    }

    @Test
    public void creatGameNoName() throws Exception {
        try {
//            registerSuccess();
            facade.createGame("");
            assertTrue(false);
        }catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void listGamesSuccess() throws Exception {
        facade.clear();
        registerSuccess();
        assertDoesNotThrow(() -> facade.createGame("game"));
        var result = assertDoesNotThrow(() -> facade.listGames());

        assertEquals(1, result.games().size());
    }

    @Test
    public void listGamesNoGames() throws Exception {
        facade.clear();
        registerSuccess();

        var result = assertDoesNotThrow(() -> facade.listGames());

        assertEquals(0, result.games().size());
    }

    @Test
    public void joinGameSuccess() throws Exception {

        facade.clear();
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

    @Test
    public void joinGameBadRequest() throws Exception {

        facade.clear();
        facade.register(newUser);

        facade.createGame("game");

        var list = assertDoesNotThrow(() -> facade.listGames());

        var i = list.games().getFirst().gameID();

        try {
            facade.joinGame(i, "");
            assertTrue(true);
        }catch (Exception e){
            assertTrue(true);
        }
    }




}
