public class AI extends Joueur{

    public Plateau plateau;
    private final int profondeurMax;

    public AI(String name, JoueurType joueurType,int profondeurMax) {
        super(name, joueurType);
        this.plateau = Plateau.getInstance();
        this.profondeurMax = profondeurMax;
    }
    private int evaluate(EtatDeJeu etatDeJeu) {
        // Implement the evaluation function for the game state here
        return 0;
    }

    @Override
    public boolean estIA() {
        return true;
    }

    private int alphaBeta(EtatDeJeu etatDeJeu, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || etatDeJeu.isGameOver()) {
            return evaluate(etatDeJeu);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (EtatDeJeu fils : etatDeJeu.getEtatFils()) {

                //on met false car prochainement alpha beta trouvera minimum
                int eval = alphaBeta(fils, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (EtatDeJeu fils : etatDeJeu.getEtatFils()) {

                //on met vrai, car prochainement alpha beta trouvera maximum
                int eval = alphaBeta(fils, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    public Mouvement makeBestMove(EtatDeJeu gameState) {
        int bestEval = Integer.MIN_VALUE;
        Mouvement bestMove = null;

        for (EtatDeJeu fils : gameState.getEtatFils()) {
            int eval = alphaBeta(fils, profondeurMax - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (eval > bestEval) {
                bestEval = eval;
                bestMove = fils.getDeplacement();
            }
        }
        return bestMove;
    }




}
