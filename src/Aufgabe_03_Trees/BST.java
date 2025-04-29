package Aufgabe_03_Trees;

import java.util.ArrayList;
import java.util.List;

/**
 * This file contains an implementation of a Binary Search Tree (BST) Any comparable data is allowed
 * within this tree (numbers, strings, comparable Objects, etc...). Supported operations include
 * adding, removing, height, and containment checks. Furthermore, multiple tree traversal Iterators
 * are provided including: 1) Preorder traversal 2) Inorder traversal 3) Postorder traversal 4)
 * Levelorder traversal
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */

public class BST<T extends Comparable<T>> {

    // Tracks the number of nodes in this BST
    int nodeCount = 0;

    // This BST is a rooted tree so we maintain a handle on the root node
    Node root = null;

    // Internal node containing node references
    // and the actual node data
    private class Node {
        T data;
        Node left, right;

        public Node(Node left, Node right, T elem) {
            this.data = elem;
            this.left = left;
            this.right = right;
        }
    }

    // Check if this binary tree is empty
    boolean isEmpty() {
        return size() == 0;
    }

    // Get the number of nodes in this binary tree
    int size() {
        return nodeCount;
    }

    // Add an element to this binary tree. Returns true
    // if we successfully perform an insertion
    boolean add(T elem) {

        // Check if the value already exists in this
        // binary tree, if it does ignore adding it
        if (contains(elem)) {
            return false;

            // Otherwise add this element to the binary tree
        } else {
            root = add(root, elem);
            nodeCount++;
            return true;
        }
    }

    // Private method to recursively add a value in the binary tree
    Node add(Node node, T elem) {

        // Base case: found a leaf node
        if (node == null) {
            node = new Node(null, null, elem);

        } else {
            // Pick a subtree to insert element
            if (elem.compareTo(node.data) < 0) {
                node.left = add(node.left, elem);
            } else {
                node.right = add(node.right, elem);
            }
        }

        return node;
    }

    // Remove a value from this binary tree if it exists, O(n)
    boolean remove(T elem) {

        // Make sure the node we want to remove
        // actually exists before we remove it
        if (contains(elem)) {
            root = remove(root, elem);
            nodeCount--;
            return true;
        }
        return false;
    }

