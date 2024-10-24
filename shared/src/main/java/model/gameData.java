package model;
import chess.*;


public record gameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {

}
