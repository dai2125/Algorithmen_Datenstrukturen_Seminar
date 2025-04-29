package Aufgabe_03_Trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BST<T extends Comparable<T>> {

    int nodeCount = 0;

    Node root = null;

    final static String OPTIONS = "ENTER: " +
            "\n\t{ -successor -value } [PRINTS SUCCESSOR]" +
            "\t{ -predecessor -value } [PRINTS PREDECESSOR]" +
            "\t{ -add -value } [ADDS VALUE]" +
            "\n\t{ -print } [PRINTS BINARY SEARCH TREE]" +
            "\t\t{ -remove -value } [REMOVES VALUE]" +
            "\t\t\t\t{ -update -old value -new value } [UPDATES VALUE] " +
            "\n\t{ -inorder } [PRINTS IN ORDER TRAVERSAL]" +
            "\t{ -preorder } [PRINTS PRE ORDER TRAVERSAL]" +
            "\t\t{ -postorder } [PRINTS POST ORDER TRAVERSAL]" +
            "\t{ -level } [PRINTS LEVEL ORDER TRAVERASAL]" +
            "\n\t{ -exit } [SHUTS DOWN PROGRAM]"
            ;

    final static String WRONGINPUT = "Invalid input";
    final static String ENTERTWONUMBERS = "Enter two numbers";
    final static String REMOVESUCCESSFULL = "Remove successful";
    final static String UPDATESUCCESSFULL = "Update successful";
    final static String REMOVEFAILED = "Remove failed";
    final static String UPDATEFAILED = "Update failed";
    final static String NODENOTFOUND = "Node not found";
    final static String SUCCESSOR = "The successor value of ";
    final static String PREDECESSOR = "The predecessor value of ";
    final static String IS = " is ";
    final static String ADDSUCCESSFULL = "Added successful";
    final static String ADDFAILED = "Add failed";
    final static String PRINT = "print";
    final static String REMOVE = "remove";
    final static String UPDATE = "update";
    final static String EXIT = "exit";
    final static String LEVEL = "level";
    final static String PREDECESSORS = "predecessor";
    final static String SUCCESSORS = "successor";
    final static String PREORDER = "preorder";
    final static String POSTORDER = "postorder";
    final static String INORDER = "inorder";
    final static String ADD = "add";


    private class Node {
        T data;
        Node left, right;

        public Node(Node left, Node right, T elem) {
            this.data = elem;
            this.left = left;
            this.right = right;
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
            printAddFailed();
            return false;
        } else {
            root = add(root, elem);
            nodeCount++;
            printAddSuccessfull();
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
        return node;
    }

    boolean remove(T elem) {
        if (contains(elem)) {
            root = remove(root, elem);
            nodeCount--;
            printRemoveSuccessFull();
            return true;
        }
        printRemoveFailed();
        return false;
    }

    Node remove(Node node, T elem) {

        if (node == null) return null;

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
        return node;
    }

    Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    Node findMax(Node node) {
        while (node.right != null) node = node.right;
        return node;
    }

    boolean contains(T elem) {
        return contains(root, elem);
    }

    boolean search(T value) {
        return searchRecursive(root, value);
    }

    boolean searchRecursive(Node current, T value) {
        if(current == null) {
            return false;
        }
        int cmp = value.compareTo(current.data);
        if(cmp == 0) {
            return true;
        }
        return cmp < 0 ? searchRecursive(current.left, value) : searchRecursive(current.right, value);
    }

    boolean contains(Node node, T elem) {

        if (node == null) return false;

        int cmp = elem.compareTo(node.data);

        if (cmp < 0) return contains(node.left, elem);
        else if (cmp > 0) return contains(node.right, elem);
        else return true;
    }

    int height() {
        return height(root);
    }

    int height(Node node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    T minimum() {
        Node min = root;
        while(min.left != null) {
            min = min.left;
        }
        return min.data;
    }

    T maximum() {
        Node max = root;
        while(max.right != null) {
            max = max.right;
        }
        return max.data;
    }

    T successor(T elem) {
        Node node = findNode(root, elem);
        if (node == null) return null;

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
        if (node == null) return null;

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
        if (node == null) return null;

        if (node.left != null) {
            return findMax(node.left);
        }
        return null;
    }

    Node successorNode(T elem) {
        Node node = findNode(root, elem);
        if (node == null) return null;

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
            printNodeNotFound();
            return;
        }

        if (node != null && node.data != newValue) {
            Node pre = predecessorNode(node.data);
            Node suc = successorNode(node.data);
            int smaller;
            int greater;

            if(pre == null) {
                pre = node;
            }
            if(suc == null) {
                suc = node;
            }

            smaller = newValue.compareTo(pre.data);
            greater = newValue.compareTo(suc.data);
            if(greater < 0 && smaller > 0) {
                node.data = newValue;
                printUpdateSuccessFull();
            } else {
                printUpdateFailed();
            }
        }
    }

    void printTraversalList() {
        List nodes = new ArrayList<Node>();

        addNodesToList(root, nodes);

        System.out.println();
        for(int i = 0; i < nodes.size(); i++) {
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

    void printInOrderTraversal() {
        Iterator iterator = traverse(TreeTraversalOrder.IN_ORDER);
        while(iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    void printPostOrderTraversal() {
        Iterator iterator = traverse(TreeTraversalOrder.POST_ORDER);
        while(iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    void printPreOrderTraversal() {
        Iterator iterator = traverse(TreeTraversalOrder.PRE_ORDER);
        while(iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    void printLevelTraverse() {
        Iterator iterator = traverse(TreeTraversalOrder.LEVEL_ORDER);
        while(iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    java.util.Iterator<T> levelOrderTraversal() {

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
        if(node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data);
            printSubTree(node.right, prefix + (isTail ? "    " : "│   "), false);
            printSubTree(node.left, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    static void printOptions() {
        System.out.println(OPTIONS);
    }

    static void printWrongInput() {
        System.out.println(WRONGINPUT);
    }

    static void printUpdate() {
        System.out.println(ENTERTWONUMBERS);
    }

    static boolean isNumeric(String str) {
        if(str.matches("[0-9]{1,3}")) {
            return true;
        }
        return false;
    }

    static boolean isNotNumeric(String str) {
        return !isNumeric(str);
    }

    static void printRemoveSuccessFull() {
        System.out.println(REMOVESUCCESSFULL);
    }

    static void printRemoveFailed() {
        System.out.println(REMOVEFAILED);
    }

    static void printUpdateSuccessFull() {
        System.out.println(UPDATESUCCESSFULL);
    }

    static void printUpdateFailed() {
        System.out.println(UPDATEFAILED);
    }

    static void printNodeNotFound() {
        System.out.println(NODENOTFOUND);
    }

    static void printSuccessor() {
        System.out.print(SUCCESSOR);
    }

    static void printPredecessor() {
        System.out.print(PREDECESSOR);
    }

    static void printIs() {
        System.out.print(IS);
    }

    static void printValue(String text) {
        System.out.print(text);
    }

    static void printAddFailed() {
        System.out.println(ADDFAILED);
    }

    static void printAddSuccessfull() {
        System.out.println(ADDSUCCESSFULL);
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] values = { 55, 40, 28, 10, 30, 60, 95, 88, 68, 90, 63, 84, 99, 98 };

        for (int val : values) bst.add(val);
        Scanner scanner = new Scanner(System.in);
        String[] input = new String[3];

        while(true) {
            printOptions();
            input = scanner.nextLine().split("\\s+");

            if (input[0].equalsIgnoreCase(EXIT)) {
                break;
            } else if (input[0].equalsIgnoreCase(LEVEL)) {
                bst.printLevelTraverse();
            } else if (input[0].equalsIgnoreCase(PRINT)) {
                bst.printTree();
            } else if (input[0].equalsIgnoreCase(INORDER)) {
                bst.printInOrderTraversal();
            } else if (input[0].equalsIgnoreCase(POSTORDER)) {
                bst.printPostOrderTraversal();
            } else if (input[0].equalsIgnoreCase(PREORDER)) {
                bst.printPreOrderTraversal();
            } else if(input.length == 2) {
                if (input[0].equalsIgnoreCase(ADD) && !input[1].equals("") && isNumeric(input[1])) {
                    bst.add(Integer.parseInt(input[1]));
                } else if(input[0].equalsIgnoreCase(REMOVE) && !input[1].equals("") && isNumeric(input[1])) {
                    bst.remove(Integer.parseInt(input[1]));
                } else if (input[0].equalsIgnoreCase(SUCCESSORS) && !input[1].equals("") && isNumeric(input[1])) {
                    printSuccessor();
                    printValue(input[1]);
                    printIs();
                    System.out.println(bst.successor(Integer.parseInt(input[1])));
                } else if (input[0].equalsIgnoreCase(PREDECESSORS) && !input[1].equals("") && isNumeric(input[1])) {
                    printPredecessor();
                    printValue(input[1]);
                    printIs();
                    System.out.println(bst.predecessor(Integer.parseInt(input[1])));
                } else {
                    printWrongInput();
                }
            } else if(input.length == 3) {
                if(input[0].equalsIgnoreCase(UPDATE) && !input[1].equals("") && !input[2].equals("") && isNumeric(input[1]) && isNumeric(input[2])) {
                    bst.updateNode(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                } else {
                    printWrongInput();
                }
            } else {
                printWrongInput();
            }
        }
    }
}