package Aufgabe_03.PDF;

public class Node<T extends Comparable<T>> {

    T value;
    Node<T> left, right, parent;

    Node(T value, Node<T> parent) {
        this.value = value;
        this.parent = parent;
    }
}
