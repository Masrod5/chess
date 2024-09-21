package chess;

import java.util.Arrays;
import java.util.Objects;

//import static chess.ChessPiece.*;
//import static chess.ChessPiece.PieceType;
import static chess.ChessPiece.PieceType.ROOK;
import static chess.ChessPiece.PieceType.BISHOP;
import static chess.ChessPiece.PieceType.KNIGHT;
import static chess.ChessPiece.PieceType.KING;
import static chess.ChessPiece.PieceType.QUEEN;
import static chess.ChessPiece.PieceType.PAWN;


/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */

public class ChessBoard {

    private ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessPiece[][] getSquares() {
        return squares;
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }

    public void setSquares(ChessPiece[][] squares) {
        this.squares = squares;
    }

    public ChessBoard(ChessPiece[][] squares) {
        this.squares = squares;
    }

    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
//        System.out.print(squares.toString());
        squares[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ROOK);
        squares[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE, KNIGHT);
        squares[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE, BISHOP);
        squares[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, QUEEN);
        squares[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, KING);
        squares[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE, BISHOP);
        squares[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE, KNIGHT);
        squares[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ROOK);
        for (int i = 0; i < 8; i++){
            squares[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, PAWN);
        }

        for (int i = 0; i < 8; i++){
            squares[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, PAWN);
        }
        squares[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ROOK);
        squares[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK, KNIGHT);
        squares[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK, BISHOP);
        squares[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, QUEEN);
        squares[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, KING);
        squares[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK, BISHOP);
        squares[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK, KNIGHT);
        squares[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ROOK);

//        ChessBoard map;
//
//        addPiece(new ChessPosition(1,1),new ChessPiece(ChessGame.TeamColor.BLACK, ROOK));
//        addPiece(new ChessPosition(1,2),new ChessPiece(ChessGame.TeamColor.BLACK, KNIGHT));
//        addPiece(new ChessPosition(1,3),new ChessPiece(ChessGame.TeamColor.BLACK, BISHOP));
//        addPiece(new ChessPosition(1,4),new ChessPiece(ChessGame.TeamColor.BLACK, QUEEN));
//        addPiece(new ChessPosition(1,5),new ChessPiece(ChessGame.TeamColor.BLACK, KING));
//        addPiece(new ChessPosition(1,6),new ChessPiece(ChessGame.TeamColor.BLACK, BISHOP));
//        addPiece(new ChessPosition(1,7),new ChessPiece(ChessGame.TeamColor.BLACK, KNIGHT));
//        addPiece(new ChessPosition(1,8),new ChessPiece(ChessGame.TeamColor.BLACK, ROOK));
//
//
//        for (int i = 1; i <=  8; i++){
//            addPiece(new ChessPosition(7,i),new ChessPiece(ChessGame.TeamColor.WHITE, PAWN));
//        }
//        addPiece(new ChessPosition(8,1),new ChessPiece(ChessGame.TeamColor.WHITE, ROOK));
//        addPiece(new ChessPosition(8,2),new ChessPiece(ChessGame.TeamColor.WHITE, KNIGHT));
//        addPiece(new ChessPosition(8,3),new ChessPiece(ChessGame.TeamColor.WHITE, BISHOP));
//        addPiece(new ChessPosition(8,4),new ChessPiece(ChessGame.TeamColor.WHITE, QUEEN));
//        addPiece(new ChessPosition(8,5),new ChessPiece(ChessGame.TeamColor.WHITE, KING));
//        addPiece(new ChessPosition(8,6),new ChessPiece(ChessGame.TeamColor.WHITE, BISHOP));
//        addPiece(new ChessPosition(8,7),new ChessPiece(ChessGame.TeamColor.WHITE, KNIGHT));
//        addPiece(new ChessPosition(8,8),new ChessPiece(ChessGame.TeamColor.WHITE, ROOK));


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
