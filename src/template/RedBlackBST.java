package template;
import java.util.LinkedList;
import java.util.Queue;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private Node root;     // root of the BST

    private class Node {
        private boolean color;     // color of parent link
        private int N;             // subtree count
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees

        public Node(Key key, Value val, boolean color, int N) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.N = N;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return (x.color == RED);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    } 

    public int size() { return size(root); }

    public boolean isEmpty() {
        return root == null;
    }

    public Value get(Key key) { return get(root, key); }

    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    public boolean containsKey(Key key) {
        return (get(key) != null);
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value val) { 
        if (h == null) return new Node(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, val); 
        else if (cmp > 0) h.right = put(h.right, key, val); 
        else              h.val   = val;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;

        return h;
    }

    public void removeMin() {
        if (!isRed(root.left) && !isRed(root.right)) // if both children of root are black, set root to red
            root.color = RED;

        root = removeMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node removeMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = removeMin(h.left);
        return balance(h);
    }

    public void removeMax() {
        if (!isRed(root.left) && !isRed(root.right)) // if both children of root are black, set root to red
            root.color = RED;

        root = removeMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node removeMax(Node h) { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = removeMax(h.right);

        return balance(h);
    }

    public void remove(Key key) { 
        if (!isRed(root.left) && !isRed(root.right)) // if both children of root are black, set root to red
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, Key key) { 
        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = firstKey(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = removeMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
        }
        return h;
    }

    private Node balance(Node h) {
        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public Key firstKey() {
        if (isEmpty()) return null;
        return firstKey(root).key;
    } 

    private Node firstKey(Node x) { 
        if (x.left == null) return x; 
        else                return firstKey(x.left); 
    } 

    public Key lastKey() {
        if (isEmpty()) return null;
        return lastKey(root).key;
    } 

    private Node lastKey(Node x) { 
        if (x.right == null) return x; 
        else                 return lastKey(x.right); 
    } 

    public Key floorKey(Key key) {
        Node x = floorKey(root, key);
        if (x == null) return null;
        else           return x.key;
    }    

    private Node floorKey(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floorKey(x.left, key);
        Node t = floorKey(x.right, key);
        if (t != null) return t; 
        else           return x;
    }

    public Key ceilingKey(Key key) {  
        Node x = ceilingKey(root, key);
        if (x == null) return null;
        else           return x.key;  
    }

    private Node ceilingKey(Node x, Key key) {  
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0)  return ceilingKey(x.right, key);
        Node t = ceilingKey(x.left, key);
        if (t != null) return t; 
        else           return x;
    }

    public Key select(int k) { // the key of rank k
        if (k < 0 || k >= size())  return null;
        Node x = select(root, k);
        return x.key;
    }

    private Node select(Node x, int k) { // the key of rank k in the subtree rooted at x
        int t = size(x.left); 
        if      (t > k) return select(x.left,  k); 
        else if (t < k) return select(x.right, k-t-1); 
        else            return x; 
    } 

    public int rank(Key key) { // number of keys less than key
        return rank(key, root);
    } 

    private int rank(Key key, Node x) { // number of keys less than key in the subtree rooted at x
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if      (cmp < 0) return rank(key, x.left); 
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else              return size(x.left); 
    } 

    public Iterable<Key> keys() { // all of the keys, as an Iterable
        return keys(firstKey(), lastKey());
    }

    public Iterable<Key> keys(Key lo, Key hi) { // the keys between lo and hi, as an Iterable
        Queue<Key> queue = new LinkedList<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key); 
        if (cmphi > 0) keys(x.right, queue, lo, hi); 
    } 

    public int size(Key lo, Key hi) { // number keys between lo and hi 
        if (lo.compareTo(hi) > 0) return 0;
        if (containsKey(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    public static void main(String[] args) { 
    }
}