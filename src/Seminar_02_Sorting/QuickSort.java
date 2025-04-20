package Seminar_02_Sorting;

public class QuickSort {

    int counter = 0;

    int partition(int a[], int low, int high) {
        int pivot = a[high];
        int i = low - 1;

        for(int j = low; j < high; j++) {
            counter++;
            if(a[j] <= pivot) {
                counter++;
                i++;

                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        int temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;

        return i + 1;
    }

    void sort(int a[], int low, int high) {
        if(low < high) {
            int pivot = partition(a, low, high);

            sort(a, low, pivot - 1);
            sort(a, pivot + 1, high);
        }
    }

    int partition(char a[], int low, int high) {
        int pivot = a[high];
        int i = low - 1;
        counter++;

        for(int j = low; j < high; j++) {
            if(a[j] <= pivot) {
                i++;

                char temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        char temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;

        return i + 1;
    }

    void sort(char a[], int low, int high) {
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
