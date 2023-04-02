public class Plateau {
    private final Piece[][] plateau;
    private final int taille;


    public Plateau(int taille) {
        this.taille = taille;
        this.plateau = new Piece[taille][taille];
        initialiserPlateuArdi();
    }


    public int getTaille() {
        return taille;
    }

    private void initialiserPlateuArdi() {
        // Placez les pièces sur le plateau selon les règles du tafl ard-ri

        //Positionnement du pions blanc
        plateau[0][2] = new Piece(PieceType.NOIR);
        plateau[0][3] = new Piece(PieceType.NOIR);
        plateau[0][4] = new Piece(PieceType.NOIR);
        plateau[1][3] = new Piece(PieceType.NOIR);

        plateau[2][0] = new Piece(PieceType.NOIR);
        plateau[3][0] = new Piece(PieceType.NOIR);
        plateau[4][0] = new Piece(PieceType.NOIR);
        plateau[3][1] = new Piece(PieceType.NOIR);

        plateau[6][2] = new Piece(PieceType.NOIR);
        plateau[6][3] = new Piece(PieceType.NOIR);
        plateau[6][4] = new Piece(PieceType.NOIR);
        plateau[5][3] = new Piece(PieceType.NOIR);

        plateau[2][6] = new Piece(PieceType.NOIR);
        plateau[3][6] = new Piece(PieceType.NOIR);
        plateau[4][6] = new Piece(PieceType.NOIR);
        plateau[3][5] = new Piece(PieceType.NOIR);

        //Positionnement du pions noir
        plateau[2][2] = new Piece(PieceType.BLANC);
        plateau[2][3] = new Piece(PieceType.BLANC);
        plateau[2][4] = new Piece(PieceType.BLANC);

        plateau[4][2] = new Piece(PieceType.BLANC);
        plateau[4][3] = new Piece(PieceType.BLANC);
        plateau[4][4] = new Piece(PieceType.BLANC);

        plateau[3][2] = new Piece(PieceType.BLANC);
        plateau[3][4] = new Piece(PieceType.BLANC);

        //Positionnement du roi au milieu du plateau
        plateau[3][3] = new Piece(PieceType.ROI);
    }

    public void deplacerPiece(Piece piece, int x, int y, int newX, int newY) {
        // Retournez true si le mouvement est valide, false sinon
        plateau[x][y] = null;
        plateau[newX][newY] = piece;
    }

    public Piece getPiece(int x, int y) {
        return plateau[x][y];
    }


    public Joueur getWinner() {
        //verifie si le roi existe
     /*       for (int i = 0; i < plateau.length; i++) {
                for (int j = 0; j < plateau[i].length; j++) {
                    if (plateau[i][j] == ) {
                        return true;
                    }
                }
            }
            return false;
        }
        return null;
    */
        return null;
    }

    public void afficherPlateau() {
        // Afficher les numéros de colonne
        System.out.print("  ");
        for (int j = 0; j < taille; j++) {
            System.out.print("\u001B[31m" + j + " " + "\u001B[0m");
        }
        System.out.println();

        for (int i = 0; i < taille; i++) {
            // Afficher le numéro de ligne
            System.out.print("\u001B[31m" + i + " " + "\u001B[0m");
            for (int j = 0; j < taille; j++) {
                // Vérifier si la cellule actuelle est un coin du plateau
                boolean isCorner = (i == 0 && j == 0) || (i == 0 && j == taille - 1) || (i == taille - 1 && j == 0) || (i == taille - 1 && j == taille - 1);

                Piece piece = plateau[i][j];
                if (piece == null) {
                    if (isCorner) {
                        System.out.print("\u001B[32m" + "X " + "\u001B[0m");
                    } else {
                        System.out.print(". ");
                    }
                } else {
                    switch (piece.getType()) {
                        case ROI:
                            System.out.print("\u001B[33m" + "R " + "\u001B[0m");
                            break;
                        case BLANC:
                            System.out.print("\u001B[37m" + "B " + "\u001B[0m");
                            break;
                        case NOIR:
                            System.out.print("\u001B[30m" + "N " + "\u001B[0m");
                            break;
                    }
                }
            }
            System.out.println();
        }
    }


    public boolean verifierLimites(int x, int y, int newX, int newY) {
        return x < taille && y < taille && newX < taille && newY < taille;
    }



    public boolean isGameOver() {
        // Vérifier si le roi est dans un coin du plateau
        if (plateau[0][0] != null && plateau[0][0].getType() == PieceType.ROI) {
            return true; // Coin en haut à gauche
        }
        if (plateau[0][taille-1] != null && plateau[0][taille-1].getType() == PieceType.ROI) {
            return true; // Coin en haut à droite
        }
        if (plateau[taille-1][0] != null && plateau[taille-1][0].getType() == PieceType.ROI) {
            return true; // Coin en bas à gauche
        }
        if (plateau[taille-1][taille-1] != null && plateau[taille-1][taille-1].getType() == PieceType.ROI) {
            return true; // Coin en bas à droite
        }
        // Le roi n'est pas dans un coin du plateau
        return false;
    }
}
