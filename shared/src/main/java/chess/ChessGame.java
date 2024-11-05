package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static chess.ChessPiece.PieceType.KING;

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

        ArrayList<ChessMove> newMoves = new ArrayList<ChessMove>();


        for (int i = 0; i < moves.size(); i++){
            ChessBoard tempBoard = clone(board);
            ChessGame tempGame = new ChessGame();
            tempGame.setBoard(tempBoard);

            var newPiece = tempBoard.getPiece(moves.get(i).getStartPosition());

            tempBoard.addPiece(moves.get(i).getStartPosition(), null);
            tempBoard.addPiece(moves.get(i).getEndPosition(), newPiece);

            if (!tempGame.isInCheck(newPiece.getTeamColor())) {
//                System.out.println("lol");
                newMoves.add(moves.get(i));
            }
        }


        return newMoves;

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

        /** must enter a start position for the move */
        if (board.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException();
        }


        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece.PieceType currPiece = board.getPiece(startPosition).getPieceType();
        ChessPiece piece = new ChessPiece(board.getPiece(startPosition).getTeamColor(), board.getPiece(startPosition).getPieceType());

        /** if there is no piece at the start position then throw an exception */
        if (board.getPiece(startPosition)== null){
            throw new InvalidMoveException();
        }


        Collection<ChessMove> valid = validMoves(startPosition);
        if (valid.contains(move) && piece.getTeamColor() == getTeamTurn()){
            board.addPiece(startPosition, null);  /** sets the piece that is moved off of to null. it is now empty. */


            /** if it is a pawn that is being promoted, figure out which piece to promote too. */
            if (currPiece == ChessPiece.PieceType.PAWN && (endPosition.getRow() == 8 || endPosition.getRow() == 1)) {
                if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                    board.addPiece(endPosition, new ChessPiece(whoseTurn, ChessPiece.PieceType.QUEEN));
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                    board.addPiece(endPosition, new ChessPiece(whoseTurn, ChessPiece.PieceType.ROOK));
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                    board.addPiece(endPosition, new ChessPiece(whoseTurn, ChessPiece.PieceType.KNIGHT));
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                    board.addPiece(endPosition, new ChessPiece(whoseTurn, ChessPiece.PieceType.BISHOP));
                }
            }else{ /** otherwise just move the piece like normal */
                board.addPiece(endPosition, new ChessPiece(whoseTurn, currPiece));

            }

        }else{
            throw new InvalidMoveException();
        }

        /** after each move change switch team turn */
        if (this.getTeamTurn() == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        }else{
            setTeamTurn(TeamColor.WHITE);
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
                if (board.getPiece(new ChessPosition(i, j)) != null && board.getPiece(new ChessPosition(i, j)).getPieceType() == KING) {
                    if (board.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor) {
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

    public Collection<ChessMove> allValidMoves(TeamColor color) {
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();



        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                if (board.getPiece(new ChessPosition(i, j)) != null) {
                    if (board.getPiece(new ChessPosition(i, j)).getTeamColor() == color) {
                        moves.addAll(board.getPiece(new ChessPosition(i, j)).pieceMoves(board, new ChessPosition(i, j)));
                    }
                }
            }
        }
//        System.out.println(whoseTurn.toString());


        ArrayList<ChessMove> newMoves = new ArrayList<ChessMove>();

        for (int i = 0; i < moves.size(); i++){
            ChessBoard tempBoard = clone(board);
            ChessGame tempGame = new ChessGame();
            tempGame.setBoard(tempBoard);

            ChessPiece tempPiece = tempBoard.getPiece(moves.get(i).getStartPosition());


            ChessPiece piece = new ChessPiece(tempPiece.getTeamColor(), tempPiece.getPieceType());

            tempBoard.addPiece(moves.get(i).getStartPosition(), null);
            tempBoard.addPiece(moves.get(i).getEndPosition(), piece);

            if (!tempGame.isInCheck(piece.getTeamColor())) {
//                System.out.println("lol");
                newMoves.add(moves.get(i));
            }
        }


        return newMoves;

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

        if (allValidMoves(teamColor).isEmpty()){
            return true;
        }

        /** call a method that checks the valid moves for every piece on your team
         * if no pieces have a valid move then you are in checkmate
         * note: it is only a valid move if you make the move and the king is not in check;
         */

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
        if (isInCheck(teamColor) == false && allValidMoves(teamColor).isEmpty()) {
            return true;
        }

        return false;
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
        for(int i = 1; i <= 8;i++){
            for (int j = 1; j <= 8; j++){
                newBoard.addPiece(new ChessPosition(i, j), board.getPiece(new ChessPosition(i, j)));
            }
        }

        return newBoard;
    }
}
