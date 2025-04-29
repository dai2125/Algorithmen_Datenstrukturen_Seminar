package Aufgabe_03_Trees;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TestBST<T extends Number> {

    static int[] values = { 55, 40, 28, 10, 30, 60, 95, 88, 68, 90, 63, 84, 99, 98 };
    static BST<Integer> bst = new BST<>();

    @Test
    void testAdd() {
        initializeBinarySearchTree();

        bst.add(100);
        bst.add(200);
        bst.add(300);
        bst.add(400);

        assertTrue(bst.contains(100));
        assertTrue(bst.contains(200));
        assertTrue(bst.contains(300));
        assertTrue(bst.contains(400));
    }

    @Test
    void testRemove() {
        initializeBinarySearchTree();

        bst.remove(88);
        assertFalse(bst.search(88));

        bst.remove(10);
        assertFalse(bst.search(10));

        bst.remove(40);
        assertFalse(bst.search(40));
    }

    @Test
    void testUpdateNode() {
        initializeBinarySearchTree();

        bst.updateNode(95,97);
        assertTrue(bst.search(97));

        bst.updateNode(55,56);
        assertTrue(bst.search(56));

        bst.updateNode(28,11);
        assertTrue(bst.search(11));

        bst.updateNode(88,91);
        assertFalse(bst.search(91));

        bst.updateNode(10,28);
        assertFalse(bst.search(28));

        bst.updateNode(40,27);
        assertFalse(bst.search(27));
    }

    @Test
    void testPredecessor() {
        initializeBinarySearchTree();
        assertTrue(bst.predecessor(55) == 40);
        assertTrue(bst.predecessor(40) == 30);
        assertTrue(bst.predecessor(28) == 10);
        assertTrue(bst.predecessor(10) == null);
        assertTrue(bst.predecessor(30) == 28);
        assertTrue(bst.predecessor(60) == 55);
        assertTrue(bst.predecessor(95) == 90);
        assertTrue(bst.predecessor(88) == 84);
        assertTrue(bst.predecessor(68) == 63);
        assertTrue(bst.predecessor(90) == 88);
        assertTrue(bst.predecessor(63) == 60);
        assertTrue(bst.predecessor(84) == 68);
        assertTrue(bst.predecessor(99) == 98);
        assertTrue(bst.predecessor(98) == 95);
    }

    @Test
    void testSuccecessor() {
        initializeBinarySearchTree();
        assertTrue(bst.successor(55) == null);
        assertTrue(bst.successor(40) == 55);
        assertTrue(bst.successor(28) == 30);
        assertTrue(bst.successor(10) == 28);
        assertTrue(bst.successor(30) == 40);
        assertTrue(bst.successor(60) == 63);
        assertTrue(bst.successor(95) == 98);
        assertTrue(bst.successor(88) == 90);
        assertTrue(bst.successor(68) == 84);
        assertTrue(bst.successor(90) == 95);
        assertTrue(bst.successor(63) == 68);
        assertTrue(bst.successor(84) == 88);
        assertTrue(bst.successor(99) == null);
        assertTrue(bst.successor(98) == 99);
    }

    @Test
    void testMinimum() {
        initializeBinarySearchTree();
        assertTrue(bst.minimum() == 10);
    }

    @Test
    void testMaximum() {
        initializeBinarySearchTree();
        assertTrue(bst.maximum() == 99);
    }

    @Test
    public void testInorderTraversal() {
        BST<Integer> bst = new BST<>();
        int[] input = { 55, 40, 28, 10, 30, 60, 95, 88, 68, 90, 63, 84, 99, 98 };

        for (int val : input) bst.add(val);

        int[] expected = input.clone();
        Arrays.sort(expected);

        Iterator<Integer> it = bst.inOrderTraversal();

        int index = 0;
        while (it.hasNext()) {
            int actual = it.next();
            assertEquals(expected[index], actual);
            index++;
        }

        assertEquals(expected.length, index);
    }

    @Test
    public void testPreorderTraversal() {
        BST<Integer> bst = new BST<>();
        int[] input = { 55, 40, 28, 10, 30, 60, 95, 88, 68, 90, 63, 84, 99, 98 };
        int[] expected = { 55, 40, 28, 10, 30, 60, 95, 88, 68, 63, 84, 90, 99, 98 };

        for (int val : input) bst.add(val);

        Iterator<Integer> it = bst.preOrderTraversal();

        int index = 0;
        while (it.hasNext()) {
            int actual = it.next();
            assertEquals(expected[index], actual);
            index++;
        }

        assertEquals(expected.length, index);
    }

    @Test
    public void testPostorderTraversal() {
        BST<Integer> bst = new BST<>();
        int[] input = { 55, 40, 28, 10, 30, 60, 95, 88, 68, 90, 63, 84, 99, 98 };
        int[] expected = { 10, 30, 28, 40, 63, 84, 68, 90, 88, 98, 99, 95, 60, 55 };

        for (int val : input) bst.add(val);

        Iterator<Integer> it = bst.postOrderTraversal();

        int index = 0;
        while (it.hasNext()) {
            int actual = it.next();
            assertEquals(expected[index], actual);
            index++;
        }

        assertEquals(expected.length, index);
    }

    @Test
    public void testLevelTraversal() {
        BST<Integer> bst = new BST<>();
        int[] input = { 55, 40, 28, 10, 30, 60, 95, 88, 68, 90, 63, 84, 99, 98 };
        int[] expected = { 55, 40, 60, 28, 95, 10, 30, 88, 99, 68, 90, 98, 63, 84 };

        for (int val : input) bst.add(val);

        Iterator<Integer> it = bst.traverse(TreeTraversalOrder.LEVEL_ORDER);

        int index = 0;
        while (it.hasNext()) {
            int actual = it.next();
            assertEquals(expected[index], actual);
            index++;
        }

        assertEquals(expected.length, index);
    }

    static void initializeBinarySearchTree() {
        for(int value: values) {
            bst.add(value);
        }
    }
}