package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

//import static EscapeSequences.*;
import static java.lang.Math.abs;
import static ui.EscapeSequences.*;

public class ChessBoardPrint {

//    private static ChessBoard realBoard = new ChessBoard();

    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;

    // Padded characters.
//    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";

    private static Random rand = new Random();



    public static void drawHeaders(PrintStream out,  int add) {

        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        out.print("   ");
        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            out.print(" " + headers[abs(boardCol + add)] + " ");
        }
        out.print("   ");

        out.print(SET_BG_COLOR_BLACK);
        out.println();
    }

    public static void drawBoard(PrintStream out, boolean reverse){
//        boolean reverse = true;
        int add = 0;
        if (reverse == true){
            add = -7;
        }

        String[] sideHeaders = {"8", "7", "6", "5", "4", "3", "2", "1"};

        out.print(SET_BG_COLOR_LIGHT_GREY); // print the top headers
        out.print(SET_TEXT_COLOR_BLACK);
        drawHeaders(out, add);

        for (int j = 0; j < 8; j++) {
            out.print(SET_BG_COLOR_LIGHT_GREY); // print the left side headers
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" " + sideHeaders[abs(j + add)] + " ");

            for (int i = 0; i < 8; i++) {
                if ((j+i)%2 == 0){
                    out.print(SET_BG_COLOR_WHITE);
                }else{
                    out.print(SET_BG_COLOR_BLACK);
                }
                printPiece(out, abs(j + add), abs(i + add));
            }

            out.print(SET_BG_COLOR_LIGHT_GREY); // print the right side headers
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" " + sideHeaders[abs(j + add)] + " ");

            out.print(SET_BG_COLOR_BLACK);
            out.println();
        }
        out.print(SET_BG_COLOR_LIGHT_GREY); // print the bottom headers
        drawHeaders(out, add);
    }

    private static void printPiece(PrintStream out, int row, int col){
        ChessBoard realBoard = new ChessBoard();
        realBoard.resetBoard();

        var tempPiece = realBoard.getPiece(new ChessPosition(row+1, col+1));
        if (tempPiece == null){
            out.print(EMPTY);
        }else {
            if (tempPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                out.print(SET_TEXT_COLOR_RED);
            }else {
                out.print(SET_TEXT_COLOR_YELLOW);
            }

            if (tempPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
                if (tempPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    out.print(WHITE_ROOK);
                } else {
                    out.print(BLACK_ROOK);
                }
            } else if (tempPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                if (tempPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    out.print(WHITE_KNIGHT);
                } else {
                    out.print(BLACK_KNIGHT);
                }
            }else if (tempPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                if (tempPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    out.print(WHITE_BISHOP);
                } else {
                    out.print(BLACK_BISHOP);
                }
            } else if (tempPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                if (tempPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    out.print(WHITE_QUEEN);
                } else {
                    out.print(BLACK_QUEEN);
                }
            }else if (tempPiece.getPieceType() == ChessPiece.PieceType.KING) {
                if (tempPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    out.print(WHITE_KING);
                } else {
                    out.print(WHITE_KING);
                }
            }else if (tempPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
                if (tempPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    out.print(WHITE_PAWN);
                } else {
                    out.print(BLACK_PAWN);
                }
            }else{
                out.print("aksfvpkadjbfgpviwubar");
            }
        }
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    public static void drawTicTacToeBoard(PrintStream out) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            drawRowOfSquares(out);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                // Draw horizontal row separator.
                drawHorizontalLine(out);
                setBlack(out);
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_PADDED_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                setWhite(out);

                if (squareRow == SQUARE_SIZE_IN_PADDED_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    printPlayer(out, rand.nextBoolean() ? X : O);
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
                }

                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                    // Draw vertical column separator.
//                    setRed(out);
//                    out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
                }

                setBlack(out);
            }

            out.println();
        }
    }

    private static void drawHorizontalLine(PrintStream out) {

        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_PADDED_CHARS +
                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_PADDED_CHARS;

        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_PADDED_CHARS; ++lineRow) {
//            setRed(out);
//            out.print(EMPTY.repeat(boardSizeInSpaces));
//
            setBlack(out);
//            out.println();
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }
}
