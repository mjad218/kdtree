/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;

public class KdTree {

    private Node root;

    private int size = 0;

    static class Node {
        private Point2D point;
        private Node right;
        private Node parent;

        private int level;
        private Node left;

        Node(Point2D point) {
            this.point = point;
            this.right = null;
            this.left = null;
        }

        void setRight(Node n) {
            this.right = n;
        }

        void setLeft(Node n) {
            this.left = n;
        }

        void setParent(Node n) {
            this.parent = n;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int level() {
            return this.level;
        }

        public Node parent() {
            return this.parent;
        }

        public Point2D point() {
            return this.point;
        }

        public Node right() {
            return this.right;
        }

        public Node left() {
            return this.left;
        }

        public Point2D[] limits() {
            Point2D p1 = null;
            Point2D p2 = null;

            if (this.parent() == null) {
                double x1 = this.level % 2 == 0 ? this.point().x() : 0;
                double x2 = this.level % 2 == 0 ? this.point().x() : 1;
                double y1 = this.level % 2 != 0 ? this.point().y() : 0;
                double y2 = this.level % 2 != 0 ? this.point().y() : 1;

                p1 = new Point2D(x1, y1);
                p2 = new Point2D(x2, y2);
                Point2D[] points = { p1, p2 };
                return points;
            }

            if (this.level % 2 == 0) {
                // vertical line
                Node parent = this.parent();
                Point2D[] parentLimits = parent.limits();
                Node grandParent = parent.parent();
                double y1;
                double y2;
                y1 = parentLimits[0].y();
                y2 = this.point().y() > y1 ? 1 : 0;
                if (grandParent != null) {
                    Point2D[] grandParentLimits = grandParent.limits();
                    double grandPointLimitsYMin = Math.min(grandParentLimits[0].y(),
                                                           grandParentLimits[1].y());
                    double grandPointLimitsYMax = Math.max(grandParentLimits[0].y(),
                                                           grandParentLimits[1].y());
                    if (between(this.point().y(), y1, grandPointLimitsYMin)) {
                        y2 = grandPointLimitsYMin;
                    }
                    else if (between(this.point().y(), y1, grandPointLimitsYMax)) {
                        y2 = grandPointLimitsYMax;

                    }
                }
                double x = this.point().x();
                p1 = new Point2D(x, y1);
                p2 = new Point2D(x, y2);
                Point2D[] points = { p1, p2 };
                return points;
            }
            Node parent = this.parent();
            Point2D[] parentLimits = parent.limits();
            Node grandParent = parent.parent();
            double x1;
            double x2;
            x1 = parentLimits[0].x();
            x2 = this.point().x() > x1 ? 1 : 0;
            if (grandParent != null) {
                Point2D[] grandParentLimits = grandParent.limits();
                double grandPointLimitsXMin = Math.min(grandParentLimits[0].x(),
                                                       grandParentLimits[1].x());
                double grandPointLimitsXMax = Math.max(grandParentLimits[0].x(),
                                                       grandParentLimits[1].x());
                if (between(this.point().x(), x1, grandPointLimitsXMin)) {
                    x2 = grandPointLimitsXMin;
                }
                else if (between(this.point().x(), x1, grandPointLimitsXMax)) {
                    x2 = grandPointLimitsXMax;

                }
            }
            double y = this.point().y();
            p1 = new Point2D(x1, y);
            p2 = new Point2D(x2, y);
            Point2D[] points = { p1, p2 };
            return points;
        }

        private boolean between(double value, double l1, double l2) {
            double min = Math.min(l1, l2);
            double max = Math.max(l1, l2);
            return value >= min && value <= max;
        }

        public void draw() {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            this.point.draw();
            StdDraw.setPenRadius();
            Point2D[] limits = this.limits();
            Point2D p1 = limits[0];
            Point2D p2 = limits[1];
            Color color = this.level() % 2 == 0 ? StdDraw.RED : StdDraw.BLUE;
            StdDraw.setPenColor(color);
            StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
        }

        public RectHV rectHV() {
            if (this.level() % 2 == 0) {
                // vertical
                // y1 , y2
                Point2D[] limits = this.limits();
                Point2D[] parentLimits = this.parent().limits();
                double x1 = parentLimits[0].x();
                double x2 = parentLimits[1].x();
                double y1 = limits[0].y();
                double y2 = limits[1].y();
                return new RectHV(x1, y1, x2, y2);

            }
            Point2D[] limits = this.limits();
            Point2D[] parentLimits = this.parent().limits();
            double x1 = limits[0].x();
            double x2 = limits[1].x();
            double y1 = parentLimits[0].y();
            double y2 = parentLimits[1].y();
            return new RectHV(x1, y1, x2, y2);
        }

        public int compareTo(Node that) {
            if (this.level % 2 == 0) {
                if (this.point.x() == that.point().x()) {
                    // if (this.point.y() == that.point().y()) {
                    //     return 0;
                    // }
                    // return this.point.y() - that.point().y() > 0 ? 1 : -1;
                    return 0;
                }
                return this.point.x() - that.point().x() > 0 ? 1 : -1;
            }
            if (this.point.y() == that.point().y()) {
                // if (this.point.x() == that.point().x()) {
                //     return 0;
                // }
                // return this.point.x() - that.point().x() > 0 ? 1 : -1;
                return 0;

            }
            return this.point.y() - that.point().y() > 0 ? 1 : -1;
        }
    }


