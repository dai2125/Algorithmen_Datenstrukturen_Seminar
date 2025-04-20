package Seminar_02_Sorting;

public class QuickSortMiddle {
    int counter = 0;

    int partition(int a[], int low, int high) {
        int middle = (low + high) / 2;
        int pivot = a[middle];

        int temp = a[middle];
        a[middle] = a[high];
        a[high] = temp;

        int i = low - 1;

        for(int j = low; j < high; j++) {
            counter++;
            if(a[j] <= pivot) {
                i++;
                counter++;
                temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;

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
