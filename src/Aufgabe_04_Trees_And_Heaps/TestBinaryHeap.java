package Aufgabe_04_Trees_And_Heaps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBinaryHeap {

    private BinaryHeap<Integer> heap;

    @BeforeEach
    void setUp() {
        heap = new BinaryHeap<>(10);
    }

    @Test
    void testInsertAndPeek() {
        heap.insert(50);
        heap.insert(20);
        heap.insert(30);

        assertEquals(20, heap.peek());
        assertEquals(3, heap.size());
    }

    @Test
    void testExtractMin() {
        heap.insert(10);
        heap.insert(30);
        heap.insert(5);
        heap.insert(40);

        int min = heap.extractMin();
        assertEquals(5, min);
        assertEquals(3, heap.size());

        assertEquals(10, heap.peek());
    }

    @Test
    void testMultipleInsertsAndExtracts() {
        int[] values = {25, 15, 30, 10, 20};
        for (int val : values) heap.insert(val);

        assertEquals(10, heap.extractMin());
        assertEquals(15, heap.extractMin());
        assertEquals(3, heap.size());
    }

    @Test
    void testIsEmptyAndSize() {
        assertTrue(heap.isEmpty());
        heap.insert(5);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
    }
}
