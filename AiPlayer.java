import java.util.*;

public class AiPlayer {

    public int d_level;
    public GameBoard gameState;

    
    public AiPlayer(int d, GameBoard curr_Game) {
        this.d_level = d;
        this.gameState = curr_Game;
    }
    
    public int findBestPlay(GameBoard curr_Game) throws CloneNotSupportedException {
        int move = Maxconnect4.INVALID;
        if (curr_Game.getCurrentTurn() == Maxconnect4.ONE) {
            int maxval = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.NOFCOLS; i++) {
                if (curr_Game.isValidPlay(i)) {
                    GameBoard next_state = new GameBoard(curr_Game.getGameBoard());
                    next_state.playPiece(i);
                    int value = max_value(next_state, d_level, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (maxval > value) {
                        move = i;
                        maxval = value;
                    }
                }
            }
        } else {
            int minval = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.NOFCOLS; i++) {
                if (curr_Game.isValidPlay(i)) {
                    GameBoard next_state = new GameBoard(curr_Game.getGameBoard());
                    next_state.playPiece(i);
                    int value = min_value(next_state, d_level, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (minval < value) {
                        move = i;
                        minval = value;
                    }
                }
            }
        }
        return move;
    }
    private int min_value(GameBoard gb, int d_level, int a_value, int b_value)
        throws CloneNotSupportedException {        
        if (!gb.isBoardFull() && d_level > 0) {
            int maxval = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.NOFCOLS; i++) {
                if (gb.isValidPlay(i)) {
                    GameBoard next_move = new GameBoard(gb.getGameBoard());
                    next_move.playPiece(i);
                    int value = max_value(next_move, d_level - 1, a_value, b_value);
                    if (maxval > value) {
                        maxval = value;
                    }
                    if (maxval <= a_value) {
                        return maxval;
                    }
                    if (b_value > maxval) {
                        b_value = maxval;
                    }
                }
            }
            return maxval;
        } else {
            return gb.getScore(Maxconnect4.TWO) - gb.getScore(Maxconnect4.ONE);
        }
    }    
    private int max_value(GameBoard gb, int d_level, int a_value, int b_value)
        throws CloneNotSupportedException {       
        if (!gb.isBoardFull() && d_level > 0) {
            int minval = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.NOFCOLS; i++) {
                if (gb.isValidPlay(i)) {
                    GameBoard next_move = new GameBoard(gb.getGameBoard());
                    next_move.playPiece(i);
                    int value = min_value(next_move, d_level - 1, a_value, b_value);
                    if (mival < value) {
                        minval = value;
                    }
                    if (minval >= b_value) {
                        return minval;
                    }
                    if (a_value < minval) {
                        a_value = minval;
                    }
                }
            }
            return minval;
        } else {            
            return gb.getScore(Maxconnect4.TWO) - gb.getScore(Maxconnect4.ONE);
        }
    }
}
