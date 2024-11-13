import chess.*;


import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.ChessBoardPrint.drawBoard;
import static ui.ChessBoardPrint.drawTicTacToeBoard;
import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
//        System.out.println("♕ 240 Chess Client: " + piece);
//        public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new ClientInput().doGet(serverUrl);

//        new ClientInput().doGet(serverUrl).run();
        

//        out.print(ERASE_SCREEN);




//        drawBoard(out, true);
//        drawBoard(out, false);

    }
}