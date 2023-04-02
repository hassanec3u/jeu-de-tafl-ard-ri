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

    public boolean jouerTour(int x, int y, int newX, int newY) {
        Piece piece = plateau.getPiece(x, y);

        //verifie que la position choisi contient un pion
        if (piece == null) {
            System.out.println("Aucun pion sur les coordonnées saisis");
            return false;
        }

        //verifie si les cordonnées entré sont dans la limites du plateau
        if(!plateau.verifierLimites(x,y,newX,newY)){
            System.out.println("l'un de vos valeurs saisis se trouve en dehors des limites du plateau");
            return true;
        }

        //verifie si un pion une presente ou on veut deplacer le pion
        if (plateau.getPiece(newX, newY) != null) {
            System.out.println("Un pion se trouve a la nouvelle position");
            return false;
        }

        if (!verifieDeplacemenHorizonVertical(x, y, newX, newY)) {
            return false;
        }

        //verifie que le jouer auc pions noir bouge que ces pions
        if (joueurActu.getPieceType() == PieceType.NOIR)
            if (piece.getType() == PieceType.NOIR) {
                plateau.deplacerPiece(piece, x, y, newX, newY);
                changerJoueur();
                return true;
            } else {
                System.out.println("Vous pouviez pas deplacer des pions BLANC/ROI");
                return false;
            }
        else {
            //verifie que le jouer auc pions blanc bouge que ces pions
            if (piece.getType() == PieceType.ROI || piece.getType() == PieceType.BLANC) {
                plateau.deplacerPiece(piece, x, y, newX, newY);
                changerJoueur();
                return true;
            } else {
                System.out.println("Vous pouviez pas deplacer des pions NOIR");
                return false;
            }
        }

    }


    private boolean unPionEstDejaPresent(int newX, int newY) {
        return plateau.getPiece(newX, newY) != null;
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

    public void afficherPlateau() {
        plateau.afficherPlateau();
    }

    private void changerJoueur() {
        joueurActu = (joueurActu == player1) ? IA : player1;
    }

    public boolean isGameOver() {
        if(plateau.isGameOver()){
            return true;
        };
        return false;
    }
}
