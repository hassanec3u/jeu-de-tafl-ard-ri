public class Plateau {
    private final Piece[][] plateau;
    private int nbPionNoir;
    private int nbPionBlanc;

    private boolean roiEstCapture;
    private final int taille;


    public Plateau(int taille) {
        this.taille = taille;
        this.plateau = new Piece[taille][taille];
        this.nbPionNoir = 12;
        this.nbPionBlanc = 8;
        this.roiEstCapture = false;
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


    public void deplacerPion(Piece piece, int x, int y, int newX, int newY) {
        // Retournez true si le mouvement est valide, false sinon
        plateau[x][y] = null;
        plateau[newX][newY] = piece;
    }

    public void capture(Piece piece, int newX, int newY) {
        int[][] offsets = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // offsets pour les côtés gauche, droit, haut et bas
        for (int[] offset : offsets) {
            int xAdversaire = newX + offset[0];
            int yAdversaire = newY + offset[1];
            if (xAdversaire >= 0 && xAdversaire < plateau.length && yAdversaire >= 0 && yAdversaire < plateau[0].length) {
                Piece pieceAdversaire = plateau[xAdversaire][yAdversaire];
                if (pieceAdversaire != null) {
                    if (piece.estMonAdversaire(piece)){
                        boolean[][] visited = new boolean[plateau.length][plateau[0].length];
                        boolean[] presenceRoiDansLeGroupe = {false};
                        if(groupeCapture(xAdversaire,yAdversaire,visited,presenceRoiDansLeGroupe)){
                            roiEstCapture = true;
                        }
                    }

                    if (pieceAdversaire.estLeRoi()) {
                        // Vérifier si le roi est capturé
                        int count = 0;
                        for (int[] offset2 : offsets) {
                            int x = xAdversaire + offset2[0];
                            int y = yAdversaire + offset2[1];
                            if (x >= 0 && x < plateau.length && y >= 0 && y < plateau[0].length) {
                                Piece p = plateau[x][y];
                                if (p != null && p.estMonAllie(piece)) {
                                    count++;
                                }
                            }
                        }
                        if (estSurBord(xAdversaire, yAdversaire) && count >= 3) {
                            retirerPion(xAdversaire, yAdversaire);
                            roiEstCapture = true;
                        } else if (estPresDeForteresse(xAdversaire, yAdversaire) && count >= 2) {
                            retirerPion(xAdversaire, yAdversaire);
                            roiEstCapture = true;
                        } else if (count == 4) {
                            retirerPion(xAdversaire, yAdversaire);
                            roiEstCapture = true;
                        }
                    } else {
                        if (pieceAdversaire.estMonAdversaire(piece)) {
                            int xAllie = xAdversaire + offset[0];
                            int yAllie = yAdversaire + offset[1];
                            if (xAllie >= 0 && xAllie < plateau.length && yAllie >= 0 && yAllie < plateau[0].length) {
                                Piece pieceAllie = plateau[xAllie][yAllie];
                                if ((pieceAllie != null && pieceAllie.estMonAllie(piece)) || (pieceAllie == null && estUneForteresse(xAllie, yAllie))) {
                                    retirerPion(xAdversaire, yAdversaire);
                                }
                            }
                        }

                    }
                }
            }
        }
        miseAjourNbrePions();
    }

    public boolean groupeCapture(int x, int y, boolean[][] visited, boolean[] presenceRoiDansLeGroupe) {
        //ca veut dire le roi ou un allié est sur le bord
        if (!verifierLimites(x, y, x, y) ) {
            return false;
        }

        if(  visited[x][y] ){
            return true;
        }
        visited[x][y] = true;

        Piece piece = plateau[x][y];

        if (piece == null ){
            return false;
        }

        if(piece.estUnPionNoir()){
            return true;
        }


        // Si la pièce est un roi, mettez à jour la variable presenceRoiDansLeGroupe
        if (piece.estLeRoi()) {
            presenceRoiDansLeGroupe[0] = true;
        }

        int[][] offsets = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // offsets pour les côtés gauche, droit, haut et bas
        boolean encercle = true;

        for (int[] offset : offsets) {
            int newX = x + offset[0];
            int newY = y + offset[1];
            encercle = encercle && groupeCapture(newX, newY, visited,presenceRoiDansLeGroupe);

        }
        return encercle ;
    }




    public void retirerPion(int x, int y) {
        plateau[x][y] = null;
    }

    public Piece getPiece(int x, int y) {
        return plateau[x][y];
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

    public boolean estUneForteresse(int x, int y) {
        return (x == 0 && y == 0) || (x == 0 && y == plateau[0].length - 1) || (x == plateau.length - 1 && y == 0) || (x == plateau.length - 1 && y == plateau[0].length - 1);
    }

    public boolean estSurBord(int x, int y) {
        return (x == 0 || x == plateau.length - 1 || y == 0 || y == plateau[0].length - 1);
    }

    public boolean estPresDeForteresse(int x, int y) {
        int[][] offsets = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // offsets pour les côtés gauche, droit, haut et bas

        for (int[] offset : offsets) {
            int xAdjacent = x + offset[0];
            int yAdjacent = y + offset[1];

            if (estUneForteresse(xAdjacent, yAdjacent)) {
                return true;
            }
        }
        return false;

    }

    public boolean isGameOver() {
        // Vérifier si le roi est dans un coin du plateau
        if (plateau[0][0] != null && plateau[0][0].estLeRoi()) {
            return true; // Coin en haut à gauche
        }
        if (plateau[0][taille - 1] != null && plateau[0][taille - 1].estLeRoi()) {
            return true; // Coin en haut à droite
        }
        if (plateau[taille - 1][0] != null && plateau[taille - 1][0].estLeRoi()) {
            return true; // Coin en bas à gauche
        }
        if (plateau[taille - 1][taille - 1] != null && plateau[taille - 1][taille - 1].estLeRoi()) {
            return true; // Coin en bas à droite
        }
        if (roiEstCapture) {
            System.out.println();
            return true;
        }
        return nbPionNoir == 0;

    }

    public void miseAjourNbrePions() {
        int nbPionsNoirs = 0;
        int nbPionsBlancs = 0;
        for (Piece[] pieces : plateau) {
            for (Piece piece : pieces) {
                if (piece != null) {
                    if (piece.estUnPionNoir()) {
                        nbPionsNoirs++;
                    }
                    if (piece.estUnPionBlanc()) {
                        nbPionsBlancs++;
                    }
                }
            }

        }
        this.nbPionNoir = nbPionsBlancs;
        this.nbPionBlanc = nbPionsNoirs;

        System.out.println("Nombre de pions noirs: " + nbPionsNoirs + " <-----> Nombre de pions blancs: " + nbPionsBlancs);
    }

}
