import java.util.Scanner;

public class Game {
    public void start() {
        boolean isGameOver = false;
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        System.out.println("Player1, place your ships on the game field");
        player1.battlefield.setField();
        changeTurn();

        System.out.println("Player 2, place your ships on the game field");
        player2.battlefield.setField();
        changeTurn();

        while(!isGameOver) {
            player2.battlefield.printBattlefield(true);
            System.out.println("---------------------");
            player1.battlefield.printBattlefield(false);
            System.out.println("Player 1, it's your turn: ");
            isGameOver = player2.battlefield.shootCannon();
            if (isGameOver) {
                break;
            }
            changeTurn();
            player1.battlefield.printBattlefield(true);
            System.out.println("---------------------");
            player2.battlefield.printBattlefield(false);
            System.out.println("Player 2, it's your turn: ");
            isGameOver = player1.battlefield.shootCannon();
            if (isGameOver) {
                break;
            }
            changeTurn();
        }
    }

    private void changeTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }
}
