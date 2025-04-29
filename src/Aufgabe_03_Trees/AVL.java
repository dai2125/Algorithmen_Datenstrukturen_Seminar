package Aufgabe_03_Trees;

import java.util.ArrayList;
import java.util.List;

public class AVL<T extends Comparable<T>> {

    int nodeCount = 0;

    Node root = null;

    private class Node {
        T data;
        Node left, right;
        int height;

        public Node(Node left, Node right, T elem) {
            this.data = elem;
            this.left = left;
            this.right = right;
            this.height = 1;
        }
    }

    boolean isEmpty() {
        return size() == 0;
    }

    int size() {
        return nodeCount;
    }

    boolean add(T elem) {

        if (contains(elem)) {
            return false;
        } else {
            root = add(root, elem);
            nodeCount++;
            balanceTree(root);
            return true;
        }
    }

    Node add(Node node, T elem) {
        if (node == null) {
            node = new Node(null, null, elem);
        } else {
            if (elem.compareTo(node.data) < 0) {
                node.left = add(node.left, elem);
            } else {
                node.right = add(node.right, elem);
            }
        }
        return balanceTree(node);
        //return node;
    }

    boolean remove(T elem) {
        if (contains(elem)) {
            root = remove(root, elem);
            nodeCount--;

            balanceTree(root);
            return true;
        }
        return false;
    }

