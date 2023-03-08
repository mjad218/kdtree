/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    private Node root;

    private int size = 0;

    static class Node {
        private Point2D point;
        private Node right;

        private int level;
        private Node left;

        Node(Point2D point) {
            this.point = point;
        }

        void setRight(Node n) {
            this.right = n;
        }

        void setLeft(Node n) {
            this.left = n;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int level() {
            return this.level;
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


        public int compareTo(Point2D that) {
            if (this.level % 2 == 0) {
                if (this.point.x() == that.x()) {
                    return 0;
                }
                return this.point.x() - that.x() > 0 ? 1 : -1;
            }
            if (this.point.y() == that.y()) {
                return 0;
            }
            return this.point.y() - that.y() > 0 ? 1 : -1;
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
            return;
        }

        this.insert(p, root);

    }

    private void insert(Point2D p, Node n) {

        if (p.compareTo(n.point()) == 1) {
            Node node = n.right();
            if (node == null) {
                Node nodeToBeInserted = new Node(p);
                node.setRight(nodeToBeInserted);
                return;
            }
            insert(p, node);
        }

        Node node = n.left();
        if (node.left() == null) {
            Node nodeToBeInserted = new Node(p);
            node.setLeft(nodeToBeInserted);
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
        if (root.right() != null && point.compareTo(root.right().point()) == 1) {
            return contains(root.right(), point);
        }
        return root.left() != null && contains(root.left(), point);

    }
    // does the set contain point p?

    public void draw() {
        this.draw(root);
    }

    private void draw(Node n) {
        if (n == null) {
            return;
        }
        n.point().draw();
        draw(n.right());
        draw(n.left());
    }
    // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
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
