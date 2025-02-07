package chess;

import java.util.ArrayList;
import java.util.Collection;

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


            ChessPiece piece = tempBoard.getPiece(move.getStartPosition());

            tempBoard.addPiece(move.getStartPosition(), null);
            tempBoard.addPiece(move.getEndPosition(), piece);

            if (!isInCheck(piece.getTeamColor())){
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





    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        TeamColor otherTeamColor;
        Collection<ChessMove> otherMoves = new ArrayList<>();
        
        ChessPiece king = null;
        ChessPosition kingPosition = null;
        ChessBoard board = getBoard();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                ChessPosition curPosition = new ChessPosition(i+1, j+1);

                if (board.getPiece(curPosition) != null && board.getPiece(curPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(curPosition).getTeamColor() == teamColor){
                    king = board.getPiece(curPosition);
                    kingPosition = curPosition;
                    break;
                }
            }
            if (king != null){
                break;
            }
        }
        
        if (king.getTeamColor() == TeamColor.WHITE){
            otherTeamColor = TeamColor.BLACK;
        }else{
            otherTeamColor = TeamColor.WHITE;
        }

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                ChessPosition curPosition = new ChessPosition(i+1, j+1);
                ChessPiece curPiece = board.getPiece(curPosition);
                if (curPiece != null && curPiece.getTeamColor() == otherTeamColor){
                    otherMoves.addAll(curPiece.pieceMoves(board, curPosition));
                }
            }
        }
        
        for (ChessMove move : otherMoves){
            if (move.getEndPosition() == kingPosition){
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
        throw new RuntimeException("Not implemented");
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
}
