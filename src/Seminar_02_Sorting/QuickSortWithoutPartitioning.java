package Seminar_02_Sorting;

import java.util.Random;

public class QuickSortWithoutPartitioning {

    int counter = 0;

    void swap(int[] a, int low, int high) {
        int temp = a[low];
        a[low] = a[high];
        a[high] = temp;
        counter++;
    }

    int generateRandomPivot(int low, int high) {
        Random random = new Random();
        return random.nextInt(high - low + 1) + low;
    }

    void sort(int[] a, int low, int high) {
        if (low < high) {
            int pivotIndex = generateRandomPivot(low, high);
            int pivotValue = a[pivotIndex];

            swap(a, pivotIndex, high);

            int i = low - 1;

            for(int j = low; j < high; j++) {
                counter++;
                if(a[j] < pivotValue) {
                    i++;
                    counter++;
                    swap(a, i, j);
                }
            }

            swap(a, i + 1, high);

            sort(a, low, i);
            sort(a, i + 2, high);
        }
    }

    int getCounter() {
        return counter;
    }
}
