package models;

public class Position {

    private int x = 0;
    private int y = 0;

    public Position(){}
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEqual(Position position) {
        return x == position.x && y == position.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void updateY(int offset) {
        y += offset;
    }

    public void updateX(int offset) {
        x += offset;
    }

    public String toXY() {
        return Integer.toString(x) + y;
    }
}
