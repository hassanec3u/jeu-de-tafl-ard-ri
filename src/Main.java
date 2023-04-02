import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Joueur player1 = new JoueurHumain("Aboubacar", PieceType.NOIR);
        Joueur AI = new AI("IA", PieceType.BLANC);
        Game game = new Game(player1, AI, 7); // Plateau de 7x7

        Scanner scanner = new Scanner(System.in);

        while (!game.isGameOver()) {
            System.out.println(game.getJoueurActu().getNomJoueur() + "'s turn.");
            game.afficherPlateau();
            System.out.println("Entrer la position actuelle du pion que vous vouliez deplacé (x y)\n et ensuite sa nouvelle position (newX newY) de cette piece:");

            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int newX = scanner.nextInt();
            int newY = scanner.nextInt();

            game.jouerTour(x, y, newX, newY);

        }

        // System.out.println("Game over. " + game.getWinner().getName() + " wins!");
    }
}
