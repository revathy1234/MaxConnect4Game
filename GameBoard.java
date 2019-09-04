import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameBoard implements Cloneable {    
    private int[][] playBoard;
    private int pieceCount;
    private int currentTurn;
    private Maxconnect4.PLAYER_TYPE first_move;
    private Maxconnect4.MODE g_mode;
    public static final int cols = 7;
    public static final int rows = 6;
    public static final int MAX_PIECE_COUNT = 42;
        public GameBoard(String inputFile) {
        this.playBoard = new int[rows][cols];
        this.pieceCount = 0;
        int counter = 0;
        BufferedReader input = null;
        String gameData = null;        
        try {
            input = new BufferedReader(new FileReader(inputFile));
        } catch (IOException e) {
            System.out.println("\nProblem opening the input file!\nTry again." + "\n");
            e.printStackTrace();
        }
        
        for (int i = 0; i < rows; i++) {
            try {
                gameData = input.readLine();                
                for (int j = 0; j < cols; j++) {
                    this.playBoard[i][j] = gameData.charAt(counter++) - 48;                    
                    if (!((this.playBoard[i][j] == 0) || (this.playBoard[i][j] == Maxconnect4.ONE) || (this.playBoard[i][j] == Maxconnect4.TWO))) {
                        System.out.println("\nProblems!\n--The piece read " + "from the input file was not a 1, a 2 or a 0");
                        this.exit_function(0);
                    }

                    if (this.playBoard[i][j] > 0) {
                        this.pieceCount++;
                    }
                }
            } catch (Exception e) {
                System.out.println("\nProblem reading the input file!\n" + "Try again.\n");
                e.printStackTrace();
                this.exit_function(0);
            }            
            counter = 0;
        }         
        try {
            gameData = input.readLine();
        } catch (Exception e) {
            System.out.println("\nProblem reading the next turn!\n" + "--Try again.\n");
            e.printStackTrace();
        }
        this.currentTurn = gameData.charAt(0) - 48;        
        if (!((this.currentTurn == Maxconnect4.ONE) || (this.currentTurn == Maxconnect4.TWO))) {
            System.out.println("Problems!\n The current turn read is not a " + "1 or a 2!");
            this.exit_function(0);
        } else if (this.getCurrentTurn() != this.currentTurn) {
            System.out.println("Problems!\n the current turn read does not " + "correspond to the number of pieces played!");
            this.exit_function(0);
        }
    } 
     public void pvalue() {
        if ((this.currentTurn == Maxconnect4.ONE && first_move == Maxconnect4.PLAYER_TYPE.COMPUTER)
            || (this.currentTurn == Maxconnect4.TWO && first_move == Maxconnect4.PLAYER_TYPE.HUMAN)) {
            Maxconnect4.COMPUTER_PIECE = Maxconnect4.ONE;
            Maxconnect4.HUMAN_PIECE = Maxconnect4.TWO;
        } else {
            Maxconnect4.HUMAN_PIECE = Maxconnect4.ONE;
            Maxconnect4.COMPUTER_PIECE = Maxconnect4.TWO;
        }
        
        System.out.println("Human plays as : " + Maxconnect4.HUMAN_PIECE + " , Computer plays as : " + Maxconnect4.COMPUTER_PIECE);        
    }        
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public GameBoard(int masterGame[][]) {

        this.playBoard = new int[rows][cols];
        this.pieceCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.playBoard[i][j] = masterGame[i][j];

                if (this.playBoard[i][j] > 0) {
                    this.pieceCount++;
                }
            }
        }
    } 
    public int getScore(int player) {        
        int playerScore = 0;        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i][j + 1] == player)
                    && (this.playBoard[i][j + 2] == player) && (this.playBoard[i][j + 3] == player)) {
                    playerScore++;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < cols; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j] == player)
                    && (this.playBoard[i + 2][j] == player) && (this.playBoard[i + 3][j] == player)) {
                    playerScore++;
                }
            }
        } 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j + 1] == player)
                    && (this.playBoard[i + 2][j + 2] == player) && (this.playBoard[i + 3][j + 3] == player)) {
                    playerScore++;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i + 3][j] == player) && (this.playBoard[i + 2][j + 1] == player)
                    && (this.playBoard[i + 1][j + 2] == player) && (this.playBoard[i][j + 3] == player)) {
                    playerScore++;
                }
            }
        }

        return playerScore;
    } 

    public int getUnBlockedThrees(int player) {        
        int plScore = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i][j + 1] == player)
                    && (this.playBoard[i][j + 2] == player)
                    && (this.playBoard[i][j + 3] == player || this.playBoard[i][j + 3] == 0)) {
                    plScore++;
                }
            }
        } 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < cols; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j] == player)
                    && (this.playBoard[i + 2][j] == player)
                    && (this.playBoard[i + 3][j] == player || this.playBoard[i + 3][j] == 0)) {
                    plScore++;
                }
            }
        }  
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j + 1] == player)
                    && (this.playBoard[i + 2][j + 2] == player)
                    && (this.playBoard[i + 3][j + 3] == player || this.playBoard[i + 3][j + 3] == 0)) {
                    plScore++;
                }
            }
        }

        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i + 3][j] == player) && (this.playBoard[i + 2][j + 1] == player)
                    && (this.playBoard[i + 1][j + 2] == player)
                    && (this.playBoard[i][j + 3] == player || this.playBoard[i][j + 3] == 0)) {
                    plScore++;
                }
            }
        }

        return plScore;
    }    
    public int getCurrentTurn() {
        return (this.pieceCount % 2) + 1;
    } 
    public int getPieceCount() {
        return this.pieceCount;
    }    
    public int[][] getGameBoard() {
        return this.playBoard;
    }    
    public boolean isValidPlay(int column) {
        if (!(column >= 0 && column < 7)) {            
            return false;
        } else if (this.playBoard[0][column] > 0) {
            return false;
        } else {
            return true;
        }
    }    
    boolean isBoardFull() {
        return (this.getPieceCount() >= GameBoard.MAX_PIECE_COUNT);
    }
        public boolean playPiece(int column) {
        if (!this.isValidPlay(column)) {
            return false;
        } else {            
            for (int i = 5; i >= 0; i--) {
                if (this.playBoard[i][column] == 0) {
                    this.playBoard[i][column] = getCurrentTurn();
                    this.pieceCount++;
                    return true;
                }
            }
            
            System.out.println("Something went wrong....");

            return false;
        }
    } 
    public void removePiece(int column) {
        for (int i = 0; i < rows; i++) {
            if (this.playBoard[i][column] > 0) {
                this.playBoard[i][column] = 0;
                this.pieceCount--;

                break;
            }
        }
    } 
    public void printGameBoard() {
        System.out.println(" -----------------");

        for (int i = 0; i < rows; i++) {
            System.out.print(" | ");
            for (int j = 0; j < cols; j++) {
                System.out.print(this.playBoard[i][j] + " ");
            }

            System.out.println("| ");
        }

        System.out.println(" -----------------");
    } 
    public void printGameBoardToFile(String outputFile) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    output.write(this.playBoard[i][j] + 48);
                }
                output.write("\r\n");
            }            
            output.write(this.getCurrentTurn() + "\r\n");
            output.close();

        } catch (IOException e) {
            System.out.println("\nProblem writing to the output file!\n" + "Try again.");
            e.printStackTrace();
        }
    } 

    private void exit_function(int value) {
        System.out.println("exiting from GameBoard.java!\n\n");
        System.exit(value);
    }    
    public void first_turn(Maxconnect4.PLAYER_TYPE turn) {        
        first_move = turn;
        pvalue();
    }

    public Maxconnect4.PLAYER_TYPE getfirst_turn() {        
        return first_move;
    }    
    public void setg_mode(Maxconnect4.MODE mode) {        
        g_mode = mode;
    }

    public Maxconnect4.MODE getg_mode() {        
        return g_mode;
    }

} 
