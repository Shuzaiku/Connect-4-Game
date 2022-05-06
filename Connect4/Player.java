package Connect4;

public class Player {
    protected char[][] tokens; // saves tokens belonging to the player
    protected char token; // decides which char will be used to mark player's token
    protected byte turn; // decides player 1 or 2

    private byte ROWS;
    private byte COLUMNS;

    Player(byte assignedTurn) {
        Board GameBoard = new Board();
        ROWS = GameBoard.ROWS;
        COLUMNS = GameBoard.COLUMNS;

        this.tokens = new char[ROWS][COLUMNS];
        this.turn = assignedTurn;
    }

    protected boolean ownsToken(byte row, byte column) {
        if (this.tokens[row][column] == this.token) {
            return true;
        }
        return false;
    }

    protected String getColor() {
        String redAnsi = "\u001b[31m"; // player 1 colour
        String yellowAnsi = "\u001b[33m"; // player 2 colour
        return (this.turn == 1)? redAnsi : yellowAnsi;
    }
    
    protected byte getMatches(byte currRow, byte currColumn, byte rowStep, byte columnStep, byte matches) {
        currRow += rowStep;
        currColumn += columnStep;

        if (currRow == ROWS || currRow == -1 || currColumn == COLUMNS || currColumn == -1) {
            return matches;
        }

        if (this.tokens[currRow][currColumn] == this.token) {
            matches++;
            return getMatches(currRow, currColumn, rowStep, columnStep, matches);
        } else {
            return matches;
        }
    }
}