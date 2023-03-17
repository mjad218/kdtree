/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

public class KdTree {

    private Node root;

    private int size = 0;

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node right;
        private Node parent;

        private Point2D[] limits;
        private int level;
        private Node left;

        Node(Point2D point) {
            this.point = point;
            this.right = null;
            this.left = null;
            this.parent = null;
            this.rect = null;
            this.limits = null;
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
            if (this.limits != null) {
                return this.limits;
            }
            Point2D p1 = null;
            Point2D p2 = null;
            double xCoor = this.point().x();
            double yCoor = this.point().y();
            Node parent = this.parent();
            Point2D[] parentLimits = { };
            double parentLimits1XCoor = 0, parentLimits1YCoor = 0, parentLimits2XCoor = 0,
                    parentLimits2YCoor = 0;
            if (parent != null) {
                parentLimits = parent.limits();
                parentLimits1XCoor = parentLimits[0].x();
                parentLimits1YCoor = parentLimits[0].y();
                // parentLimits2XCoor = parentLimits[1].x();
                // parentLimits2YCoor = parentLimits[1].y();
            }

            Node grandParent = null;
            Point2D[] grandParentLimits = { };
            double grandParentLimits1XCoor = 0, grandParentLimits1YCoor = 0, grandParentLimits2XCoor
                    = 0,
                    grandParentLimits2YCoor = 0;
            if (parent != null && parent.parent() != null) {
                grandParent = parent.parent();
                grandParentLimits = grandParent.limits();
                grandParentLimits1XCoor = grandParentLimits[0].x();
                grandParentLimits1YCoor = grandParentLimits[0].y();
                grandParentLimits2XCoor = grandParentLimits[1].x();
                grandParentLimits2YCoor = grandParentLimits[1].y();
            }

            if (parent == null) {
                double x1 = this.level % 2 == 0 ? xCoor : 0;
                double x2 = this.level % 2 == 0 ? xCoor : 1;
                double y1 = this.level % 2 != 0 ? yCoor : 0;
                double y2 = this.level % 2 != 0 ? yCoor : 1;

                p1 = new Point2D(x1, y1);
                p2 = new Point2D(x2, y2);
                Point2D[] points = { p1, p2 };
                this.limits = points;
                return points;
            }

            if (this.level % 2 == 0) {
                // vertical line
                double y1;
                double y2;
                y1 = parentLimits1YCoor;
                y2 = yCoor > y1 ? 1 : 0;
                if (grandParent != null) {
                    double grandPointLimitsYMin = Math.min(grandParentLimits1YCoor,
                                                           grandParentLimits2YCoor);
                    double grandPointLimitsYMax = Math.max(grandParentLimits1YCoor,
                                                           grandParentLimits2YCoor);
                    if (between(yCoor, y1, grandPointLimitsYMin)) {
                        y2 = grandPointLimitsYMin;
                    }
                    else if (between(yCoor, y1, grandPointLimitsYMax)) {
                        y2 = grandPointLimitsYMax;
                    }
                }
                double x = xCoor;
                p1 = new Point2D(x, y1);
                p2 = new Point2D(x, y2);
                Point2D[] points = { p1, p2 };
                this.limits = points;

                return points;
            }
            double x1;
            double x2;
            x1 = parentLimits1XCoor;
            x2 = xCoor > x1 ? 1 : 0;
            if (grandParent != null) {
                double grandPointLimitsXMin = Math.min(grandParentLimits1XCoor,
                                                       grandParentLimits2XCoor);
                double grandPointLimitsXMax = Math.max(grandParentLimits1XCoor,
                                                       grandParentLimits2XCoor);
                if (between(xCoor, x1, grandPointLimitsXMin)) {
                    x2 = grandPointLimitsXMin;
                }
                else if (between(xCoor, x1, grandPointLimitsXMax)) {
                    x2 = grandPointLimitsXMax;
                }
            }
            double y = yCoor;
            p1 = new Point2D(x1, y);
            p2 = new Point2D(x2, y);
            Point2D[] points = { p1, p2 };
            this.limits = points;
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
            // StdDraw.setPenColor(new Color(0, 0, 0, 80));
            // this.rectHV().draw();
        }

        public RectHV getRect() {
            if (this.rect == null) {
                this.rect = this.rectHV();
                return this.rect;
            }
            return this.rect;
        }

        public void setRect(RectHV r) {
            this.rect = r;
        }