    public KdTree() {
        this.root = null;
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
        if (this.contains(p)) {
            System.out.print(p);
            System.out.print(" Duplicate point \n");
            return;
        }

        this.insert(p, root);
    }

    private void insert(Point2D p, Node n) {

        this.size++;
        if (this.root == null) {
            this.root = new Node(p);
            return;
        }
        if (n.compareTo(new Node(p)) < 0) {
            Node node = n.right();
            if (node == null) {
                Node nodeToBeInserted = new Node(p);
                n.setRight(nodeToBeInserted);
                nodeToBeInserted.setParent(n);
                nodeToBeInserted.setLevel(n.level() + 1);
                System.out.print(p);
                System.out.print(" Parent point  ");
                System.out.print(n.point());
                System.out.print(" Right \n");

                return;
            }
            insert(p, node);
            return;
        }

        Node node = n.left();
        if (node == null) {
            Node nodeToBeInserted = new Node(p);
            n.setLeft(nodeToBeInserted);
            nodeToBeInserted.setParent(n);
            nodeToBeInserted.setLevel(n.level() + 1);
            System.out.print(p);
            System.out.print(" Parent point ");
            System.out.print(n.point());
            System.out.print(" Left \n");

            return;
        }
        insert(p, node);
    }
    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {

        return this.contains(root, p);
    }

    private boolean contains(Node root, Point2D point) {
        if (root == null) {
            return false;
        }
        if (root.point().equals(point)) {
            return true;
        }

        if (root.compareTo(new Node(point)) > 0) {
            return contains(root.left(), point);
        }
        return contains(root.right(), point);
    }
    // does the set contain point p?

    public void draw() {
        StdDraw.clear();
        this.draw(root);
    }

    private void draw(Node n) {
        if (n == null) {
            return;
        }
        n.draw();
        draw(n.right());
        draw(n.left());
    }
    // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<>();
        return this.range(rect, root, points);
    }

    private Iterable<Point2D> range(RectHV rect, Node root, ArrayList<Point2D> points) {

        if (root.rectHV().intersects(rect)) {
            if (rect.contains(root.point())) {
                points.add(root.point());
            }
            range(rect, root.left(), points);
            range(rect, root.right(), points);
        }
        return points;
    }

    // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D point) {
        Point2D nearestPoint = new Point2D(1, 1);
        return nearest(point, this.root, nearestPoint, 1);
    }

    private Point2D nearest(Point2D point, Node root, Point2D nearestPoint,
                            double smallestDistance) {
        double distance = root.rectHV().distanceTo(point);
        if (distance < smallestDistance) {
            double distanceToRoot = root.point().distanceTo(point);
            if (distanceToRoot < smallestDistance) {
                smallestDistance = distanceToRoot;
                nearestPoint = root.point();
            }
            nearest(point, root.right(), nearestPoint, smallestDistance);
            nearest(point, root.left(), nearestPoint, smallestDistance);
        }
        return nearestPoint;
    }

    // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }
}
