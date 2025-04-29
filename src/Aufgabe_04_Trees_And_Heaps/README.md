# BinaryHeap (Min-Heap)

Ein Generischer Binary Heap, unterstützt extractMin() für Min-Heaps. 

## Features

- Min-/Max-Heap via `Type.MIN` oder `Type.MAX` umschalten
- `insert(T value)` – Einfügen eines Elements
- `extractMin()` – Entfernt und gibt das Minimum (nur Min-Heap)
- `poll()` / `peek()` – Entnahme ohne/mit Entfernen
- `successor()` - Nachfolger
- `predecessor()` - Vorgänger
- `min()` - Minimum
- `max()` - Maximum
- `remove()` - löscht Node
- `add()` - fügt Node hinzu
- `balance()` - Neveliert sich nach jedem `remove()` und `add()`
 
## Verwendung

```java
BinaryHeap<Integer> heap = new BinaryHeap<>(10);
heap.insert(5);
heap.insert(2);
heap.insert(8);

System.out.println(heap.extractMin());  // → 2
System.out.println(heap.peek());        // → 5
```

## Tests
- `testInsertPeek()`
- `testExtractMin()`
- `testMultipleInsertsAndExtracts()`
- `testIsEmptyAndSize()`