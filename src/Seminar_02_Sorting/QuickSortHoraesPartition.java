package Seminar_02_Sorting;

public class QuickSortHoraesPartition {
    int counter = 0;

    void swap(int[] arr, int i , int j) {
        counter++;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    int partition(int a[], int low, int high) {
        // int randomIndex = (low) + (int)(Math.random() * (high - low + 1));
        // swap(a, low, randomIndex);
        int pivot = a[low];
        int i = low - 1;
        int j = high + 1;

        while(true) {
            do {
                i++;
                counter++;
            } while(a[i] < pivot);
            do {
                j--;
                counter++;
            } while(a[j] > pivot);
            if(i >= j) {
                return j;
            }
            swap(a, i, j);
            counter++;
        }
    }

    void sort(int a[], int low, int high) {
        if(low < high) {
            counter++;
            int pivot = partition(a, low, high);

            sort(a, low, pivot);
            sort(a, pivot + 1, high);
        }
    }

    int getCounter() {
        return counter;
    }

}
