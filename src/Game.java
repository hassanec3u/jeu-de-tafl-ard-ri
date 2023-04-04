
public class Game {
    private final Plateau plateau;
    private final Joueur player1;
    private final Joueur IA;
    private Joueur joueurActu;

    public Game(Joueur player1, Joueur player2, int boardSize) {
        this.player1 = player1;
        this.IA = player2;
        this.plateau = Plateau.getInstance();
        this.joueurActu = player1;
    }

    public Joueur getJoueurActu() {
        return joueurActu;
    }

    public void afficherPlateau() {
        plateau.afficherPlateau();
    }

    public String afficherGagnant() {
        changerJoueur();
        return joueurActu.getNomJoueur();
    }

    private void changerJoueur() {
        joueurActu = (joueurActu == player1) ? IA : player1;
    }

    public boolean isGameOver() {
        return plateau.isGameOver();
    }


    public boolean jouerTour(int x, int y, int newX, int newY) {
        boolean isIA = joueurActu.estIA();
        if (plateau.jouerTour(x, y, newX, newY, isIA,joueurActu.getJoueurType())) {
            changerJoueur();
            return true;
        }
        return false;
    }


}