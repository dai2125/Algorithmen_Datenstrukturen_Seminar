package Seminar_02_Sorting;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestSorting {

    int min = 1;
    int max = 500;

    static long b1 = 0;
    
    Random random = new Random();
    int[] n = {10, 15, 20, 25, 50, 100, 200, 500, 1000, 2000, 3000, 10000, 20000, 40000, 90000, 1000000};

    int[] result0 = new int[n.length];
    int[] result1 = new int[n.length];
    int[] result2 = new int[n.length];
    int[] result3 = new int[n.length];
    int[] result4 = new int[n.length];
    int[] result5 = new int[n.length];

    int[] temp0;
    int[] temp1;
    int[] temp2;
    int[] temp3;
    int[] temp4;
    int[] temp5;
    
    @Test
    public void testSort() {
        long startTime = System.currentTimeMillis();
        long endTime = 0;

        for (int i = 0; i < n.length; i++) {

            QuickSort quickSort = new QuickSort();
            QuickSortRandomPivot quickSortRandomPivot = new QuickSortRandomPivot();
            QuickSortHoraesPartition quickSortHoraesPartition = new QuickSortHoraesPartition();
            QuickSortWithoutPartitioning quickSortWithoutPartitioning = new QuickSortWithoutPartitioning();
            QuickSortMiddle quickSortMiddle = new QuickSortMiddle();
            MergeSort mergeSort = new MergeSort();

            int[] unSorted = randomIntArray(min, max, n[i]);
            temp0 = Arrays.copyOf(unSorted, unSorted.length);
            temp1 = Arrays.copyOf(unSorted, unSorted.length);
            temp2 = Arrays.copyOf(unSorted, unSorted.length);
            temp3 = Arrays.copyOf(unSorted, unSorted.length);
            temp4 = Arrays.copyOf(unSorted, unSorted.length);
            temp5 = Arrays.copyOf(unSorted, unSorted.length);

            long[] quickSortTime = new long[n.length];
            long[] quickSortHoreasTime = new long[n.length];
            long[] quickSortWithoutPartitioningTime = new long[n.length];
            long[] mergeSortTime = new long[n.length];
            long[] quickSortRandomPivotTime = new long[n.length];
            long[] quickSortMiddleTime = new long[n.length];

            long start = 0;
            long end = 0;

            printN(n[i]);

            start = System.nanoTime();
            quickSort.sort(temp0, 0, n[i] - 1);
            result0[i] = quickSort.getCounter();
            end = System.nanoTime();
            b1 += quickSortTime[i] = end - start;
            // System.out.println(printTime(start, end, "QuickSort"));


            start = System.nanoTime();
            quickSortRandomPivot.sort(temp1, 0, n[i] - 1);
            result1[i] = quickSortRandomPivot.getCounter();
            end = System.nanoTime();
            b1 += quickSortRandomPivotTime[i] = end - start;
            // System.out.println(start, end, "QuickSortRandomPivot"));
            

            start = System.nanoTime();
            quickSortHoraesPartition.sort(temp2, 0, n[i] - 1);
            result2[i] = quickSortHoraesPartition.getCounter();
            end = System.nanoTime();
            b1 += quickSortHoreasTime[i] = end - start;
            // System.out.println(start, end, "Horaes"));


            start = System.nanoTime();
            quickSortWithoutPartitioning.sort(temp3, 0, n[i] - 1);
            result3[i] = quickSortWithoutPartitioning.getCounter();
            end = System.nanoTime();
            b1 += quickSortWithoutPartitioningTime[i] = end - start;
            // System.out.println(start, end, "QuickSortWithoutPartioning"));


            start = System.nanoTime();
            quickSortMiddle.sort(temp4, 0, n[i] - 1);
            result4[i] = quickSortMiddle.getCounter();
            end = System.nanoTime();
            b1 += quickSortMiddleTime[i] = end - start;
            //System.out.println(start, end, "QuickSortMiddle"));

            start = System.nanoTime();
            mergeSort.sort(temp5, 0, n[i] - 1);
            result5[i] = mergeSort.getCounter();
            end = System.nanoTime();
            b1 += mergeSortTime[i] = end - start;
            //System.out.println(start, end, "MergeSort"));

            assertTrue(isSorted(temp0));
            assertTrue(isSorted(temp1));
            assertTrue(isSorted(temp2));
            assertTrue(isSorted(temp3));
            assertTrue(isSorted(temp4));
            assertTrue(isSorted(temp5));

            List<Result> results = new ArrayList<>();
            results.add(new Result("QuickSort", result0, quickSortTime[i], quickSort.getCounter(), n[i] - 1));
            results.add(new Result("RandomPivot", result1, quickSortRandomPivotTime[i], quickSortRandomPivot.getCounter(), n[i] - 1));
            results.add(new Result("Horaes", result2, quickSortHoreasTime[i], quickSortHoraesPartition.getCounter(), n[i] - 1));
            results.add(new Result("WithoutPartitiong", result3, quickSortWithoutPartitioningTime[i], quickSortWithoutPartitioning.getCounter(), n[i] - 1));
            results.add(new Result("MiddlePivot", result4, quickSortMiddleTime[i], quickSortMiddle.getCounter(), n[i] - 1));
            results.add(new Result("MergeSort", result5, mergeSortTime[i], mergeSort.getCounter(), n[i] - 1));

            results.sort(Comparator.comparingLong(Result::getTime));
           for(Result result : results) {
               if(result.getName().equals("MergeSort")) {
                   assertTrue(result.getSteps() <= result.getWorstCase() * 1.5);
               } else if(result.getName().equals("Horaes")) {
                   // TODO Puffer das sich die Schritte immer am oberen Ende befinden, nur bei kleinen Datenmengen, kann Fehler auslösen
                   assertTrue(result.getSteps() <= result.getWorstCase() + 10 && result.getSteps() >= result.getBestCase());
               } else {
                   assertTrue(result.getSteps() <= result.getWorstCase() && result.getSteps() >= result.getBestCase());
               }
               System.out.println(result.toString());
           }
        }

        endTime = System.currentTimeMillis();
        printTotalTime(endTime, startTime);
        printTotalTimeCheck(getTotalTime());
    }

    static void printTotalTime(long end, long start) {
        System.out.println("Total time: " + (end - start));
    }

    static void printTotalTimeCheck(long totalTime) {
        System.out.println("Total Time Check: " + totalTime / 1_000_000);
    }

    static <T extends Comparable<T>> boolean isSorted(T[] a) {
        for(int i = 0; i < a.length - 1; i++) {
            if(a[i].compareTo(a[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isSorted(int[] arr) {
        for(int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    static int[] randomIntArray(int min, int max, int n) {
        if (min >= max || n < 0) {
            throw new IllegalArgumentException();
        }
        int[] theInts = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; ++i){
            theInts[i] = rand.nextInt(max - min) + min;
        }
        return theInts;
    }
    
    static void print(int[] arr) {
        System.out.println("PRINTING START");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("\nPRINTING END");
    }
    
    static String printTime(long start, long end, String name) {
        totalTime(end - start);
        return "Time taken: " + (end - start) + " ns, Algorithm: " + name;
    }

    static void totalTime(long num) {
        b1 += num;
    }

    static long getTotalTime() {
        return b1;
    }

    static void printN(int n) {
        System.out.println("N = " + n);
    }
}
