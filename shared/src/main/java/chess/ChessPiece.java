package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
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

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

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
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        int row;
        int col;

        if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN){
            /** don't forget to add promotion pieces if the hit the end */
            /** move forward one */
            if (board.getPiece(myPosition).pieceColor == ChessGame.TeamColor.WHITE) {
                row = myPosition.getRow();
                col = myPosition.getColumn();
                row += 1;
                if (row <= 8 && board.getPiece(new ChessPosition(row, col)) == null) {
                    if (row == 8) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.QUEEN));
                    } else {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                    }
                }

                /** initial move of two, check for piece right in front of it */
                row = myPosition.getRow();
                col = myPosition.getColumn();
                row += 1;
                if (row == 3 && board.getPiece(new ChessPosition(row, col)) == null) {
                    row += 1;
                    if (row == 4 && board.getPiece(new ChessPosition(row, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                    }
                }
                /** capture right */
                row = myPosition.getRow();
                col = myPosition.getColumn();
                row += 1;
                col += 1;
                if (row <= 8 && col <= 8 && board.getPiece(new ChessPosition(row, col)) != null) {
                    if (board.getPiece(new ChessPosition(row, col)).pieceColor != this.pieceColor) {
                        if (row == 8) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.QUEEN));
                        } else {
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                        }
                    }
                }
                /** capture left */
                row = myPosition.getRow();
                col = myPosition.getColumn();
                row += 1;
                col -= 1;
                if (row <= 8 && col >= 1 && board.getPiece(new ChessPosition(row, col)) != null) {
                    if (board.getPiece(new ChessPosition(row, col)).pieceColor != this.pieceColor) {
                        if (row == 8) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.QUEEN));
                        } else {
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                        }
                    }
                }
            }

            if (board.getPiece(myPosition).pieceColor == ChessGame.TeamColor.BLACK) {
                row = myPosition.getRow();
                col = myPosition.getColumn();
                row -= 1;
                if (row >= 1 && board.getPiece(new ChessPosition(row, col)) == null) {
                    if (row == 1) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.QUEEN));
                    } else {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                    }
                }

                /** initial move of two, check for piece right in front of it */
                row = myPosition.getRow();
                col = myPosition.getColumn();
                row -= 1;
                if (row == 6 && board.getPiece(new ChessPosition(row, col)) == null) {
                    row -= 1;
                    if (row == 5 && board.getPiece(new ChessPosition(row, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                    }
                }
                /** capture right */
                row = myPosition.getRow();
                col = myPosition.getColumn();
                row -= 1;
                col += 1;
                if (row >= 1 && col <= 8 && board.getPiece(new ChessPosition(row, col)) != null) {
                    if (board.getPiece(new ChessPosition(row, col)).pieceColor != this.pieceColor) {
                        if (row == 1) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.QUEEN));
                        } else {
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                        }
                    }
                }
                /** capture left */
                row = myPosition.getRow();
                col = myPosition.getColumn();
                row -= 1;
                col -= 1;
                if (row >= 1 && col >= 1 && board.getPiece(new ChessPosition(row, col)) != null) {
                    if (board.getPiece(new ChessPosition(row, col)).pieceColor != this.pieceColor) {
                        if (row == 1) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.QUEEN));
                        } else {
                            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                        }
                    }
                }
            }
        }

        if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            row += 2;
            col += 1;
            if (row <= 8 && col <= 8){
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            row += 2;
            col -= 1;
            if (row <= 8 && col >= 1){
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            row -= 2;
            col += 1;
            if (row >= 1 && col <= 8){
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            row -= 2;
            col -= 1;
            if (row >= 1 && col >= 1){
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }

            row = myPosition.getRow();
            col = myPosition.getColumn();
            row += 1;
            col += 2;
            if (row <= 8 && col <= 8){
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            row -= 1;
            col += 2;
            if (row >= 1 && col <= 8){
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            row += 1;
            col -= 2;
            if (row <= 8 && col >= 1){
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            row -= 1;
            col -= 2;
            if (row >= 1 && col >= 1){
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }

        }

        if (board.getPiece(myPosition).getPieceType() == PieceType.KING) {
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if (row < 8 && col < 8){
                row += 1;
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if (row < 8 && col > 1){
                row += 1;
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if (row > 1 && col > 1){
                row -= 1;
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if (row > 1 && col < 8){
                row -= 1;
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if (row < 8){
                row += 1;
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if (row > 1){
                row -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if (col > 1){
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            if (col < 8){
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){

                }else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
            }
        }

        if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN) {
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8 && col < 8){
                row += 1;
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8 && col > 1){
                row += 1;
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row > 1 && col > 1){
                row -= 1;
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row > 1 && col < 8){
                row -= 1;
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8){
                row += 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row > 1){
                row -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col > 1){
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col < 8){
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
        }

        if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8 && col < 8){
                row += 1;
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8 && col > 1){
                row += 1;
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row > 1 && col > 1){
                row -= 1;
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row > 1 && col < 8){
                row -= 1;
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
        }

        if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK){
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8){
                row += 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row > 1){
                row -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col > 1){
                col -= 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col < 8){
                col += 1;
                if (blocked(board, myPosition, moves, row, col) == true){
                    break;
                }
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
        }


        return moves;
    }

    /** blocked
     * returns true if the position is blocked
     * only adds the position to possible moves if it can be captured
     */
    private boolean blocked(ChessBoard board, ChessPosition myPosition, ArrayList<ChessMove> moves, int row, int col){
        if (board.getPiece(new ChessPosition(row, col)) != null){
            if (board.getPiece(new ChessPosition(row, col )).pieceColor != this.pieceColor){
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            }
            return true;
        }

        return false;
    }
}
