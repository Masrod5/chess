package ui;

import chess.*;

import javax.swing.plaf.basic.BasicLabelUI;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

//import static EscapeSequences.*;
import static java.lang.Math.abs;
import static ui.EscapeSequences.*;

public class PrintBoard {

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

    public static void drawBoard(ChessBoard board, boolean reverse, ArrayList<ChessMove> highlight){
        PrintStream out = System.out;
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

                boolean green = false;
                boolean yellow = false;

                if (highlight != null) {
                    for (ChessMove thing : highlight) {

                        if (abs(thing.getEndPosition().getRow()-1+add) == j && abs(thing.getEndPosition().getColumn()-1+add) == i) {
                            int ha = thing.getEndPosition().getRow();
                            green = true;
                            if (abs(thing.getStartPosition().getRow()-1+add) == j && abs(thing.getStartPosition().getColumn()-1+add) == i) {
                                yellow = true;
                            }
                            break;
                        }
                    }
                }

                if ((j+i)%2 == 0){
                    if (yellow == true) {
                        out.print(SET_BG_COLOR_WHITE);
                    } else if (green == true){
                        out.print(SET_BG_COLOR_GREEN);
                    }else {
                        out.print(SET_BG_COLOR_BLUE);
                    }
                }else{
                    if (yellow == true) {
                        out.print(SET_BG_COLOR_WHITE);
                    } else if (green == true){
                        out.print(SET_BG_COLOR_DARK_GREEN);
                    }else {
                        out.print(SET_BG_COLOR_DARK_GREY);
                    }
                }
                printPiece(out, abs(j + add), abs(i + add), board);
            }

            out.print(SET_BG_COLOR_LIGHT_GREY); // print the right side headers
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" " + sideHeaders[abs(j + add)] + " ");

            out.print(SET_BG_COLOR_BLACK);
            out.println();
        }
        out.print(SET_BG_COLOR_LIGHT_GREY); // print the bottom headers
        drawHeaders(out, add);
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

    private static void printPiece(PrintStream out, int row, int col, ChessBoard realBoard){
//        realBoard.resetBoard();

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
            out.print(SET_BG_COLOR_BLACK);
        }
    }
}