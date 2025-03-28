/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet set;

    public PointSET() {
        this.set = new TreeSet<Point2D>();
    }

    // construct an empty set of points
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // is the set empty?
    public int size() {
        return set.size();
    }
    // number of points in the set

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (this.set.contains(p)) {
            return;
        }
        this.set.add(p);

    }
    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return this.set.contains(p);
    }          // does the set contain point p?

    public void draw() {
        for (Object p : set)
            ((Point2D) p).draw();
    }
    // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        for (Object p : set)
            if (rect.contains((Point2D) p)) points.add((Point2D) p);
        return points;
    }
    // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        Point2D nearestPoint = null;
        double smallestDistance = 1000000000;
        for (Object p : set) {
            double distance = point.distanceTo((Point2D) p);
            if (distance < smallestDistance) {
                nearestPoint = (Point2D) p;
                smallestDistance = distance;
            }
        }

        return nearestPoint;
    }
    // a nearest neighbor in the set to point p; null if the set is empty


    public static void main(String[] args) {

    }
}
