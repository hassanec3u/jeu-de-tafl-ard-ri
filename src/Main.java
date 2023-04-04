import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Joueur player1 = new JoueurHumain("Aboubacar", JoueurType.ATTACK);
        Joueur AI = new AI("IA", JoueurType.DEFEND, 4);
        Game game = new Game(player1, AI, 7); // Plateau de 7x7

        Scanner scanner = new Scanner(System.in);

        while (!game.isGameOver()) {
            if (!game.getJoueurActu().estIA())  System.out.println(game.getJoueurActu().getNomJoueur() + "'s turn.");
            if (!game.getJoueurActu().estIA()) System.out.println("saisissez  oldX, oldY, newX, newY");

            game.afficherPlateau();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int newX = scanner.nextInt();
            int newY = scanner.nextInt();

            game.jouerTour(x, y, newX, newY);

        }

        game.afficherPlateau();
        System.out.println("Game over. " + game.afficherGagnant() + " a gagnée!");
    }
}
