public class Joueur {
    private final String nomJoueur;
    private final JoueurType joueurType;

    public Joueur(String name, JoueurType joueurType) {
        this.nomJoueur = name;
        this.joueurType = joueurType;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public JoueurType getJoueurType() {
        return joueurType;
    }

    public boolean estIA(){
        return false;
    }
}
