package god;

public class Point2D {
    private int x;
    private int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double getDistance(Point2D p1, Point2D p2) {
        double distance;
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();
        distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance;
    }
}
