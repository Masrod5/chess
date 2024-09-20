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

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return PieceType.ROOK;
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
//        ChessMove move = new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn()), PieceType.ROOK);
        if (this.getPieceType() == PieceType.ROOK) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
//            int check =
            while (col < 8){ /** posisble moves to the right */
                col += 1;
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));

            }
            col = myPosition.getColumn();
            while (col > 1){ /** posisble moves to the left */
                col -= 1;
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
            col = myPosition.getColumn();
            while (row > 1){ /** posisble moves to the down */
                row -= 1;
                if(board.getPiece(new ChessPosition(row, col)) != null){
                    break;
                }
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
                ChessBoard man = board;

            }
            row = myPosition.getRow();
            while (row < 8){ /** posisble moves to the left */
                row += 1;
                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(row, col), null));
            }
        }

        return moves;
    }
}
