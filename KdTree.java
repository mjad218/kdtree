/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class KdTree {

    private Node root;

    private int size = 0;

    static class Node {
        private double x, y;
        private Point2D point;
        private Node right;
        private Node left;

        Node(Point2D point) {
            this.point = point;
            this.x = point.x();
            this.y = point.y();
        }

        void setRight(Node n) {
            this.right = n;
        }

        void setLeft(Node n) {
            this.left = n;
        }


    }

    private TreeSet set;

    public KdTree() {
        this.set = set;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // is the set empty?
    public int size() {
        return size;
    }
    // number of points in the set

    public void insert(Point2D p) {
        if (this.set.contains(p)) {
            return;
        }
        this.set.add(p);

    }
    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return this.set.contains(p);
    }          // does the set contain point p?

    public void draw() {
        for (Object p : set)
            ((Point2D) p).draw();
    }
    // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        for (Object p : set)
            if (rect.contains((Point2D) p)) points.add((Point2D) p);
        return points;
    }
    // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D point) {
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
