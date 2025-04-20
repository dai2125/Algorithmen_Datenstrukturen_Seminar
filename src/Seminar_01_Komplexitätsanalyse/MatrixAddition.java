package Seminar_01_Komplexitätsanalyse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class MatrixAddition extends JPanel {

    int[] sizes;
    long[][] addTimes;
    long[][] mulTimes;
    int minSize;

    MatrixAddition(int[] sizes, long[][] addTimes, long[][] mulTimes) {
        this.sizes = sizes;
        this.addTimes = addTimes;
        this.mulTimes = mulTimes;
        minSize = sizes[0];
    }

    static int[][] addMatricesThreadSupport(int[][] A, int[][] B) throws InterruptedException {
        int n = A.length;
        int[][] result = new int[n][n];

        int threadsCount = Runtime.getRuntime().availableProcessors(); // 8 Threads
        Thread[] threads = new Thread[threadsCount];

        for (int t = 0; t < threadsCount; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < n; i++) {
                    final int row = i;
                    for (int j = 0; j < n; j++) {
                        int sum = 0;
                        for (int k = 0; k < n; k++) {
                            sum += A[row][k] + B[k][j];
                        }
                        result[row][j] = sum;
                    }
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        return result;
    }

    static int[][] mulMatricesThreadSupport(int[][] A, int[][] B) throws InterruptedException {
        int n = A.length;
        int[][] result = new int[n][n];

        int threadsCount = Runtime.getRuntime().availableProcessors(); // z. B. 8 Threads
        Thread[] threads = new Thread[threadsCount];

        for (int t = 0; t < threadsCount; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < n; i++) {
                    final int row = i;
                    for (int j = 0; j < n; j++) {
                        int sum = 0;
                        for (int k = 0; k < n; k++) {
                            sum += A[row][k] * B[k][j];
                        }
                        result[row][j] = sum;
                    }
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        return result;
    }

    static int[][] generateRandomMatrix(int n) {
        int[][] matrix = new int[n][n];
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = rand.nextInt(1, 100);
        return matrix;
    }

    static boolean searchMatrix(int[][] matrix, int target) {
        printMatrixInformation(matrix);
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == target) {
                    printFoundNullValue(target, i, j);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int maxSize = sizes[sizes.length - 1];
        int maxTime = (int) mulTimes[mulTimes.length - 1][mulTimes.length - 1];

        g2.drawLine(50, height - 50, width - 50, height - 50);
        g2.drawLine(50, height - 50, 50, 50);

        for (int i = 0; i < sizes.length; i++) {
            int x = 50 + (sizes[i] * (width - 100) / maxSize);
            g2.drawLine(x, height - 50, x, height - 45);
            g2.drawString(Integer.toString(sizes[i]), x - 10, height - 30);
        }

        for (int i = 0; i < addTimes.length; i++) {
            int y = height - 50 - ((int) addTimes[i][i] * (height - 100) / maxTime);
            g2.drawLine(45, y, 50, y);
            g2.drawString("" + addTimes[i][i], 5, y + 5);
        }

        int startX = 50;
        int startY = height - 50;

        Color[] colors = {Color.GREEN, Color.BLUE, Color.MAGENTA, Color.RED, Color.YELLOW, Color.ORANGE};

        int y = 50;
        int a = 0;
        int b = 3;
        Label label;

        for (int i = 0; i < sizes.length; i++) {
            g2.setColor(colors[i]);
            g2.drawLine(startX, startY, startX + sizes[i] * (width - 100) / maxSize, startY - (int) (addTimes[i][i] * (height - 100) / maxTime));
            g2.drawLine(startX, startY, startX + sizes[i] * (width - 100) / maxSize, startY - (int) (mulTimes[i][i] * (height - 100) / maxTime));
            y = y + 25;
            label = new Label("Calculation Add: " + (i + 1) + ", n: " + sizes[i] + ", time: " + addTimes[i][i]);
            g2.drawString(label.getText(), 75, y);

            y = y + 25;
            label = new Label("Calculation Mul: " + (i + 1) + ", n: " + sizes[i] + ", time: " + mulTimes[i][i]);
            g2.drawString(label.getText(), 75, y);
        }
    }

    static void printMatrixInformation(int[][] matrix) {
        System.out.println("Matrix length: " + matrix.length + " : " + matrix[0].length);
    }

    static void printFoundNullValue(int target, int i, int j) {
        System.out.println("Target " + target + " found at index " + i + " and " + j);
    }

    static void printAdditionInformation(int sizes, long addTimes) {
        System.out.println("Add - Size: " + sizes + ", time: " + addTimes);
    }

    static void printMultiplicationInformation(int sizes, long mulTimes) {
        System.out.println("Mul - Size: " + sizes + ", time: " + mulTimes);
    }

    public static void main(String[] args) throws InterruptedException {

        int[] sizes = {250, 500, 1000};
        long[][] addTimes = new long[sizes.length][sizes.length];
        long[][] mulTimes = new long[sizes.length][sizes.length];

        for (int i = 0; i < sizes.length; i++) {

            int[][] A = generateRandomMatrix(sizes[i]);
            int[][] B = generateRandomMatrix(sizes[i]);
            int[][] C;

            long start = System.currentTimeMillis();
            C = addMatricesThreadSupport(A, B);
            long end = System.currentTimeMillis();
            addTimes[i][i] = end - start;

            printAdditionInformation(sizes[i], addTimes[i][i]);
            searchMatrix(C, 0);

            start = System.currentTimeMillis();
            C = mulMatricesThreadSupport(A, B);
            end = System.currentTimeMillis();
            mulTimes[i][i] = end - start;

            printMultiplicationInformation(sizes[i], mulTimes[i][i]);
            searchMatrix(C, 0);
        }

        JFrame frame = new JFrame("n3 Diagram");
        MatrixAddition chart = new MatrixAddition(sizes, addTimes, mulTimes);
        frame.add(chart);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
//            int[][] A = {
//                    {9, 8, 7},
//                    {6, 5, 4},
//                    {3, 2, 1}
//            };
//
//            int[][] B = {
//                    {1, 2, 3},
//                    {4, 5, 6},
//                    {7, 8, 9}
//            };
