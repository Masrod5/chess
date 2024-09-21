package chess;

import org.w3c.dom.ranges.Range;

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

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

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
        PAWN;

        @Override
        public String toString() {
            return "PieceType{}";
        }
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
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece.PieceType check = board.getPiece(myPosition).getPieceType();

        if (check == PieceType.PAWN) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            if (this.getTeamColor() == ChessGame.TeamColor.WHITE){
                if (row < 8 && board.getPiece(new ChessPosition(row+1, col))==null){
                    if (row+1 == 8) {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col), PieceType.KNIGHT));
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col), PieceType.BISHOP));
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col), PieceType.ROOK));
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col), PieceType.QUEEN));
                    }else{
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row+1, col), null));
                    }
                }
                if (row == 2 && board.getPiece(new ChessPosition(row+2, col))==null && board.getPiece(new ChessPosition(row+2, col))==null) {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row+2, col), null));
                }
                if (col > 1) {
                    ChessPiece guy = board.getPiece(new ChessPosition(row + 1, col - 1));
                    if (guy != null && guy.getTeamColor() != this.getTeamColor()) {
//                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col - 1), null));
                        if (row+1 == 8) {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col-1), PieceType.KNIGHT));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col-1), PieceType.BISHOP));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col-1), PieceType.ROOK));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col-1), PieceType.QUEEN));
                        }else{
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row+1, col-1), null));
                        }
                    }
                }
                if (col < 8) {
                    ChessPiece guy = board.getPiece(new ChessPosition(row + 1, col + 1));
                    if (guy != null && guy.getTeamColor() != this.getTeamColor()) {
//                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col + 1), null));
                        if (row+1 == 8) {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col+1), PieceType.KNIGHT));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col+1), PieceType.BISHOP));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col+1), PieceType.ROOK));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col+1), PieceType.QUEEN));
                        }else{
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row+1, col+1), null));
                        }
                    }
                }
