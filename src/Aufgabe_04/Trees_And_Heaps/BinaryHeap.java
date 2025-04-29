package Aufgabe_04.Trees_And_Heaps;

import java.util.ArrayList;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {

    private static final int ROOT = 0;

    private Type type = Type.MIN;
    private T[] arr;
    private int n;

    private Node<T> root;

    public BinaryHeap(int size) {
        this.n = 0;
        this.arr = (T[]) new Comparable[size + 0];
    }

    T extractMin() {
        T min = getMin();
        swap(this.arr, 0, n-1);
        this.n--;
        if (0 < this.n) minHeapify(0);
        return min;
    }

    void minHeapify(int idx) {
        if (idx >= n) {
            System.out.println("\nNo element found or index out of heap capacity!\n");
            return;
        }

        int smallest = idx;
        int left = 2 * idx + 1;
        int right = 2 * idx + 2;

        if (left < n && arr[left].compareTo(arr[smallest]) < 0) {
            smallest = left;
        }

        if (right < n && arr[right].compareTo(arr[smallest]) < 0) {
            smallest = right;
        }

        if (smallest != idx) {
            swap(arr, idx, smallest);
            minHeapify(smallest);
        }
    }

    void swap(T[] a, int p1, int p2) {
        if (p1<0 || p1>a.length-1 || p2<0 || p2>a.length-1) return;
        T temp = a[p1];
        a[p1] = a[p2];
        a[p2] = temp;
    }

    void swap(int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    T getMin() {
        return arr[0];
    }

    boolean compare(int i, int j) {
        switch (type) {
            case MAX:
                return arr[i].compareTo(arr[j]) < 0;
            case MIN:
                return arr[i].compareTo(arr[j]) > 0;
        }
        return false;
    }

    void moveDown(int parent) {
        int largest = parent;
        int left = 2 * parent;
        int right = 2 * parent + 1;

        if (left <= n && compare(largest, left)) {
            largest = left;
        }

        if (right <= n && compare(largest, right)) {
            largest = right;
        }

        if (largest != parent) {
            swap(parent, largest);
            moveDown(largest);
        }
    }

    void moveUp(int child) {
        int parent = (child - 1) / 2;

        while (child > ROOT && compare(parent, child)) {
            swap(child, parent);
            child = parent;
            parent = (child - 1) / 2;
        }
    }

    T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Binary Heap is empty!");
        }

        return arr[0];
    }

    T poll() {
        if (isEmpty()) {
            throw new IllegalStateException("Binary Heap is empty!");
        }

        T item = arr[0];
        swap(ROOT, n);
        moveDown(ROOT);
        arr[0] = null;
        n--;

        return item;
    }

    boolean isEmpty() {
        return n == 0;
    }

    int size() {
        return n;
    }

    void insert(T value) {
        n++;
        arr[n - 1] = value;
        moveUp(n - 1);
    }

    boolean insertRecursive(Node<T> current, T value) {
        int cmp = value.compareTo(current.value);
        if(cmp < 0) {
            if(current.left == null) {
                current.left = new Node<>(value, current);
                return true;

            }
            return insertRecursive(current.left, value);
        } else if(cmp > 0) {
            if(current.right == null) {
                current.right = new Node<>(value, current);
                return true;
            }
            return insertRecursive(current.right, value);
        }
        return false;
    }

    Node<T> search(T value) {
        return searchRecursive(root, value);
    }

    Node<T> searchRecursive(Node<T> current, T value) {
        if(current == null) {
            return null;
        }
        int cmp = value.compareTo(current.value);
        if(cmp == 0) {
            return current;
        }
        return cmp < 0 ? searchRecursive(current.left, value) : searchRecursive(current.right, value);
    }

    T minimum() {
        Node<T> min = root;
        while(min.left != null) {
            min = min.left;
        }
        return min.value;
    }

    T maximum() {
        Node<T> max = root;
        while(max.right != null) {
            max = max.right;
        }
        return max.value;
    }

    T predecessor(T value) {
        Node<T> node = search(value);
        if(node == null) {
            return null;
        }

        if(node.left != null) {
            node = node.left;
            while(node.right != null) {
                node = node.right;
            }
            return node.value;
        }

        Node<T> parent = node.parent;
        while(parent != null && node == parent.left) {
            node = parent;
            parent = parent.parent;
        }
        return parent != null ? parent.value : null;
    }

    T successor(T value) {
        Node<T> node = search(value);
        if(node == null) {
            return null;
        }

        if(node.right != null) {
            node = node.right;
            while(node.left != null) {
                node = node.left;
            }
            return node.value;
        }

        Node<T> parent = node.parent;
        while(parent != null && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        return parent != null ? parent.value : null;
    }

    boolean remove(T value) {
        Node<T> node = search(value);
        if(node == null) {
            return false;
        }

        if(node.left == null && node.right == null) {
            transplant(node, null);
        }
        if(node.left == null) {
            transplant(node, node.right);
        } else if(node.right == null) {
            transplant(node, node.left);
        } else {
            Node<T> succ = getMinimumNode(node.right);
            //Node<T> succ = node.right;

            while(succ.left != null) {
                succ = succ.left;

                if(succ.parent != node) {
                    transplant(succ, succ.right);
                    succ.right = node.right;
                    succ.right.parent = succ;
                }
            }
        }
        return true;
    }

    void transplant(Node<T> u, Node<T> v) {
        if(u.parent == null) {
            root = v;
        } else if(u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }

        if(v != null) {
            v.parent = u.parent;
        }
    }

    Node<T> getMinimumNode(Node<T> node) {
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }

    List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    void inorderTraversal(Node<T> node, List<T> list) {
        if(node != null) {
            inorderTraversal(node.left, list);
            list.add(node.value);
            inorderTraversal(node.right, list);
        }
    }

//    boolean updateValue(T oldValue, T newValue) {
//        Node<T> node = search(oldValue);
//        if(node == null) {
//            return false;
//        }
//
//        T pred = predecessor(oldValue);
//        T succ = successor(oldValue);
//
//        if((pred == null || newValue.compareTo(pred) > 0) && (succ == null || newValue.compareTo(succ) < 0)) {
//            remove(oldValue);
//            return insert(newValue);
//        }
//        return false;
//    }

    enum Type {
        MIN,
        MAX
    }

    void print() {
        print(0, "", true);
    }

    void print(int idx, String prefix, boolean isTail) {
        if (idx < n && arr[idx] != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + arr[idx]);
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;

            if (right < n && arr[right] != null)
                print(left, prefix + (isTail ? "    " : "│   "), false);
            else
                print(left, prefix + (isTail ? "    " : "│   "), true);

            print(right, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    public static void main(String[] args) {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        //int[] values = { 9, 7, 8, 5, 4, 3 };
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(values.length);

        for (int val : values) binaryHeap.insert(val);

        System.out.println("Binary Heap:");
        binaryHeap.print();

        System.out.println("binaryHeap.extractMin(): " + binaryHeap.extractMin());
        binaryHeap.print();
    }
}