    Node remove(Node node, T elem) {

        if (node == null) return null;

        int cmp = elem.compareTo(node.data);

        // Dig into left subtree, the value we're looking
        // for is smaller than the current value
        if (cmp < 0) {
            node.left = remove(node.left, elem);

            // Dig into right subtree, the value we're looking
            // for is greater than the current value
        } else if (cmp > 0) {
            node.right = remove(node.right, elem);

            // Found the node we wish to remove
        } else {

            // This is the case with only a right subtree or
            // no subtree at all. In this situation just
            // swap the node we wish to remove with its right child.
            if (node.left == null) {
                return node.right;

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (node.right == null) {

                return node.left;

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. In this implementation I have decided to find the
                // smallest value in the right subtree which can be found by
                // traversing as far left as possible in the right subtree.
            } else {

                // Find the leftmost node in the right subtree
//                Node tmp = findMax(node.left);
                Node tmp = findMin(node.right);

                // Swap the data
                node.data = tmp.data;

                // Go into the right subtree and remove the leftmost node we
                // found and swapped data with. This prevents us from having
                // two nodes in our tree with the same value.
                node.right = remove(node.right, tmp.data);
//                node.left = remove(node.left, tmp.data);

                // If instead we wanted to find the largest node in the left
                // subtree as opposed to smallest node in the right subtree
                // here is what we would do:
                // Node tmp = findMax(node.left);
                // node.data = tmp.data;
                // node.left = remove(node.left, tmp.data);

            }
        }

        return node;
    }

    // Helper method to find the leftmost node (which has the smallest value)
    Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Helper method to find the rightmost node (which has the largest value)
    Node findMax(Node node) {
        while (node.right != null) node = node.right;
        return node;
    }

    // returns true is the element exists in the tree
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

    // private recursive method to find an element in the tree
    boolean contains(Node node, T elem) {

        // Base case: reached bottom, value not found
        if (node == null) return false;

        int cmp = elem.compareTo(node.data);

        // Dig into the left subtree because the value we're
        // looking for is smaller than the current value
        if (cmp < 0) return contains(node.left, elem);

            // Dig into the right subtree because the value we're
            // looking for is greater than the current value
        else if (cmp > 0) return contains(node.right, elem);

            // We found the value we were looking for
        else return true;
    }

    // Computes the height of the tree, O(n)
    int height() {
        return height(root);
    }

    // Recursive helper method to compute the height of the tree
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

    // Successor eines gegebenen Knotens
    T successor(T elem) {
        Node node = findNode(root, elem);
        if (node == null) return null;  // Element nicht gefunden

        // Wenn der Knoten einen rechten Unterbaum hat
        if (node.right != null) {
            return findMin(node.right).data;
        }

        // Ansonsten gehe nach oben zum Vorfahren
        Node parent = findParent(root, node);
        while (parent != null && node == parent.right) {
            node = parent;
            parent = findParent(root, node);
        }
        return parent != null ? parent.data : null;
    }

    // Predecessor eines gegebenen Knotens
    T predecessor(T elem) {
        Node node = findNode(root, elem);
        if (node == null) return null;  // Element nicht gefunden

        // Wenn der Knoten einen linken Unterbaum hat
        if (node.left != null) {
            return findMax(node.left).data;
        }

        // Ansonsten gehe nach oben zum Vorfahren
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

    // Hilfsmethode zum Finden des Knotens mit einem bestimmten Wert
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

            if(pre == null) {
                pre = node;
            }
            if(suc == null) {
                suc = node;
            }

            smaller = newValue.compareTo(pre.data);
            greater = newValue.compareTo(suc.data);
            System.out.println("smaller: " + smaller + " greater: " + greater);
            if(greater < 0 && smaller > 0) {
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


    // This method returns an iterator for a given TreeTraversalOrder.
    // The ways in which you can traverse the tree are in four different ways:
    // preorder, inorder, postorder and levelorder.
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



    // Returns as iterator to traverse the tree in pre order
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

    // Returns as iterator to traverse the tree in order
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

                // Dig left
                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }

                Node node = stack.pop();

                // Try moving down right once
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

    // Returns as iterator to traverse the tree in post order
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

    // Returns as iterator to traverse the tree in level order
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

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        //int[] values = {50, 30, 70, 20, 40, 60, 80, 30, 30, 20, 70};
        int[] values = { 55, 40, 28, 10, 30, 60, 95, 88, 68, 90, 63, 84, 99, 98 };

        for (int val : values) bst.add(val);

        System.out.println("Baumstruktur:");
        bst.printTree();

//        System.out.println("\nInorder Traversierung:");
//        System.out.println(bst.inorder());
//
//        System.out.println("\nMinimum: " + bst.minimum());
//        System.out.println("Maximum: " + bst.maximum());
//
//        System.out.println("\nPredecessor von 60: " + bst.predecessor(60));
//        System.out.println("Successor von 60: " + bst.successor(60));

//        System.out.println("\nUpdate 60 → 65:");
//        if (bst.updateValue(60, 65)) System.out.println("Update erfolgreich.");
//        else System.out.println("Update ungültig.");
//        bst.printTree();

//        System.out.println("\nEntferne 70:");
//        bst.remove(50);
//        bst.printTree();

        System.out.println("Update Node");
        bst.updateNode(95,97);
        //System.out.println(bst.updateNode3(28, 11));
        bst.printTree();

        //System.out.println("Suche nach 20: ");
        //System.out.println(bst.search(70));

        System.out.println("bst.minimum(): " + bst.minimum());
        System.out.println("bst.maximum(): " + bst.maximum());

        System.out.println("bst.successor(): " + bst.successor(20));
        System.out.println("bst.predecessor(): " + bst.predecessor(20));

        bst.printInorder(bst.root);
        bst.printTraversalList();


    }


}