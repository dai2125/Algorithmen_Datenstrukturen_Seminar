package Aufgabe_03_Trees;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {

    // TODO GUI https://github.com/dostonhamrakulov/Binary-Tree-in-Java-with-GUI/blob/master/BinaryTree/src/com/iidoston/TheWindow.java

    private Node<T> root;

    public boolean insert(T value) {
        if(root == null) {
            root = new Node<>(value, null);
            return true;
        }
        return insertRecursive(root, value);
    }

    private boolean insertRecursive(Node<T> current, T value) {
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

    public Node<T> search(T value) {
        return searchRecursive(root, value);
    }

    private Node<T> searchRecursive(Node<T> current, T value) {
        if(current == null) {
            return null;
        }
        int cmp = value.compareTo(current.value);
        if(cmp == 0) {
            return current;
        }
        return cmp < 0 ? searchRecursive(current.left, value) : searchRecursive(current.right, value);
    }

    public T minimum() {
        Node<T> min = root;
        while(min.left != null) {
            min = min.left;
        }
        return min.value;
    }

    public T maximum() {
        Node<T> max = root;
        while(max.right != null) {
            max = max.right;
        }
        return max.value;
    }

    public T predecessor(T value) {
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

    public T successor(T value) {
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

    public boolean remove(T value) {
        Node<T> node = search(value);
        if(node == null) {
            return false;
        }

        if(node.left == null && node.right == null) {
            System.out.println("remove0: " + node.left + " : " + node.right);
            transplant(node, null);
        }
        if(node.left == null) {
            System.out.println("remove1: " + node.right);
            transplant(node, node.right);
        } else if(node.right == null) {
            System.out.println("remove2: " + node.left);
            transplant(node, node.left);
        } else {
            System.out.println("remove3: " + node.value + " : " + node.left.value + " : " + node.right.value);
            Node<T> succ = getMinimumNode(node.right);
            //Node<T> succ = node.right;

            System.out.println("remove4: " + succ.value);
            while(succ.left != null) {
                System.out.println("remove5: " + succ.left.value);
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

    private void transplant(Node<T> u, Node<T> v) {
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

    private Node<T> getMinimumNode(Node<T> node) {
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(Node<T> node, List<T> list) {
        if(node != null) {
            inorderTraversal(node.left, list);
            list.add(node.value);
            inorderTraversal(node.right, list);
        }
    }

    public boolean updateValue(T oldValue, T newValue) {
        Node<T> node = search(oldValue);
        if(node == null) {
            return false;
        }

        T pred = predecessor(oldValue);
        T succ = successor(oldValue);

        if((pred == null || newValue.compareTo(pred) > 0) && (succ == null || newValue.compareTo(succ) < 0)) {
            remove(oldValue);
            return insert(newValue);
        }
        return false;
    }

    public void printTree() {
        printSubTree(root, "", true);
    }

    private void printSubTree(Node<T> node, String prefix, boolean isTail) {
        if(node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.value);
            printSubTree(node.left, prefix + (isTail ? "    " : "│   "), false);
            printSubTree(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }


    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] values = {50, 30, 70, 20, 40, 60, 80};

        for (int val : values) bst.insert(val);

        System.out.println("Baumstruktur:");
        bst.printTree();

        System.out.println("\nInorder Traversierung:");
        System.out.println(bst.inorder());

        System.out.println("\nMinimum: " + bst.minimum());
        System.out.println("Maximum: " + bst.maximum());

        System.out.println("\nPredecessor von 60: " + bst.predecessor(60));
        System.out.println("Successor von 60: " + bst.successor(60));

//        System.out.println("\nUpdate 60 → 65:");
//        if (bst.updateValue(60, 65)) System.out.println("Update erfolgreich.");
//        else System.out.println("Update ungültig.");
//        bst.printTree();

        System.out.println("\nEntferne 70:");
        bst.remove(70);
        bst.printTree();
    }






















}
