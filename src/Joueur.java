public class Joueur {
    private final String nomJoueur;
    private final PieceType pieceType;

    public Joueur(String name, PieceType pieceType) {
        this.nomJoueur = name;
        this.pieceType = pieceType;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
