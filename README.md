# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```







actor Client
participant Server
participant Handler
participant Service
participant DataAccess
database db

entryspacing 0.9
group #navy Registration #white
Client -> Server: [POST] /user\n{"username":" ", "password":" ", "email":" "}
Server -> Handler: {"username":" ", "password":" ", "email":" "}
Handler -> Service: register(RegisterRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db:Find UserData by username
DataAccess --> Service: null
Service -> DataAccess:createUser(userData)
DataAccess -> db:Add UserData
Service --> DataAccess:createAuth(authData)
DataAccess -> db:Add AuthData
Service --> Handler: RegisterResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

group #orange Login #white
Client->Server:[POST] /session\n{username, password}
Server->Handler:{ "username":"", "password":"" }
Handler->Service:login (login request)
Service->DataAccess:getUser(username)
DataAccess->db:Find user data
Service<-DataAccess:user data
Service->DataAccess:create new auth token
DataAccess->db:add auth token
Handler<-Service:login result
Server<-Handler:{ "username":"", "authToken":"" }
Client<-Server:200\n{ "username":"", "authToken":"" }
end

group #green Logout #white
Client -> Server: [DELETE] /session\nauthToken
Server->Handler:{"auth token"}
Handler->Service:logout request
Service->DataAccess:get user by auth token
DataAccess->db:find user data
Service<-DataAccess:user data
Service->DataAccess:delete auth token
DataAccess->db:remove auth token
Handler<-Service:logout result
Server<-Handler:{}
Client<-Server:200 \n{}
end

group #red List Games #white
Client -> Server: [GET] /game\nauthToken
Server->Handler:{auth token}
Handler->Service:list games request
Service->DataAccess:get user info by auth token
DataAccess->db:find user data
Service<-DataAccess:user data
Handler<-Service:listGames
Server<-Handler:{ "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
Client<-Server:200\n{ "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
end

group #purple Create Game #white
Client -> Server: [POST] /game\nauthToken\n{gameName}
end

group #yellow Join Game #black
Client -> Server: [PUT] /game\nauthToken\n{playerColor, gameID}
end

group #gray Clear application #white
Client -> Server: [DELETE] /db
end