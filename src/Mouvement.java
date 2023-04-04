public class Mouvement {
    private final int departX;
    private final int departY;
    private final int destinationX;
    private final int destinationY;
    // private final MoveType moveType;


    //public Move(int sourceRow, int sourceCol, int destRow, int destCol, MoveType moveType) {
    public Mouvement(int departX, int departY, int destinationX, int destinationY) {

        this.departX = departX;
        this.departY = departY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        //   this.moveType = moveType;
    }


    public int getDepartX() {
        return departX;
    }

    public int getDepartY() {
        return departY;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }
}

