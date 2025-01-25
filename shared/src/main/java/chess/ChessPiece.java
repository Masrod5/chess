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


        if (pieceType == PieceType.ROOK){
            moves.addAll(addMoves(board, myPosition, 1, 0));
            moves.addAll(addMoves(board, myPosition, 0, 1));
            moves.addAll(addMoves(board, myPosition, -1, 0));
            moves.addAll(addMoves(board, myPosition, 0, -1));
        }

        if (pieceType == PieceType.BISHOP){
            moves.addAll(addMoves(board, myPosition, 1, 1));
            moves.addAll(addMoves(board, myPosition, -1, -1));
            moves.addAll(addMoves(board, myPosition, 1, -1));
            moves.addAll(addMoves(board, myPosition, -1, 1));
        }

        if (pieceType == PieceType.QUEEN || pieceType == PieceType.KING){
            moves.addAll(addMoves(board, myPosition, 1, 0));
            moves.addAll(addMoves(board, myPosition, 0, 1));
            moves.addAll(addMoves(board, myPosition, -1, 0));
            moves.addAll(addMoves(board, myPosition, 0, -1));

            moves.addAll(addMoves(board, myPosition, 1, 1));
            moves.addAll(addMoves(board, myPosition, -1, -1));
            moves.addAll(addMoves(board, myPosition, 1, -1));
            moves.addAll(addMoves(board, myPosition, -1, 1));
        }

        if (pieceType == PieceType.KNIGHT){
            moves.addAll(addMoves(board, myPosition, 2, 1));
            moves.addAll(addMoves(board, myPosition, 2, -1));

            moves.addAll(addMoves(board, myPosition, -2, 1));
            moves.addAll(addMoves(board, myPosition, -2, -1));

            moves.addAll(addMoves(board, myPosition, 1, 2));
            moves.addAll(addMoves(board, myPosition, -1, 2));

            moves.addAll(addMoves(board, myPosition, 1, -2));
            moves.addAll(addMoves(board, myPosition, -1, -2));
        }

        if (pieceType == PieceType.PAWN){
            int change;
            if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
                change = 1;
            }else{
                change = -1;
            }
            moves.addAll(addPawnMoves(board, myPosition, change, 0));
        }

        return moves;
    }

    public Collection<ChessMove> addMoves(ChessBoard board, ChessPosition myPosition, int rowChange, int colChange){
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();
        PieceType myType = board.getPiece(myPosition).getPieceType();

        while (row <= 8 && row > 0 && col <= 8 && col > 0){
            row += rowChange;
            col += colChange;
            if (row > 8 || col > 8 || row <= 0 || col <= 0){
                break;
            }

            ChessPosition endPosition = new ChessPosition(row, col);

            if (board.getPiece(endPosition) == null){
                moves.add(new ChessMove(myPosition, endPosition, null));
            }else if (board.getPiece(endPosition).getTeamColor() != myColor){
                moves.add(new ChessMove(myPosition, endPosition, null));
                break;
            }else{
                break;
            }
            if (myType == PieceType.KING || myType == PieceType.KNIGHT || myType == PieceType.PAWN){
                break;
            }
        }



        return moves;
    }

    public Collection<ChessMove> addPawnMoves(ChessBoard board, ChessPosition myPosition, int rowChange, int colChange){

        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        while (row <= 8 && row > 0 && col <= 8 && col > 0){
            row += rowChange;
            col += colChange;
            ChessPosition endPosition = new ChessPosition(row, col);

            if (board.getPiece(endPosition) == null){
                moves.addAll(pawnAdd(board, myPosition, endPosition));
            }
            endPosition = new ChessPosition(row, col+1);
            if (col+1 <=8 && isAlly(board, myPosition, endPosition) == false){
                moves.addAll(pawnAdd(board, myPosition, endPosition));
            }
            endPosition = new ChessPosition(row, col-1);
            if (col-1 >= 1 && isAlly(board, myPosition, endPosition) == false){
                moves.addAll(pawnAdd(board, myPosition, endPosition));
            }

            if (rowChange > 0){
                if (row == 3){
                    if (board.getPiece(new ChessPosition(row+1, col)) == null){
                        moves.add(new ChessMove(myPosition, new ChessPosition(row+1, col), null));
                    }
                }
            }else{
                if (row == 6 && board.getPiece(new ChessPosition(row, col)) == null){
                    if (board.getPiece(new ChessPosition(row-1, col)) == null){
                        moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col), null));
                    }
                }
            }
            break;
        }

        return moves;
    }

    public Collection<ChessMove> pawnAdd(ChessBoard board, ChessPosition startPosition, ChessPosition endPosition){
        Collection<ChessMove> moves = new ArrayList<>();
        ChessGame.TeamColor myColor = board.getPiece(startPosition).getTeamColor();

        if ((endPosition.getRow() == 8 && myColor == ChessGame.TeamColor.WHITE) || (endPosition.getRow() == 1 && myColor == ChessGame.TeamColor.BLACK)){
            moves.add(new ChessMove(startPosition, endPosition, PieceType.ROOK));
            moves.add(new ChessMove(startPosition, endPosition, PieceType.KNIGHT));
            moves.add(new ChessMove(startPosition, endPosition, PieceType.BISHOP));
            moves.add(new ChessMove(startPosition, endPosition, PieceType.QUEEN));
        }else {
            moves.add(new ChessMove(startPosition, endPosition, null));
        }

        return moves;
    }

    public boolean isAlly(ChessBoard board, ChessPosition startPosition, ChessPosition endPosition){
        ChessGame.TeamColor myTeam = board.getPiece(startPosition).getTeamColor();
        if (board.getPiece(endPosition) == null){
            return true;
        }
        if (endPosition.getColumn() < 1 || endPosition.getColumn() > 8){
            return true;
        }
        ChessGame.TeamColor other = board.getPiece(endPosition).getTeamColor();

        if (myTeam == other){
            return true;
        }else{
            return false;
        }
    }



}