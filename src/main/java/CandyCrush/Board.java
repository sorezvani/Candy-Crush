package CandyCrush;
import java.util.Random;

public class Board {
    private int numRows;
    private int numCols;
    private int [][] board;
    Player player ;
    public int getBoardElement(int row , int col) {
        return board[row][col];
    }
    public Board(String name , int numRows , int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        board = new int [numRows][numCols];
        player = new Player(name);
        generateBoard();
    }
    public int randomGenerate () {
        // Generate Random number to place in the game board for place of fruits
        Random rand = new Random();
        return rand.nextInt(5) + 1;
    }
    public boolean firstCheck (int numRow , int numColumn , int number) {
        // a method to firstCheck if there is 3 number in a row or column when generating the board
        if (numRow > 1 && number == board[numRow-1][numColumn] && number == board[numRow-2][numColumn]) return true;
        return numColumn > 1 && number == board[numRow][numColumn - 1] && number == board[numRow][numColumn - 2];
    }
    public boolean firstCheck3Move () {
        int count =0;
        int[][] checkBoard = new int[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            if (numCols >= 0) System.arraycopy(board[row], 0, checkBoard[row], 0, numCols);
        }
        for(int i = 0; i < numRows -1 ; i++){
            for (int j = 0; j < numCols -1 ; j++){
                if (checkAndSwapCells(i,j,i,j+1)) {
                    if(hasMatches(i,j)) {
                        eliminateCellsWithOutCount(i,j);
                        count ++;
                    }else if(hasMatches(i,j+1)) {
                        eliminateCellsWithOutCount(i,j+1);
                        count ++;
                    }else checkAndSwapCells(i,j,i,j+1);
                }
                if (checkAndSwapCells(i,j,i+1,j)) {
                    if (hasMatches(i,j)) {
                        eliminateCellsWithOutCount(i,j);
                        count ++;
                    }else if (hasMatches(i+1,j)) {
                        eliminateCellsWithOutCount(i+1,j);
                        count ++;
                    }else checkAndSwapCells(i,j,i+1,j);
                }
            }
        }
        if (count >2 ) {
            for (int row = 0; row < numRows; row++) {
                if (numCols >= 0) System.arraycopy(checkBoard[row], 0, board[row], 0, numCols);
            }
        }
        return count > 2;
    }
    public void generateBoard () {
        // a method to first generate the board and firstCheck not to be any 3 number to be in a row or column and to firstCheck if there is 3 possible move at start of the game
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++){
                int num;
                do {
                    num = randomGenerate();
                } while (firstCheck(row , col , num));
                board[row][col] = num;
            }
        }
        if (!firstCheck3Move()) generateBoard();
    }
    public void playerMove (int row1,int col1,int row2,int col2) {
        if (checkAndSwapCells(row1,col1,row2,col2)){
            if (eliminateCells(row1,col1)) return ;
            if (eliminateCells(row2,col2)) return ;
            checkAndSwapCells(row1,col1,row2,col2);
            player.loseHeart();
            return;
        }
        player.loseHeart();
    }
    public boolean eliminateCells(int row, int col) {
        int cellValue = board[row][col];
        int count = 1;
        int end = 1;
        int start = -1;
        boolean cellsEliminated = false;

        // Check for vertical matches
        while (row + start >= 0 && cellValue == board[row + start][col]) {
            count++;
            start--;
        }
        while ( row + end < numRows && cellValue == board[row + end][col]) {
            count++;
            end++;
        }
        if(count >= 3) {
            end += row - 1 ;
            start += row + 1 ;
            cellsEliminated = true ;
            player.calculateNewScore(count);
            removeVerticalMatches(end,start,col);
        }

        // Trigger cascade effect if cells were eliminated
        if (cellsEliminated) {
            triggerCascadeEffect();
            return true;
        }

        count = 1;
        end = 1;
        start = -1;

        // Check for horizontal matches
        while (col + start >= 0 && cellValue == board[row][col + start]) {
            count++;
            start--;
        }
        while ( col + end < numCols && cellValue == board[row][col + end]) {
            count ++;
            end ++;
        }
        if (count >= 3){
            end += col - 1 ;
            start += col + 1 ;
            cellsEliminated = true ;
            player.calculateNewScore(count);
            removeHorizontalMatches(end,start,row);
        }

        // Trigger cascade effect if cells were eliminated
        if (cellsEliminated) {
            triggerCascadeEffect();
            return true;
        }
        return false;
    }
    public void eliminateCellsWithOutCount(int row, int col) {
        int cellValue = board[row][col];
        int count = 1;
        int end = 1;
        int start = -1;

        // Check for vertical matches
        while (row + start >= 0 && cellValue == board[row + start][col]) {
            count++;
            start--;
        }
        while ( row + end < numRows && cellValue == board[row + end][col]) {
            count++;
            end++;
        }
        if(count >= 3) {
            end += row - 1 ;
            start += row + 1 ;
            removeVerticalMatches(end,start,col);
            return;
        }


        count = 1;
        end = 1;
        start = -1;

        // Check for horizontal matches
        while (col + start >= 0 && cellValue == board[row][col + start]) {
            count++;
            start--;
        }
        while ( col + end < numCols && cellValue == board[row][col + end]) {
            count ++;
            end ++;
        }
        if (count >= 3){
            end += col - 1 ;
            start += col + 1 ;
            removeHorizontalMatches(end,start,row);
        }
    }
    public boolean hasNotPossibleMove() {
        // to find out if there is any possible move to do in board or not
        for(int i = 0; i < numRows -1 ; i++){
            for (int j = 0; j < numCols -1 ; j++){
                if (checkAndSwapCells(i,j,i,j+1)) {
                    if(hasMatches(i,j) || hasMatches(i,j+1)) {
                        checkAndSwapCells(i,j,i,j+1);
                        return false;
                    }
                    checkAndSwapCells(i,j,i,j+1);
                }
                if (checkAndSwapCells(i,j,i+1,j)) {
                    if(hasMatches(i,j) || hasMatches(i+1,j)) {
                        checkAndSwapCells(i,j,i+1,j);
                        return false;
                    }
                    checkAndSwapCells(i,j,i+1,j);
                }
            }
        }
        return true;
    }
    public boolean hasMatches (int row,int col) {
        // to firstCheck if there is 3 or more matches in a row or col
        if (row > numRows || col > numCols) return false;
        int count = 1;
        int num = board[row][col];

        // Check for matches in the row
        int i=1;
        while (row - i >= 0 && num == board[row - i][col]) {
            count++;
            i++;
        }
        i = 1;
        while ( row + i < numRows && num == board[row + i][col]) {
            count++;
            i++;
        }
        if (count >= 3){
            return true;
        }

        // Check for matches in the column
        i = 1;
        count = 1;
        while (col - i >= 0 && num == board[row][col - i]) {
            count++;
            i++;
        }
        i = 1;
        while ( col + i < numCols && num == board[row][col + i]) {
            count++;
            i++;
        }
        return count >= 3;
    }
    public void triggerCascadeEffect () {
        regenerateBoard();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                eliminateCells(i,j);
            }
        }
    }
    public void regenerateBoard() {
        for (int col = 0; col < numCols; col++) {
            int numEmptySpaces = 0;
            for (int row = numRows -1 ; row >= 0; row--) {
                if (board[row][col] == 0) {
                    numEmptySpaces++;
                } else if (numEmptySpaces > 0) {
                    // Shift fruit down to fill empty space
                    board[row + numEmptySpaces][col] = board[row][col];
                    board[row][col] = 0 ;
                }
            }
            for (int row =0 ; row < numEmptySpaces ; row++) {
                board [row] [col] = randomGenerate();
            }
        }
    }
    public void removeVerticalMatches(int end , int start , int col) {
        for(int i = start; end >=i;i++){
            board[i][col] = 0;
        }
    }
    public void removeHorizontalMatches(int end , int start ,int row) {
        for(int i=start; end >=i;i++){
            board[row][i] = 0;
        }
    }
    public boolean checkAndSwapCells (int row1,int col1,int row2,int col2) {
        // to firstCheck if it can swap cells or not and if it can change it
        if (Math.abs(row2 - row1) + Math.abs(col2 - col1) == 1 && row1 <= numRows && row2 <= numRows && col1 <= numCols && col2 <= numCols) {
            int tempValue = board[row1][col1];
            board[row1][col1] = board[row2][col2];
            board[row2][col2] = tempValue;
            return true;
        }
        return false;
    }
}
