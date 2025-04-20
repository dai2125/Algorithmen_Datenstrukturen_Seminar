package Seminar_01_Komplexitätsanalyse;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MatrixAddition extends JPanel {

    private final int[] sizes;
    private final long[][] times;
    long[][] mulTimes;
    int minSize;

    public MatrixAddition(int[] sizes, long[][] times, long[][] mulTimes) {
        this.sizes = sizes;
        this.times = times;
        this.mulTimes = mulTimes;
        minSize = sizes[0];
    }

    public static boolean searchMatrix(int[][] matrix, int target) {

        System.out.println("Matrix length: " + matrix.length + " : " + matrix[0].length);
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == target) {
                    System.out.println("Target " + target + " found at index " + i + " and " + j);
                    return false;
                }
            }
        }
        System.out.println("End of searchMatrix");
        return true;
    }


    public static int[][] addMatricesThreadSupport(int[][] A, int[][] B) throws InterruptedException {
        int n = A.length;
        int[][] result = new int[n][n];

        int threadsCount = Runtime.getRuntime().availableProcessors(); // 8 Threads
        Thread[] threads = new Thread[threadsCount];

        AtomicInteger rowCounter = new AtomicInteger(0);
        AtomicInteger entriesProcessed = new AtomicInteger(0);

        for(int t = 0 ; t < threadsCount; t++) {
            threads[t] = new Thread(() -> {
                for(int i = 0 ; i < n ; i++) {
                    final int row = i;
                    for(int j = 0 ; j < n ; j++) {
                        int sum = 0;
                        for(int k = 0 ; k < n ; k++) {
                            sum += A[row][k] + B[k][j];
                            // sum = A[row][k] * B[k][j];
                        }
//                        int count = entriesProcessed.incrementAndGet();
//                        if (count % 10_000_000 == 0) {
//                            System.out.println(count + " Einträge verarbeitet");
//                        }
                        result[row][j] = sum;
                    }
                }
            });
            threads[t].start();
        }

//        for (int t = 0; t < threadsCount; t++) {
//            threads[t] = new Thread(() -> {
//                int row;
//                while ((row = rowCounter.getAndIncrement()) < n) {
//                    for (int col = 0; col < n; col++) {
//                        result[row][col] = A[row][col] + B[row][col];
//
//                        int count = entriesProcessed.incrementAndGet();
//                        if (count % 10_000_000 == 0) {
//                            System.out.println(count + " Einträge verarbeitet");
//                        }
//                    }
//                }
//            });
//            threads[t].start();
//        }

        for (Thread thread : threads) {
            thread.join();
        }

        return result;
    }

    public static int[][] mulMatricesThreadSupport(int[][] A, int[][] B) throws InterruptedException {
        int n = A.length;
        int[][] result = new int[n][n];

        int threadsCount = Runtime.getRuntime().availableProcessors(); // z. B. 8 Threads
        Thread[] threads = new Thread[threadsCount];

        AtomicInteger rowCounter = new AtomicInteger(0);
        AtomicInteger entriesProcessed = new AtomicInteger(0);

        for(int t = 0 ; t < threadsCount; t++) {
            threads[t] = new Thread(() -> {
                for(int i = 0 ; i < n ; i++) {
                    final int row = i;
                    for(int j = 0 ; j < n ; j++) {
                        int sum = 0;
                        for(int k = 0 ; k < n ; k++) {
                            // sum += A[row][k] + B[k][j];
                            sum = A[row][k] * B[k][j];
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



    public static int[][] addMatricesThreadSupport2(int[][] A, int[][] B) throws InterruptedException {
        int n = A.length;
        int[][] result = new int[n][n];

        Thread[] threads = new Thread[n];

        //int[] progress = new int[n];
        AtomicInteger progress = new AtomicInteger(0);
        AtomicInteger a = new AtomicInteger(0);

        // int[] counter = {0};

        for (int i = 0; i < n; i++) {
            final int row = i;
            int finalI = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < n; j++) {
                    int sum = 0;
                    for (int k = 0; k < n; k++) {
                        sum += A[row][k] + B[k][j];
                    }
                    result[row][j] = sum;

                    if(row == 1000 && j == 1000 || row == 2000 && j == 2000 || row == 3000 && j == 3000 || row == 4000 && j == 4000 || row == 5000 && j == 5000
                        || row == 6000 && j == 6000 || row == 7000 && j == 7000 || row == 8000 && j == 8000 || row == 9000 && j == 9000) {
                        System.out.println("Progress: " + row + " : " + j);
                    }

//                    if(row == 2500 && j == 2500 || row == 5000 && j == 5000 || row == 7500 && j == 7500 || row == 8000 && j == 8000 || row == 9000 && j == 9000) {
//                        System.out.println("Progress: " + row + " : " + j);
//                    }


//                    int counter = a.incrementAndGet();
//                    if (counter % 12500 == 0) { // Beispiel: alle 125M Einträge
//                        System.out.println(counter + " Einträge verarbeitet");
//                    }

//                    if(counter == 12500 || counter == 25000 || counter == 37500 || counter == 50000 || counter == 62500 || counter == 75000 || counter == 87500 || counter == 95000) {
//                        System.out.println(counter + " einträge verarbeitet");
//                    }

//                    counter[0]++;
//                    int currentProgress = progress.addAndGet(sum);
//                    if(counter[0] == 12500 || counter[0] == 25000 || counter[0] == 37500 || counter[0] == 50000 || counter[0] == 62500 || counter[0] == 75000 || counter[0] == 87500 || counter[0] == 95000) {
//                        System.out.println(counter[0] + " einträge verarbeitet");
//                    }

                }
            });
            threads[i].start();
        }

        for(Thread thread : threads) {
            thread.join();
        }
        return result;
    }

    static void printProgress(int[] progress, int n) {
        int totalProgress = 0;
        for(int p : progress) {
            totalProgress += p;
        }

        int completed = totalProgress / (n * n / 8);
        if(completed > 0 && completed <= 8) {
            System.out.println(completed + "/8 des Arrays fertig");
        }
    }

    public static int[][] mulMatricesThreadSupport2(int[][] A, int[][] B) throws InterruptedException {
        int n = A.length;
        int[][] result = new int[n][n];

        Thread[] threads = new Thread[n];

        for (int i = 0; i < n; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < n; j++) {
                    int sum = 0;
                    for (int k = 0; k < n; k++) {
                        sum += A[row][k] * B[k][j];
                    }
                    result[row][j] = sum;
                }
            });
            threads[i].start();
        }

        for(Thread thread : threads) {
            thread.join();
        }
        return result;
    }

    public static int[][] addMatrices(int[][] A, int[][] B) throws InterruptedException {
        int n = A.length;
        int[][] result = new int[n][n];

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
        return result;
    }

    public static int[][] generateRandomMatrix(int n) {
        int[][] matrix = new int[n][n];
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = rand.nextInt(1, 100);
        return matrix;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int maxSize = sizes[sizes.length - 1];
        int maxTime = (int)times[times.length - 1][times.length - 1];

        g2.drawLine(50, height - 50, width - 50, height - 50);
        g2.drawLine(50, height - 50, 50, 50);

        for(int i = 0; i < sizes.length; i++) {
            int x = 50 + (sizes[i] * (width - 100) / maxSize);
            g2.drawLine(x, height - 50, x, height - 45);
            g2.drawString(Integer.toString(sizes[i]), x - 10, height - 30);
        }


        for(int i = 0; i < times.length; i++) {
            int y = height - 50 - ((int)times[i][i] * (height - 100) / maxTime);
            g2.drawLine(45, y, 50, y);
            g2.drawString("" + times[i][i], 5, y + 5);
        }

        int startX = 50;
        int startY = height - 50;

        Color[] colors = {Color.GREEN, Color.BLUE, Color.MAGENTA, Color.RED, Color.YELLOW, Color.ORANGE};

        int y = 50;
        Label label;
        for(int i = 0; i < sizes.length; i++) {
            g2.setColor(colors[i]);
            g2.drawLine(startX, startY, startX + sizes[i] * (width - 100) / maxSize, startY - (int)(times[i][i] * (height - 100) / maxTime));
            g2.drawLine(startX, startY, startX + sizes[i] * (width - 100) / maxSize, startY - (int)(mulTimes[i][i] * (height - 100) / maxTime));
            // g2.drawString(labels[i], startX + (sizes[i] * (width - 100) / maxSize) - 150, startY - (int)(times[i][i] * (height - 100) / maxTime));
            y = y + 25;
            label = new Label("Calculation Add: " + (i + 1) + ", n: " + sizes[i] + ", times: " + times[i][i]);
            g2.drawString(label.getText(), 75, y);

            y = y + 25;
            label = new Label("Calculation Mul: " + (i + 1) + ", n: " + sizes[i] + ", times: " + mulTimes[i][i]);
            g2.drawString(label.getText(), 75, y);

            // g2.drawString(labels[i + 1], 75, y);

                    // labels[i] = "Calculation Add " + (i + 1) + ", n: " + sizes[i] + ", times: " + times[i][i];

        }

/*
        g2.setColor(Color.GREEN);
//        float dash0[] = {2.0f};
//        BasicStroke dashed0 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash0, 0.0f);
//        g2.setStroke(dashed0);
        g2.drawLine(startX, startY, startX + sizes[0] * (width - 100) / maxSize, startY - (int)(times[0][0] * (height - 100) / maxTime));
        g2.drawString(labels[0], startX + (sizes[0] * (width - 100) / maxSize) - 150, startY - (int)(times[0][0] * (height - 100) / maxTime));

        g2.setColor(Color.BLUE);
//        float dash1[] = {4.0f};
//        BasicStroke dashed1 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
//        g2.setStroke(dashed1);
        //g2.drawLine(startX, startY, startX + sizes[1], startY - sizes[1]);
        g2.drawLine(startX, startY, startX + sizes[1] * (width - 100) / maxSize, startY - (int)(times[1][1] * (height - 100) / maxTime));
        g2.drawString(labels[1], startX + (sizes[1] * (width - 100) / maxSize) - 150, startY - (int)(times[1][1] * (height - 100) / maxTime));

        g2.setColor(Color.RED);
//        float dash2[] = {6.0f};
//        BasicStroke dashed2 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f);
//        g2.setStroke(dashed2);
        g2.drawLine(startX, startY, startX + sizes[2] * (width - 100) / maxSize, startY - (int)(times[2][2] * (height - 100) / maxTime));
        g2.drawString(labels[2], startX + (sizes[2] * (width - 100) / maxSize) - 150, startY - (int)(times[2][2] * (height - 100) / maxTime));

//        g2.drawLine(startX, startY, startX + sizes[2], startY - sizes[2]);
*/
    }

    public static void main(String[] args) throws InterruptedException {
        // int n = 10000;

        int[] sizes = {1000, 2500, 5000};
        long[][] times = new long[sizes.length][sizes.length];
        long[][] mulTimes = new long[sizes.length][sizes.length];
        int[][] D = new int[10][10];

        for(int i = 0; i < sizes.length; i++) {

            int[][] A = generateRandomMatrix(sizes[i]);
            int[][] B = generateRandomMatrix(sizes[i]);

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
            int[][] C; //= new int[3][3];

            long start = System.currentTimeMillis();
            C = addMatricesThreadSupport(A, B);
            long end = System.currentTimeMillis();
            times[i][i] = end - start;

            System.out.println("Add - Size: " + sizes[i] + ", time: " + times[i][i]);
            searchMatrix(C, 0);

            start = System.currentTimeMillis();
            C = mulMatricesThreadSupport(A, B);
            end = System.currentTimeMillis();
            mulTimes[i][i] = end - start;

            System.out.println("Mul - Size: " + sizes[i] + ", time: " + times[i][i]);

//            for(int j = 0; j < 3; j++) {
//                for(int k = 0; k < 3; k++) {
//                    System.out.print(C[j][k] + " ");
//                }
//                System.out.println();
//            }

            searchMatrix(C, 0);
        }

        /*
        int[][] A = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        int[][] B = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        */


        JFrame frame = new JFrame("Liniendiagramm Beispiel");
        MatrixAddition chart = new MatrixAddition(sizes, times, mulTimes);
        frame.add(chart);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add - Size: 1000, time: 3207
        //Matrix length: 1000 : 1000
        //End of searchMatrix
        //Mul - Size: 1000, time: 3207
        //Matrix length: 1000 : 1000
        //End of searchMatrix
        //Add - Size: 2500, time: 131727
        //Matrix length: 2500 : 2500
        //End of searchMatrix
        //Mul - Size: 2500, time: 131727
        //Matrix length: 2500 : 2500
        //End of searchMatrix
        //Add - Size: 5000, time: 1875093
        //Matrix length: 5000 : 5000
        //End of searchMatrix

    }
}
