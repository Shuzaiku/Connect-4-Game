package Connect4;

// Emilio Osorio.
// Connect4 game.
// Started 2022-05-04.
// Finished 2022-05-06.

public class Main {
    private static void startGame() {
        // # Init
        String greenAnsi = "\u001b[32m";
        String magentaAnsi = "\u001b[35m";
        String redAnsi = "\u001b[31m";

        Input input = new Input();
        Board GameBoard = new Board();
        GameBoard.fillBoard();

        for (byte i = 0; i < GameBoard.players.length; i++) {
            GameBoard.players[i] = new Player((byte) (i+1));
            Player plr = GameBoard.players[i];

            input.byteExceptionMsg = Tools.getResetAnsi() + input.byteExceptionMsg;
            String plrColor = plr.getColor();
            String prompt = String.format(
                "%sPlayer %d%s, choose a symbol for your token:\n",
                plrColor, plr.turn, Tools.getResetAnsi()
            );
            plr.token = input.getChar(prompt + plrColor);
            System.out.print(Tools.getResetAnsi());
            Tools.clearScreen();
        }

        // # Game loop
        byte currTurn = 0;
        while (true) {
            currTurn %= 2;
            Player plr = GameBoard.players[currTurn];

            input.byteExceptionMsg = redAnsi + 
            "Column out of range! Choose a column between 1 and " + 
            GameBoard.COLUMNS + 
            "." +
            Tools.getResetAnsi()
            ;

            byte selectedColumn;
            GameBoard.printBoard();
            System.out.println();
            while (true) {
                selectedColumn = input.getByte(
                    String.format("%sPlayer %d%s, in what column do you want to place your token?:\n",
                    plr.getColor(),
                    plr.turn,
                    Tools.getResetAnsi())
                );
                
                if (selectedColumn < 1 || selectedColumn > GameBoard.COLUMNS) {
                    System.out.println(input.byteExceptionMsg);
                } else if (GameBoard.isColumnFull((byte) (selectedColumn-1))) {
                    System.out.println(redAnsi + "Column " + selectedColumn + " is full! Choose again." + Tools.getResetAnsi());
                } else {
                    selectedColumn--;
                    Tools.clearScreen();
                    break;
                }
            }
            GameBoard.dropToken(plr, selectedColumn);
            Tools.wait(250);
            Tools.clearScreen();

            if (GameBoard.isBoardFull()) {
                System.out.println(magentaAnsi + "Board is full! It's a tie!" + Tools.getResetAnsi());
                break;
            } else if (GameBoard.winner != null) {
                Player winner = GameBoard.winner;
                System.out.println(
                    winner.getColor() + "Player " + winner.turn +
                    greenAnsi + " has won the game!" + Tools.getResetAnsi()
                );
                break;
            }
            currTurn++;
        }
        char replay;
        input.charExceptionMsg = redAnsi + "Invalid input. Try again." + Tools.getResetAnsi();
        while (true) {
            replay = Character.toLowerCase(input.getChar("Play again? (Y/N)\n"));
            if (replay == 'y') {
                Tools.clearScreen();
                startGame();
                break;
            } else if (replay == 'n') {
                Tools.clearScreen();
                System.out.println("Game over!");
                Tools.wait(500);
                Tools.clearScreen();
                break;
            } else {
                System.out.println(input.charExceptionMsg);
            }
        }
    }

    public static void main(String[] args) {
        startGame();
    }
}