/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

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
                // this is working properly
                // WHY The fuck the others are not working?!!
                return points;
            }

            if (this.level % 2 == 0) {
                // vertical line
                Node parent = this.parent();
                Point2D[] parentLimits = parent.limits();
                // (0.2, 0) , (0.2, 1)
                // (0.5, 0.4)
                // Y SHOULD BE CONSTANT .4
                Node grandParent = parent.parent();
                double y1; // this.parent() != null ? this.parent().point().x() : 1;
                double y2;// n != null ? n.point().x() : (x1 == 1 ? 0 : 1);
                y1 = parentLimits[0].y();

                if (grandParent != null) {
                    // on the right or the left of the parent
                    // get the parent of the parent to get the other X!!
                    Point2D[] grandParentLimits = grandParent.limits();

                    y2 = grandParentLimits[0].y();
                }
                else {
                    y2 = this.point().y() > parent.point.y() ? 1 : 0;
                }

                double x = this.point().x();

                // double y = this.point().y();

                // HOW TO GET THE x ?

                p1 = new Point2D(x, y1);
                p2 = new Point2D(x, y2);

                Point2D[] points = { p1, p2 };
                return points;
            }

            Node parent = this.parent();

            Point2D[] parentLimits = parent.limits();
            // (0.2, 0) , (0.2, 1)

            // (0.5, 0.4)
            // Y SHOULD BE CONSTANT .4
            Node grandParent = parent.parent();
            double x1; // this.parent() != null ? this.parent().point().x() : 1;
            double x2;// n != null ? n.point().x() : (x1 == 1 ? 0 : 1);
            x1 = parentLimits[0].x();

            if (grandParent != null) {
                // on the right or the left of the parent
                // get the parent of the parent to get the other X!!
                Point2D[] grandParentLimits = grandParent.limits();

                x2 = grandParentLimits[0].x();
            }
            else {
                x2 = this.point().x() > parent.point.x() ? 1 : 0;
            }

            // double x = this.point().x();

            double y = this.point().y();

            // HOW TO GET THE x ?

            p1 = new Point2D(x1, y);
            p2 = new Point2D(x2, y);

            Point2D[] points = { p1, p2 };
            return points;
        }

        public void draw() {
            this.point.draw();
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
        // insert algorithm
        if (this.root == null) {
            this.root = new Node(p);
            System.out.println("null root");
            return;
        }

        this.insert(p, root);

    }

    private void insert(Point2D p, Node n) {
        System.out.println("null node");
        System.out.println(n);
        if (this.contains(p)) {
            return;
        }
        this.size++;
        if (n.compareTo(new Node(p)) > 0) {
            Node node = n.right();
            if (node == null) {
                Node nodeToBeInserted = new Node(p);
                n.setRight(nodeToBeInserted);
                nodeToBeInserted.setParent(n);
                nodeToBeInserted.setLevel(n.level() + 1);
                return;
            }
            insert(p, node);
        }

        Node node = n.left();
        if (node == null) {
            Node nodeToBeInserted = new Node(p);
            n.setLeft(nodeToBeInserted);
            nodeToBeInserted.setParent(n);
            nodeToBeInserted.setLevel(n.level() + 1);
            return;
        }
        insert(p, node);
    }
    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {

        return this.contains(root, p);
    }

    private boolean contains(Node root, Point2D point) {

        if (root != null && root.point().equals(point)) {
            return true;
        }
        if (root == null) {
            return false;
        }
        if (root.compareTo(new Node(point)) < 0) {
            return contains(root.left(), point);
        }

        if (root.compareTo(new Node(point)) > 0) {
            return contains(root.right(), point);
        }

        return false;

    }
    // does the set contain point p?

    public void draw() {
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
        return this.range(rect, root);
    }

    private Iterable<Point2D> range(RectHV rect, Node root) {


        return null;
    }

    // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D point) {
        return null;
    }
    // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }
}
