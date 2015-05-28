package god;

public class Point2D {
    private int x;
    private int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //constructor
    public void setX(int x) {
        this.x = x;
    }
    //set X coordinate
    public void setY(int y) {
        this.y = y;
    }
    //set Y coordinate
    public int getX() {
        return this.x;
    }
    //get X coordinate
    public int getY() {
        return this.y;
    }
    //get Y coordinate
    public double getDistance(Point2D p1, Point2D p2) {
        double distance;
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();
        distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance;
    }
    //get distance between 2 points
}