//                if (row+1 == 8) {
//                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col + 1), PieceType.KNIGHT));
//                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col + 1), PieceType.BISHOP));
//                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col + 1), PieceType.ROOK));
//                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row + 1, col + 1), PieceType.QUEEN));
//                }
            }

            if (this.getTeamColor() == ChessGame.TeamColor.BLACK){
                row = myPosition.getRow();
                col = myPosition.getColumn();
                if (row > 1 && board.getPiece(new ChessPosition(row-1, col))==null){ /** basic move */
//                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row-1, col), null));
                    if (row-1 == 1) {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col), PieceType.KNIGHT));
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col), PieceType.BISHOP));
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col), PieceType.ROOK));
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col), PieceType.QUEEN));
                    }else{
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row-1, col), null));
                    }
                }
                if (row == 7 && board.getPiece(new ChessPosition(row-2, col))==null && board.getPiece(new ChessPosition(row-1, col))==null) { /** initial move of 2 */
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row-2, col), null));
                }

                if (col > 1) {
                    ChessPiece guy = board.getPiece(new ChessPosition(row - 1, col - 1));
                    if (col > 1 && guy != null && guy.getTeamColor() != this.getTeamColor()) { /** capture down left */
//                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col - 1), null));
                        if (row-1 == 1) {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col-1), PieceType.KNIGHT));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col-1), PieceType.BISHOP));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col-1), PieceType.ROOK));
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col-1), PieceType.QUEEN));
                        }else{
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row-1, col-1), null));
                        }
                    }
                }
                if (col < 8) {
                    ChessPiece guy = board.getPiece(new ChessPosition(row - 1, col + 1));
                    if (col < 8 && guy != null && guy.getTeamColor() != this.getTeamColor()) { /** capture down right */
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row - 1, col + 1), null));
                    }
                }

            }

        }

        if (check == PieceType.KNIGHT) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            row = myPosition.getRow()+2; /** up left */
            col = myPosition.getColumn()-1;
            if (row <= 8 && col >= 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()+2; /** up right */
            col = myPosition.getColumn()+1;
            if (row <= 8 && col <= 8 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()-2; /** down left */
            col = myPosition.getColumn()-1;
            if (row >= 1 && col >= 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()-2; /** down right */
            col = myPosition.getColumn()+1;
            if (col <= 8 && row >= 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()-1; /** right down */
            col = myPosition.getColumn()+2;
            if (col <= 8 && row >= 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()+1; /** right up */
            col = myPosition.getColumn()+2;
            if (row <= 8 && col <= 8 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()-1; /** left down */
            col = myPosition.getColumn()-2;
            if (row >= 1 && col >= 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()+1; /** left up */
            col = myPosition.getColumn()-2;
            if (row <= 8 && col >= 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }

        }

        if (check == PieceType.KING) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            row = myPosition.getRow()+1;
            col = myPosition.getColumn()+1;
            if (row < 8 && col < 8 && row > 1 && col > 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn()+1;
            if (row < 8 && col < 8 && row > 1 && col > 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()-1;
            col = myPosition.getColumn()+1;
            if (row < 8 && col < 8 && row > 1 && col > 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()-1;
            col = myPosition.getColumn();
            if (row < 8 && col < 8 && row > 1 && col > 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()-1;
            col = myPosition.getColumn()-1;
            if (row < 8 && col < 8 && row > 1 && col > 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn()-1;
            if (row < 8 && col < 8 && row > 1 && col > 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()+1;
            col = myPosition.getColumn()-1;
            if (row < 8 && col < 8 && row > 1 && col > 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow()+1;
            col = myPosition.getColumn();
            if (row < 8 && col < 8 && row > 1 && col > 1 && blocked(board, row, col, moves, myPosition) == false){
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }


        }

        if (check == PieceType.QUEEN) { /** moves for the BISHOP */
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (col < 8 && row < 8){ /** posisble moves to the top right */
                col += 1;
                row += 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col < 8 && row > 1){ /** posisble moves to the bottom right */
                col += 1;
                row -= 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col > 1 && row > 1){ /** posisble moves to the bottom left */
                col -= 1;
                row -= 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8 && col > 1){ /** posisble moves to the top left */
                col -= 1;
                row += 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col < 8){ /** posisble moves to the right */
                col += 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }

            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col > 1){ /** posisble moves to the left */
                col -= 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }

            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row > 1){ /** posisble moves to the down */
                row -= 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }

            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8){ /** possible moves to the up */
                row += 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
        }

        if (check == PieceType.BISHOP) { /** moves for the BISHOP */
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (col < 8 && row < 8 && col > 1 && row > 1){ /** posisble moves to the top right */
                col += 1;
                row += 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col < 8 && row < 8 && col > 1 && row > 1){ /** posisble moves to the bottom right */
                col += 1;
                row -= 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col < 8 && row < 8 && col > 1 && row > 1){ /** posisble moves to the bottom left */
                col -= 1;
                row -= 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col < 8 && row < 8 && col > 1 && row > 1){ /** posisble moves to the top left */
                col -= 1;
                row += 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
        }

        if (check == PieceType.ROOK) { /** moves for the ROOK */
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (col < 8){ /** posisble moves to the right */
                col += 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }

            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (col > 1){ /** posisble moves to the left */
                col -= 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }

            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row > 1){ /** posisble moves to the down */
                row -= 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }

            row = myPosition.getRow();
            col = myPosition.getColumn();
            while (row < 8){ /** possible moves to the up */
                row += 1;
                if (blocked(board, row, col, moves, myPosition) == true){
                    break;
                } /** break out of the loop if blocked */
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
        } /** moves for the ROOK */


        System.out.print(moves.toString()); /** print the possible moves */
        return moves;
    }
    /**
        blocked will check if the space is blocked and if the piece can be captured, it will add that piece to possible moves.
    */
    private boolean blocked(ChessBoard board, int row, int col, ArrayList<ChessMove> moves, ChessPosition myPosition) {
        if (board.getPiece(new ChessPosition(row, col)) != null){
            if (board.getPiece(new ChessPosition(row, col)).getTeamColor() != this.getTeamColor()) {
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            return true;
        }
        return false;
    }

    private boolean checkinging() {
        return true;
    }


}
