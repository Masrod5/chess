package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor whoseTurn = TeamColor.WHITE;
    private ChessBoard board = new ChessBoard();


    public ChessGame() {
        board.resetBoard();
     }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return whoseTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        whoseTurn = team;
    }


    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        moves.addAll(board.getPiece(startPosition).pieceMoves(board, startPosition));


        return moves;

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        /** you must gather the piece at the start position
         * then the end position becomes null because it just moved off of it
         * then add a new piece that is the same type at the end position*/
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece.PieceType promoPiece = move.getPromotionPiece();
        ChessPiece.PieceType currPiece = board.getPiece(startPosition).getPieceType();
        ChessPiece piece = new ChessPiece(board.getPiece(startPosition).getTeamColor(), board.getPiece(startPosition).getPieceType());

        Collection<ChessMove> valid = board.getPiece(startPosition).pieceMoves(board, startPosition);
        if (valid.contains(move) && piece.getTeamColor() == whoseTurn){
            board.addPiece(startPosition, null);
            board.addPiece(endPosition, new ChessPiece(whoseTurn, currPiece));
        }else{
            throw new InvalidMoveException();
        }


    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessMove> opponentMoves = new ArrayList<ChessMove>();
        /** list of all the chessMoves that are possible for the other team */
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                if (board.getPiece(new ChessPosition(i, j)) != null) {
                    if (board.getPiece(new ChessPosition(i, j)).getTeamColor() != teamColor) {
                        opponentMoves.addAll(board.getPiece(new ChessPosition(i, j)).pieceMoves(board, new ChessPosition(i, j)));
                    }
                }
            }
        }


        ArrayList<ChessPosition> positions = new ArrayList<>();

        /** convert the list of chessMoves possible for the other team to chessPositions */
        for (int i = 0; i < opponentMoves.size(); i++){
            positions.add(opponentMoves.get(i).getEndPosition());
        }

        ChessPosition king = null;
        /** finds the position of your king */
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                if (board.getPiece(new ChessPosition(i, j)) != null) {
                    if (board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor) {
                        king = new ChessPosition(i, j);
                    }
                }
            }
        }

        /** if the possible moves of the other team land on the king, you are in check */
        if (positions.contains(king)) {
            return true;
        }
        return false;
    }

    public Collection<ChessMove> allValidMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();



        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                if (board.getPiece(new ChessPosition(i, j)) != null) {
                    if (board.getPiece(new ChessPosition(i, j)).getTeamColor() == whoseTurn) {
                        moves.addAll(board.getPiece(new ChessPosition(i, j)).pieceMoves(board, new ChessPosition(i, j)));
                    }
                }
            }
        }
        System.out.println(whoseTurn.toString());

//        moves.addAll(this.board.getPiece(startPosition).pieceMoves(board, startPosition));

        return moves;

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(whoseTurn) == false) {
            return false;
        }

        /** call a method that checks the valid moves for every piece on your team
         * if no pieces have a valid move then you are in checkmate
         * note: it is only a valid move if you make the move and the king is not in check;
         */

//        ChessPosition king = null;
//
//        for (int i = 1; i <= 8; i++) {
//            for (int j = 1; j <= 8; j++) {
//                if (board.getPiece(new ChessPosition(i, j)) != null) {
//                    if (board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor) {
//                        king = new ChessPosition(i, j);
//                    }
//                }
//            }
//        }
//
//        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
//
//
//        moves.addAll(board.getPiece(king).pieceMoves(board, king));

//        ChessBoard temp = ;



//        ChessPosition start = moves.get(0).getStartPosition();
//        ChessPosition end = moves.get(0).getEndPosition();

//        makeMove(new ChessMove(start, end, null));

//        moves clone();
//
//        makeMove(moves[0]);









        return false;
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    public ChessBoard clone(ChessBoard board){
        ChessBoard newBoard = new ChessBoard();
        for(int i = 0; i < 8;i++){
            for (int j = 0; j < 8; j++){
                newBoard.addPiece(new ChessPosition(i, j), board.getPiece(new ChessPosition(i, j)));
            }
        }


        return newBoard;
    }
}
