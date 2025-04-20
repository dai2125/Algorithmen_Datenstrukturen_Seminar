package Seminar_01_Komplexitätsanalyse;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    // TODO jeder Wert in der Reihe muss mit dem jeweiligen Wert in der Zeile multipliziert werden und dann addiert
    public static void main(String[] args) {

//        int[][] arr1 = {{1,2,3}, {4,5,6}, {7,8,9}}; //, {7,8,9}};
//        int[][] arr2 = {{1,2,3}, {4,5,6},{7,8,9}};
        boolean addition = false;
        int[] resultAddition = new int[2];
        int[] resultMultiplication = new int[2];
        long[] result = new long[6];

        for(int m = 0; m < 6; m++) {

            if(m > 2) {
                addition = true;
            }
            int n = 100;
            if(m == 0) {
                n = 100;
            } else if(m == 1) {
                n = 1000;
            } else if(m == 2) {
                n = 10000;
            } else if(m == 3) {
                n = 100;
            } else if(m == 4) {
                n = 1000;
            } else if(m == 5) {
                n = 10000;
            }
            int[][] arr1 = new int[n][n];
            int[][] arr2 = new int[n][n];
            int[][] ans = new int[n][n];
            Random random = new Random();

            if(addition) {
                System.out.println("Matrix addition of "+ n + " elements");
            } else {
                System.out.println("Matrix multiplication of "+ n + " elements");
            }

            // FILLING ARR1
            long startTime = System.nanoTime();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    arr1[i][j] = random.nextInt(10);
                }
            }

            long endTime = System.nanoTime();
            long duration = endTime - startTime / 1000000;
            System.out.println("filling arr1 with " + n + " random numbers:\t" + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));

            // FILLING ARR2
            startTime = System.nanoTime();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    arr2[i][j] = random.nextInt(10);
                }
            }

            endTime = System.nanoTime();
            duration = endTime - startTime / 1000000;
            System.out.println("filling arr2 with " + n + " random numbers:\t" + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));

            // FILLING ANS
            startTime = System.nanoTime();

            Graph graph = new Graph(n, n);

            for (int i = 0; i < ans.length; i++) {
                for (int j = 0; j < ans.length; j++) {
                    if(addition) {
                        graph.additionNode(i, j, arr1, arr2);
                    } else {
                        graph.addNode(i, j, arr1, arr2);
                    }
                }
            }

            endTime = System.nanoTime();
            duration = endTime - startTime / 1000000;
            System.out.println("filling the ans with " + n + "  the result:\t" + TimeUnit.NANOSECONDS.toMillis(endTime - startTime));

            result[m] = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        }

        System.out.println("COMPARISON RESULT");
        System.out.println("Multiplication of 100 elements\t\t" + result[0]);
        System.out.println("Addition of 100 elements\t\t\t" + result[3]);
        System.out.println("Multiplication of 1000 elements\t\t" + result[1]);
        System.out.println("Addition of 1000 elements\t\t\t" + result[4]);
        System.out.println("Multiplication of 10000 elements\t" + result[2]);
        System.out.println("Addition of 100000 elements\t\t\t" + result[5]);


        // PRINT ARRAYS
//        System.out.println("FirstNineElements ARR1");
//        for(int i = 0; i < 3; i++) {
//            for(int j = 0; j < 3; j++) {
//                System.out.print("[" + i + "][" + j + "]=" + arr1[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//        System.out.println("FirstNineElements ARR2");
//        for(int i = 0; i < 3; i++) {
//            for(int j = 0; j < 3; j++) {
//                System.out.print("[" + i + "][" + j + "]=" + arr2[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//        graph.printFirstNineElements();
//
//        System.out.println("Does the Graph contains Zeros? " + graph.check());
    }
}
