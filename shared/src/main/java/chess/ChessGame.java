package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board = new ChessBoard();
    private TeamColor whoseTurn = TeamColor.WHITE;

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
        Collection<ChessMove> moves = new ArrayList<ChessMove>();
        ChessPiece myPiece = getBoard().getPiece(startPosition);
        moves = myPiece.pieceMoves(getBoard(), startPosition);

        ArrayList<ChessMove> newMoves = new ArrayList<>();

        for (ChessMove move : moves){
            ChessBoard tempBoard = clone(board);
            ChessGame tempGame = new ChessGame();
            tempGame.setBoard(tempBoard);


            ChessPiece piece = tempBoard.getPiece(move.getStartPosition());

            tempGame.getBoard().addPiece(move.getStartPosition(), null);
            tempGame.getBoard().addPiece(move.getEndPosition(), piece);

//            tempBoard.addPiece(move.getStartPosition(), null);
//            tempBoard.addPiece(move.getEndPosition(), piece);


            if (!tempGame.isInCheck(piece.getTeamColor())){
                newMoves.add(move);
            }

        }





        return newMoves;
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


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());

        if (piece == null){
            throw new InvalidMoveException();
        }

        if (!getTeamTurn().equals(piece.getTeamColor())){
            throw new InvalidMoveException();
        }

        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());

        ChessPiece.PieceType promotion = move.getPromotionPiece();

        if (validMoves.contains(move)){
            board.addPiece(move.getStartPosition(), null);
            if (promotion != null){
                board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), promotion));
            }else {
                board.addPiece(move.getEndPosition(), piece);
            }
        }else{
            throw new InvalidMoveException();
        }

        if (piece.getTeamColor() == TeamColor.WHITE){
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

        ChessPosition kingPosition = null;

        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING){
                    if (piece.getTeamColor() == teamColor){
                        kingPosition = new ChessPosition(i, j);
                    }
                }
            }
        }

        for(ChessMove move : opponentMoves){
            if (move.getEndPosition().equals(kingPosition)){
                return true;
            }
        }

        return false;

    }
    

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        ChessPosition kingPosition = null;

        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING){
                    if (piece.getTeamColor() == teamColor){
                        kingPosition = new ChessPosition(i, j);
                    }
                }
            }
        }


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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && whoseTurn == chessGame.whoseTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, whoseTurn);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", whoseTurn=" + whoseTurn +
                '}';
    }
}
