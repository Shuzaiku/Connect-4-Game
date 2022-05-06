package Connect4;

public class Board {
    final public char EMPTY_FILL = '_';
    final public byte ROWS = 6;
    final public byte COLUMNS = 7;
    final private static String SPACING = " ";
    final private static int ANIMATION_DELAY = 100;
    final private static byte MATCHES_TO_WIN = 4;

    public char[][] arrayBoard;
    public Player[] players = new Player[2];
    public Player winner;

    protected void fillBoard() {
        this.arrayBoard = new char[ROWS][COLUMNS];
        for (byte row = 0; row < ROWS; row++) {
            for (byte column = 0; column < COLUMNS; column++) {
                this.arrayBoard[row][column] = EMPTY_FILL;
            }
        }
    }

    private void enumerateColumns() {
        System.out.print("\u001b[36m"); // cyan ansi code
        for (byte column = 0; column < COLUMNS; column++) {
            System.out.print((column+1) + SPACING);
        }
        System.out.println(Tools.getResetAnsi());
    }

    protected void printBoard() {
        enumerateColumns();
        for (byte row = 0; row < ROWS; row++) {
            for (byte column = 0; column < COLUMNS; column++) {
                for (Player plr : this.players) {
                    if (plr.ownsToken(row, column)) { // assign colour to token
                        System.out.print(plr.getColor());
                        break;
                    }
                }

                char currCell = this.arrayBoard[row][column];
                System.out.print(currCell + SPACING + Tools.getResetAnsi());
            }
            System.out.println();
        }
        enumerateColumns();
    }

    protected void dropToken(Player plr, byte column) {
        for (byte row = ROWS-1; row > -1; row--) {
            char currCell = this.arrayBoard[row][column];
            if (currCell == EMPTY_FILL) {
                byte rowGoal = (byte) (row + 1);
                animateBoard(rowGoal, column, plr);
                break;
            }
        }
    }

    protected void animateBoard(byte rowGoal, byte column, Player plr) {
        for (byte row = 0; row < rowGoal; row++) {
            char plrToken = plr.token;
            plr.tokens[row][column] = plrToken;
            this.arrayBoard[row][column] = plrToken;
            if (row > 0) {
                this.arrayBoard[row-1][column] = EMPTY_FILL;
                plr.tokens[row-1][column] = '\u0000';
            }
            printBoard();
            Tools.wait(ANIMATION_DELAY);

            if (row != rowGoal-1) {
                Tools.clearScreen();
            } else {
                byte step = 1;
                findWinner(plr, row, column, step, (byte) 0); // check up and down
                findWinner(plr, row, column, (byte) 0, step); // check sideways
                findWinner(plr, row, column, step, step); // check diagonally
                findWinner(plr, row, column, step, (byte) -step); // check inverted diagonal
            }
        }
    }

    protected void findWinner(Player plr, byte row, byte column, byte rowDirection, byte columnDirection) {
        byte matches = 1;
        for (byte step = 1; step > -2; step -= 2) {
            matches += plr.getMatches(row, column, (byte) (step * rowDirection), (byte) (step * columnDirection), (byte) 0);
        }

        if (matches == MATCHES_TO_WIN) {
            this.winner = plr;
        }
    }

    protected boolean isColumnFull(byte column) {
        if (this.arrayBoard[0][column] != EMPTY_FILL) {
            return true;
        }
        return false;
    }

    protected boolean isBoardFull() {
        for (byte column = 0; column < COLUMNS; column++) {
            if (!isColumnFull(column)) {
                return false;
            }
        }
        return true;
    }
}