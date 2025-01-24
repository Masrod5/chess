package chess;

import jdk.jshell.Snippet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return  "{" +
                 pieceColor +
                ", " + type +
                '}';
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        PieceType pieceType = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn())).getPieceType();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        int curRow = myRow;
        int curCol = myCol;
        PieceType myType = board.getPiece(myPosition).getPieceType();

        if (pieceType == PieceType.ROOK){
            curRow++;
            if (curRow < 0 || curRow > 7){

            }else if (board.getPiece(new ChessPosition(curRow, curCol)) == null){
                moves.add(new ChessMove(myPosition, new ChessPosition(curRow, curCol), null));
            }else{
                if (board.getPiece(new ChessPosition(curRow, curCol)).getPieceType() != myType) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(curRow, curCol), null));
                }
            }

        }


        return moves;
    }
}


