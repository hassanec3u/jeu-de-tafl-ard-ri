public class Piece {
    private PieceType type;

    public Piece(PieceType type) {
        this.type = type;

    }


    // Getter and setter methods
    public PieceType getType() {
        return type;
    }

    public boolean estUnPionNoir() {
        return type==PieceType.NOIR;
    }
    public boolean estUnPionBlanc() {
        return type==PieceType.BLANC;
    }

    public boolean estLeRoi () {
        return this.getType() == PieceType.ROI;
    }

    public boolean estMonAdversaire(Piece piece) {
        if (this.estUnPionNoir())
            return piece.estUnPionBlanc()|| piece.estLeRoi();
        else{
                    return piece.estUnPionNoir();
                }
            }

        public boolean estMonAllie (Piece piece){
            if (this.estUnPionNoir())
                return piece.estUnPionNoir();
            else {
                return piece.estUnPionBlanc() || piece.estLeRoi();
            }
        }
    }


