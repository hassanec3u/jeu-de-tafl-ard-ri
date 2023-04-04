import java.util.ArrayList;

public class EtatDeJeu {
    private final Plateau plateau;
    private final boolean estLIA;
    private final Mouvement dernierMouvement;

    public EtatDeJeu(Plateau plateau, boolean estLIA, Mouvement dernierMouvement) {
        this.plateau = plateau;
        this.estLIA = estLIA;
        this.dernierMouvement = dernierMouvement;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean getJoueurActu() {
        return estLIA;
    }

    public Mouvement getDernierMouvement() {
        return dernierMouvement;
    }

    public boolean isGameOver() {
        return plateau.verifieSiRoiCapture();
    }

    public ArrayList<EtatDeJeu> getEtatFils() {
        ArrayList<EtatDeJeu> etatsFils = new ArrayList<>();
        ArrayList<Mouvement> mouvementsPossibles = plateau.mouvementsPossibles();

        for (Mouvement mouvement : mouvementsPossibles) {
            //  Plateau nouveauPlateau = plateau;
//            EtatDeJeu nouvelEtat = new EtatDeJeu(nouveauPlateau,!estLIA,mouvement);

            EtatDeJeu nouvelEtat = new EtatDeJeu(plateau, !estLIA, mouvement);
            etatsFils.add(nouvelEtat);
        }

        return etatsFils;
        //return null;
    }

    public Mouvement getDeplacement() {
        return null;
    }
}