    Node remove(Node node, T elem) {
        if (node == null) {
            return null;
        }

        int cmp = elem.compareTo(node.data);

        if (cmp < 0) {
            node.left = remove(node.left, elem);
        } else if (cmp > 0) {
            node.right = remove(node.right, elem);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node tmp = findMin(node.right);
                node.data = tmp.data;
                node.right = remove(node.right, tmp.data);
            }
        }
        //return balanceTree(root);
        return node;
    }

    Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    boolean contains(T elem) {
        return contains(root, elem);
    }

    boolean search(T value) {
        return searchRecursive(root, value);
    }

    boolean searchRecursive(Node current, T value) {
        if (current == null) {
            return false;
        }
        int cmp = value.compareTo(current.data);
        if (cmp == 0) {
            return true;
        }
        return cmp < 0 ? searchRecursive(current.left, value) : searchRecursive(current.right, value);
    }

    boolean contains(Node node, T elem) {
        if (node == null) {
            return false;
        }

        int cmp = elem.compareTo(node.data);

        if (cmp < 0) {
            return contains(node.left, elem);
        } else if (cmp > 0) {
            return contains(node.right, elem);
        } else {
            return true;
        }
    }

    int height() {
        return height(root);
    }

    int height(Node node) {
        if (node == null) {
            return 0;
        }
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    T minimum() {
        Node min = root;
        while (min.left != null) {
            min = min.left;
        }
        return min.data;
    }

    T maximum() {
        Node max = root;
        while (max.right != null) {
            max = max.right;
        }
        return max.data;
    }

    T successor(T elem) {
        Node node = findNode(root, elem);
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return findMin(node.right).data;
        }

        Node parent = findParent(root, node);
        while (parent != null && node == parent.right) {
            node = parent;
            parent = findParent(root, node);
        }
        return parent != null ? parent.data : null;
    }

    T predecessor(T elem) {
        Node node = findNode(root, elem);
        if (node == null) {
            return null;
        }

        if (node.left != null) {
            return findMax(node.left).data;
        }

        Node parent = findParent(root, node);
        while (parent != null && node == parent.left) {
            node = parent;
            parent = findParent(root, node);
        }
        return parent != null ? parent.data : null;
    }

    Node predecessorNode(T elem) {
        Node node = findNode(root, elem);
        if (node == null) {
            return null;
        }

        if (node.left != null) {
            return findMax(node.left);
        }
        return null;
    }

    Node successorNode(T elem) {
        Node node = findNode(root, elem);
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return findMin(node.right);
        }
        return null;
    }

    Node findNode(Node node, T elem) {
        while (node != null) {
            int cmp = elem.compareTo(node.data);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    void updateNode(T elem, T newValue) {
        Node node = findNode(root, elem);
        if (node == null) {
            System.out.println("node not found");
            return;
        }

        if (node != null && node.data != newValue) {
            Node pre = predecessorNode(node.data);
            Node suc = successorNode(node.data);
            int smaller;
            int greater;

            if (pre == null) {
                pre = node;
            }
            if (suc == null) {
                suc = node;
            }

            smaller = newValue.compareTo(pre.data);
            greater = newValue.compareTo(suc.data);
            System.out.println("smaller: " + smaller + " greater: " + greater);
            if (greater < 0 && smaller > 0) {
                node.data = newValue;
                System.out.println("smaller < 0 && greater > 0: node.data: " + node.data + ", pre.data: " + pre.data + ", suc.data: " + suc.data);
            } else {
                System.out.println("smaller < 0 && greater > 0: couldnt update value: elem: " + elem + ", newValue: " + newValue);
            }
        }
    }

    void printTraversalList() {
        List nodes = new ArrayList<Node>();

        addNodesToList(root, nodes);

        System.out.println();
        for (int i = 0; i < nodes.size(); i++) {
            System.out.print(nodes.get(i) + " ");
        }
    }

    void addNodesToList(Node node, List nodes) {
        if (node == null) return;

        addNodesToList(node.left, nodes);
        nodes.add(node.data);
        addNodesToList(node.right, nodes);
    }

    void printInorder(Node node) {
        if (node == null)
            return;

        printInorder(node.left);

        System.out.print(node.data + " ");

        printInorder(node.right);
    }

    Node findParent(Node root, Node child) {
        Node parent = null;
        Node current = root;
        while (current != null) {
            int cmp = child.data.compareTo(current.data);
            if (cmp < 0) {
                parent = current;
                current = current.left;
            } else if (cmp > 0) {
                parent = current;
                current = current.right;
            } else {
                return parent;
            }
        }
        return null;
    }

    java.util.Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER:
                return preOrderTraversal();
            case IN_ORDER:
                return inOrderTraversal();
            case POST_ORDER:
                return postOrderTraversal();
            case LEVEL_ORDER:
                return levelOrderTraversal();
            default:
                return null;
        }
    }

    java.util.Iterator<T> preOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                Node node = stack.pop();
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    java.util.Iterator<T> inOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {

                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }

                Node node = stack.pop();

                if (node.right != null) {
                    stack.push(node.right);
                    trav = node.right;
                }

                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    java.util.Iterator<T> postOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack1 = new java.util.Stack<>();
        final java.util.Stack<Node> stack2 = new java.util.Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node node = stack1.pop();
            if (node != null) {
                stack2.push(node);
                if (node.left != null) stack1.push(node.left);
                if (node.right != null) stack1.push(node.right);
            }
        }
        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack2.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return stack2.pop().data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    java.util.Iterator<T>   levelOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.offer(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                Node node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    void printTree() {
        printSubTree(root, "", true);
    }

    void printSubTree(Node node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data);
            printSubTree(node.right, prefix + (isTail ? "    " : "│   "), false);
            printSubTree(node.left, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    int Height(Node key) {
        if (key == null) {
            return 0;
        } else {
            return key.height;
        }
    }

    int Balance(Node key) {
        if (key == null) {
            return 0;
        } else {
            return ( Height(key.right) - Height(key.left) );
        }
    }

    void updateHeight(Node key) {
        int l = Height(key.left);
        int r = Height(key.right);

        key.height = Math.max(l , r) + 1;
    }

    Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    Node balanceTree(Node root) {
        updateHeight(root);

        int balance = Balance(root);

        if (balance > 1) { //R
            if (Balance(root.right) < 0) { //RL
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            } else { //RR
                return rotateLeft(root);
            }
        }

        if (balance < -1) { //L
            if (Balance(root.left) > 0) { //LR
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            } else { //LL
                return rotateRight(root);
            }
        }
        return root;
    }

    public static void main(String[] args) {
        AVL<Integer> avl = new AVL<>();
        //int[] values = {50, 30, 70, 20, 40, 60, 80, 30, 30, 20, 70};
        //int[] values = {55, 40, 28, 10, 30, 60, 95, 88, 68, 90, 63, 84, 99, 98};
        int[] values = { 10, 20, 30, 40, 50, 25 , 100, 70, 80, 60, 90, 26, 27, 28, 29, 30};


        for (int val : values) avl.add(val);

        System.out.println("Baumstruktur:");
        avl.printTree();

        System.out.println("Update Node");
       // avl.updateNode(90, 81);
        //System.out.println(updateNode3(28, 11));

//        avl.add(71);
//        avl.add(72);
//        avl.add(81);
//        avl.add(82);
//        avl.add(83);
//        avl.add(84);
//        avl.add(85);
//        avl.add(86);
//        avl.add(87);
//        avl.add(41);
//        avl.add(42);
//        avl.add(43);
//        avl.add(44);

        avl.remove(90);
        avl.remove(100);
        //avl.remove(80);
        avl.printTree();

    }
}
