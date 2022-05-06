package Connect4;
import java.lang.Thread;

public class Tools {
    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected static void wait(int millis) {
        if (millis <= 0) {
            throw new IllegalArgumentException("Milliseconds cannot be zero or lower!");
        }

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected static String getResetAnsi() {
        return "\u001b[0m";
    } 

    protected static void printDebug(Object... values) {
        String greenAnsi = "\u001b[32m";
        for (Object value : values) {
            System.out.println(greenAnsi + "DEBUG: " + value + getResetAnsi());
        }
        wait(500);
    }
}