        public RectHV rectHV() {
            Point2D[] limits = this.limits();
            double limit1X = limits[0].x(), limit2X = limits[1].x();
            double limit1Y = limits[0].y(), limit2Y = limits[1].y();
            Point2D[] parentLimits = null;
            double plimit1X = 0, plimit2X = 0;
            double plimit1Y = 0, plimit2Y = 0;

            if (this.parent() != null) {
                parentLimits = this.parent().limits();
                plimit1X = parentLimits[0].x();
                plimit2X = parentLimits[1].x();
                plimit1Y = parentLimits[0].y();
                plimit2Y = parentLimits[1].y();

            }

            if (this.level() % 2 == 0) {
                // vertical
                // y1 , y2
                double x1 = 0;
                double x2 = 1;

                if (this.parent() != null) {
                    x1 = Math.min(plimit1X, plimit2X);
                    x2 = Math.max(plimit1X, plimit2X);
                }
                double y1 = Math.min(limit1Y, limit2Y);
                double y2 = Math.max(limit1Y, limit2Y);

                return new RectHV(x1, y1, x2, y2);

            }
            double y1 = 0;
            double y2 = 1;

            if (this.parent() != null) {
                y1 = Math.min(plimit1Y, plimit2Y);
                y2 = Math.max(plimit1Y, plimit2Y);
            }
            double x1 = Math.min(limit1X, limit2X);
            double x2 = Math.max(limit1X, limit2X);

            return new RectHV(x1, y1, x2, y2);
        }

        public int compareTo(Node that) {
            if (this.level % 2 == 0) {
                if (this.point.x() == that.point().x()) {
                    return 0;
                }
                return this.point.x() > that.point().x() ? 1 : -1;
            }
            if (this.point.y() == that.point().y()) {
                return 0;
            }
            return this.point.y() > that.point().y() ? 1 : -1;
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
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (this.contains(p)) {
            return;
        }
        this.insert(p, root);
    }

    private void insert(Point2D p, Node n) {

        if (this.root == null) {
            this.root = new Node(p);
            this.size++;
            return;
        }
        if (n.compareTo(new Node(p)) <= 0) {
            // go right
            Node node = n.right();
            if (node == null) {
                Node nodeToBeInserted = new Node(p);
                n.setRight(nodeToBeInserted);
                nodeToBeInserted.setParent(n);
                nodeToBeInserted.setLevel(n.level() + 1);
                this.size++;
                return;
            }
            insert(p, node);
            return;
        }
        // go left
        Node node = n.left();
        if (node == null) {
            Node nodeToBeInserted = new Node(p);
            n.setLeft(nodeToBeInserted);
            nodeToBeInserted.setParent(n);
            nodeToBeInserted.setLevel(n.level() + 1);
            this.size++;
            return;
        }
        insert(p, node);
    }
    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return this.contains(root, p);
    }

    private boolean contains(Node root, Point2D point) {
        if (root == null) {
            return false;
        }
        if (root.point().equals(point)) {
            return true;
        }
        if (root.compareTo(new Node(point)) <= 0) {
            return contains(root.right(), point);
        }
        return contains(root.left(), point);
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
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Queue<Point2D> points = new Queue<>();
        this.range(rect, root, points);
        return points;
    }

    private void range(RectHV rect, Node root, Queue<Point2D> points) {
        if (root == null) {
            return;
        }
        if (rect.contains(root.point())) {
            points.enqueue(root.point());
        }
        if (rect.intersects(root.getRect())) {
            range(rect, root.left(), points);
            range(rect, root.right(), points);
        }
    }

    // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        if (this.root == null) {
            return null;

        }
        Point2D p = this.root.point();
        return nearest(point, this.root, p);
    }

    private Point2D nearest(Point2D point, Node root, Point2D nearestPoint) {
        if (root == null) {
            return nearestPoint;
        }
        double distance = root.getRect().distanceSquaredTo(point);
        double smallestDistance = nearestPoint.distanceSquaredTo(point);

        double distanceToRoot = root.point().distanceSquaredTo(point);
        if (distanceToRoot < smallestDistance) {
            nearestPoint = root.point();
        }
        if (distance <= smallestDistance) {
            if (root.right() != null && root.right().getRect().contains(point)) {
                nearestPoint = nearest(point, root.right(), nearestPoint);
                if (root.left() != null) {
                    nearestPoint = nearest(point, root.left(), nearestPoint);
                }
            }
            else {
                if (root.left() != null) {
                    nearestPoint = nearest(point, root.left(), nearestPoint);
                }
                if (root.right() != null) {
                    nearestPoint = nearest(point, root.right(), nearestPoint);
                }
            }
        }
        return nearestPoint;
    }

    // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }
}
