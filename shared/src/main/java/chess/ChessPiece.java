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
        int i = 0;
        i++;

        Collection<ChessMove> moves = new ArrayList<>();
        PieceType pieceType = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn())).getPieceType();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        int curRow = myRow;
        int curCol = myCol;
//        PieceType myType = board.getPiece(myPosition).getPieceType();

        if (pieceType == PieceType.ROOK){
            moves.addAll(addMoves(board, myPosition, 1, 0));

        }


        return moves;
    }

    public Collection<ChessMove> addMoves(ChessBoard board, ChessPosition myPosition, int rowChange, int colChange){
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();

        while (row <= 8 && row > 0 && col <= 8 && col > 0){
            row += rowChange;
            col += colChange;
            if (row > 8 || col > 8 || row < 0 || col  < 0){
                break;
            }

            ChessPosition endPosition = new ChessPosition(row, col);

            if (board.getPiece(endPosition) == null){
                moves.add(new ChessMove(myPosition, endPosition, null));
            }else if (board.getPiece(endPosition).getTeamColor() != myColor){
                moves.add(new ChessMove(myPosition, endPosition, null));
            }else{
                break;
            }
        }



        return moves;
    }



}