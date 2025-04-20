package Seminar_02_Sorting;

public class MergeSort {

    int counter = 0;

    void merge(int[] a, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for(int i = 0; i < n1; ++i) {
            left[i] = a[low + i];
        }

        for(int i = 0; i < n2; ++i) {
            right[i] = a[mid + 1 + i];
        }

        int i = 0;
        int j = 0;

        int k = low;

        while(i < n1 && j < n2) {
            counter++;
            if(left[i] <= right[j]) {
                a[k] = left[i];
                i++;
            } else {
                a[k] = right[j];
                j++;
            }
            k++;
        }

        while(i < n1) {
            counter++;
            a[k] = left[i];
            i++;
            k++;
        }

        while(j < n2) {
            counter++;
            a[k] = right[j];
            j++;
            k++;
        }
    }

    void sort(int[] a, int low, int high) {
        if(low < high) {
            int mid = (low + high) / 2;

            sort(a, low, mid);
            sort(a, mid + 1, high);

            merge(a, low, mid, high);
        }
    }

    int getCounter() {
        return counter;
    }
}
