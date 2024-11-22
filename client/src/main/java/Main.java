import chess.*;


import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.ChessBoardPrint.drawBoard;
import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) throws IOException {

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl).run();

    }
}