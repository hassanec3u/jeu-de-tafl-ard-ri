public class Game {
    private final Plateau plateau;
    private final Joueur player1;
    private final Joueur IA;
    private Joueur joueurActu;

    public Game(Joueur player1, Joueur player2, int boardSize) {
        this.player1 = player1;
        this.IA = player2;
        this.plateau = new Plateau(boardSize);
        this.joueurActu = player1;
    }

    public Joueur getJoueurActu() {
        return joueurActu;
    }

    private boolean unPionEstDejaPresent(int newX, int newY) {
        return plateau.getPiece(newX, newY) != null;
    }

    public void afficherPlateau() {
        plateau.afficherPlateau();
    }

    public String afficherGagnant( ) {
        changerJoueur();
        return joueurActu.getNomJoueur();
    }

    private void changerJoueur() {
        joueurActu = (joueurActu == player1) ? IA : player1;
    }

    public boolean isGameOver() {
        return plateau.isGameOver();
    }

    public void jouerTour(int x, int y, int newX, int newY) {

        // vérifie si les coordonnées entrées sont dans les limites du plateau
        if (!plateau.verifierLimites(x, y, newX, newY)) {
            System.out.println("l'un de vos valeurs saisis se trouve en dehors des limites du plateau");
            return;
        }

        Piece piece = plateau.getPiece(x, y);
        // vérifie que la position choisie contient un pion
        if (piece == null) {
            System.out.println("Aucun pion sur les coordonnées saisis");
            return;
        }


        // vérifie si un pion est déjà présent dans la nouvelle position
        if (plateau.getPiece(newX, newY) != null) {
            System.out.println("Un pion se trouve a la nouvelle position");
            return;
        }

        //empeche au pions noir d'acceder au coins
        if (piece.estUnPionNoir()) {
            if (plateau.estUneForteresse(newX, newY)) {
                System.out.println("un pion noir ne peut pas acceder au sortie du roi");
                return;
            }
        }

        // oblige les déplacements rectilignes
        if (!verifieDeplacemenHorizonVertical(x, y, newX, newY)) {
            return;
        }

        // vérifie que le joueur aux pions noirs bouge que ses pions
        if (joueurActu.getPieceType() == PieceType.NOIR)
            if (piece.estUnPionNoir()) {
                plateau.deplacerPion(piece, x, y, newX, newY);
                plateau.capture(piece,newX,newY);
                changerJoueur();
            } else {
                System.out.println("Vous pouviez pas deplacer des pions BLANC/ROI");
            }
        else {
            // vérifie que le joueur aux pions blancs bouge que ses pions
            if (piece.estLeRoi() || piece.estUnPionBlanc()) {
                plateau.deplacerPion(piece, x, y, newX, newY);
                plateau.capture(piece,newX,newY);
                changerJoueur();
            } else {
                System.out.println("Vous pouviez pas deplacer des pions NOIR");
            }
        }
    }
    private boolean verifieDeplacemenHorizonVertical(int x, int y, int newX, int newY) {
        // vérifier si le pion se déplace sur une même ligne ou même colonne
        if (x == newX || y == newY) {
            // déplacement horizontal
            if (x == newX) {
                // vérifier si la tour ne passe pas par-dessus une autre pièce
                int pas = (newY > y) ? 1 : -1;
                for (int i = y + pas; i != newY; i += pas) {
                    if (unPionEstDejaPresent(x, i)) {
                        System.out.println("Impossible de deplacer le pion choisi vers cette destination car il y'a deja un pion qui sur le chemin");
                        return false;
                    }
                }
            }
            // déplacement vertical
            else {
                // vérifier si la tour ne passe pas par-dessus une autre pièce
                int pas = (newX > x) ? 1 : -1;
                for (int i = x + pas; i != newX; i += pas) {
                    if (unPionEstDejaPresent(i, y)) {
                        System.out.println("Impossible de deplacer le pion choisi vers cette destination car il y'a deja un pion sur le chemin");
                        return false;
                    }
                }
            }
            return true;
        }
        System.out.println("On ne peut se deplacer qu'en ligne");
        return false;
    }


}