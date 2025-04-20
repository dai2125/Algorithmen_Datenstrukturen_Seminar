package Seminar_02_Sorting;

import java.util.Random;

public class QuickSortRandomPivot {
    int counter = 0;

    void random(int a[], int low, int high) {
        Random random = new Random();
        int pivot = random.nextInt(high - low) + low;

        int temp = a[pivot];
        a[pivot] = a[high];
        a[high] = temp;
    }

    int partition(int arr[], int low, int high) {
        random(arr, low, high);
        int pivot = arr[high];
        counter++;

        int i = (low - 1);
        for(int j = low; j < high; j++) {
            counter++;
            if(arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                counter++;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    void sort(int a[], int low, int high) {
        if(low < high) {
            counter++;
            int pivot = partition(a, low, high);

            sort(a, low, pivot - 1);
            sort(a, pivot + 1, high);
        }
    }

    int getCounter() {
        return counter;
    }
}
