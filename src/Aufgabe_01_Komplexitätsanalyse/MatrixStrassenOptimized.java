package Aufgabe_01_Komplexitätsanalyse;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MatrixStrassenOptimized {
    private final int[][] data;
    private final int rows;
    private final int cols;

    private static final int MINIMUM_STRASSEN_THRESHOLD = 64;

    public MatrixStrassenOptimized(int[][] data) {
        this.data = deepCopy(data);
        this.rows = data.length;
        this.cols = data[0].length;
    }

    private static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    public int[][] getData() {
        return deepCopy(data); // Return a deep copy to maintain encapsulation
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void display() {
        Arrays.stream(data).map(Arrays::toString).forEach(System.out::println);
    }

    public static MatrixStrassenOptimized multiply(MatrixStrassenOptimized a, MatrixStrassenOptimized b, boolean parallel) {
        if (a.getCols() != b.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions incompatible for multiplication");
        }

        int[][] result = new int[a.getRows()][b.getCols()];

        if (parallel) {
            // Parallelizing initialization of result matrix to make it thread-safe
            Arrays.stream(result).parallel().forEach(row -> Arrays.fill(row, 0));

            // Start parallel multiplication using ForkJoinPool for better parallelism
            ForkJoinPool.commonPool().invoke(new ParallelMultiplication(a.data, b.data, result, 0, a.getRows()));
        } else {
            for (int i = 0; i < a.getRows(); i++) {
                for (int j = 0; j < b.getCols(); j++) {
                    int sum = 0;
                    for (int k = 0; k < a.getCols(); k++) {
                        sum += a.data[i][k] * b.data[k][j];
                    }
                    result[i][j] = sum;
                }
            }
        }

        return new MatrixStrassenOptimized(result);
    }

    private static class ParallelMultiplication extends RecursiveTask<Void> {
        private final int[][] a;
        private final int[][] b;
        private final int[][] result;
        private final int startRow;
        private final int endRow;
        private static final int THRESHOLD = 64; // If the submatrix is small enough, do direct multiplication to avoid overhead

        ParallelMultiplication(int[][] a, int[][] b, int[][] result, int startRow, int endRow) {
            this.a = a;
            this.b = b;
            this.result = result;
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        protected Void compute() {
            if (endRow - startRow <= THRESHOLD) {
                computeDirectly();
                return null;
            }

            int midRow = (startRow + endRow) / 2;
            ParallelMultiplication left = new ParallelMultiplication(a, b, result, startRow, midRow);
            ParallelMultiplication right = new ParallelMultiplication(a, b, result, midRow, endRow);

            right.fork();
            left.compute();
            right.join();

            return null;
        }

        private void computeDirectly() {
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    int sum = 0;
                    for (int k = 0; k < b.length; k++) {
                        sum += a[i][k] * b[k][j];
                    }
                    result[i][j] = sum;
                }
            }
        }
    }

    public static MatrixStrassenOptimized strassenMultiply(MatrixStrassenOptimized a, MatrixStrassenOptimized b) {
        if (a.getCols() != b.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions incompatible for multiplication");
        }

        int maxDim = Math.max(Math.max(a.getRows(), a.getCols()), Math.max(b.getRows(), b.getCols()));
        int paddedSize = nextPowerOfTwo(maxDim); // Padding to the next power of two to optimize Strassen's method

        int[][] paddedA = padMatrix(a.data, paddedSize);
        int[][] paddedB = padMatrix(b.data, paddedSize);
        int[][] paddedResult = strassenMultiplyRecursive(paddedA, paddedB);

        return new MatrixStrassenOptimized(extractResult(paddedResult, a.getRows(), b.getCols()));
    }

    private static int[][] strassenMultiplyRecursive(int[][] a, int[][] b) {
        int n = a.length;

        if (n <= MINIMUM_STRASSEN_THRESHOLD) {
            return standardMultiply(a, b);
        }

        int newSize = n / 2;

        int[][] a11 = new int[newSize][newSize];
        int[][] a12 = new int[newSize][newSize];
        int[][] a21 = new int[newSize][newSize];
        int[][] a22 = new int[newSize][newSize];
        int[][] b11 = new int[newSize][newSize];
        int[][] b12 = new int[newSize][newSize];
        int[][] b21 = new int[newSize][newSize];
        int[][] b22 = new int[newSize][newSize];

        divideMatrix(a, a11, a12, a21, a22);
        divideMatrix(b, b11, b12, b21, b22);

        // Calculate the 7 matrix multiplications (M1 to M7) using Strassen's method
        int[][] m1 = strassenMultiplyRecursive(addMatrices(a11, a22), addMatrices(b11, b22));
        int[][] m2 = strassenMultiplyRecursive(addMatrices(a21, a22), b11);
        int[][] m3 = strassenMultiplyRecursive(a11, subtractMatrices(b12, b22));
        int[][] m4 = strassenMultiplyRecursive(a22, subtractMatrices(b21, b11));
        int[][] m5 = strassenMultiplyRecursive(addMatrices(a11, a12), b22);
        int[][] m6 = strassenMultiplyRecursive(subtractMatrices(a21, a11), addMatrices(b11, b12));
        int[][] m7 = strassenMultiplyRecursive(subtractMatrices(a12, a22), addMatrices(b21, b22));

        int[][] c11 = addMatrices(subtractMatrices(addMatrices(m1, m4), m5), m7);
        int[][] c12 = addMatrices(m3, m5);
        int[][] c21 = addMatrices(m2, m4);
        int[][] c22 = addMatrices(subtractMatrices(addMatrices(m1, m3), m2), m6);

        return combineMatrices(c11, c12, c21, c22);
    }

    private static int nextPowerOfTwo(int n) {
        return (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)));
    }

    private static int[][] padMatrix(int[][] matrix, int size) {
        int[][] padded = new int[size][size];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, padded[i], 0, matrix[i].length);
        }
        return padded;
    }

    private static int[][] extractResult(int[][] padded, int rows, int cols) {
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(padded[i], 0, result[i], 0, cols);
        }
        return result;
    }

    private static int[][] standardMultiply(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    private static void divideMatrix(int[][] matrix, int[][] a11, int[][] a12, int[][] a21, int[][] a22) {
        int size = matrix.length / 2;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                a11[i][j] = matrix[i][j];
                a12[i][j] = matrix[i][j + size];
                a21[i][j] = matrix[i + size][j];
                a22[i][j] = matrix[i + size][j + size];
            }
        }
    }

    private static int[][] addMatrices(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private static int[][] subtractMatrices(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    private static int[][] combineMatrices(int[][] c11, int[][] c12, int[][] c21, int[][] c22) {
        int n = c11.length * 2;
        int[][] result = new int[n][n];

        for (int i = 0; i < c11.length; i++) {
            for (int j = 0; j < c11.length; j++) {
                result[i][j] = c11[i][j];
                result[i][j + c11.length] = c12[i][j];
                result[i + c11.length][j] = c21[i][j];
                result[i + c11.length][j + c11.length] = c22[i][j];
            }
        }
        return result;
    }

    static MatrixStrassenOptimized addMatricesThreadSupport(int[][] A, int[][] B) throws InterruptedException {
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

        MatrixStrassenOptimized resultMatrix = new MatrixStrassenOptimized(result);
        return resultMatrix;
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

    static boolean searchMatrix(MatrixStrassenOptimized matrix, int target) {
        printMatrixInformation(matrix.data);
        int rows = matrix.rows;
        int cols = matrix.cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(matrix.data[i][j] == target) {
                    printFoundNullValue(target, i, j);
                    return false;
                }
            }
        }
        return true;
    }

    static boolean compareMatrices(MatrixStrassenOptimized matrix1, MatrixStrassenOptimized matrix2) {
        printMatrixInformation(matrix1.data);
        printMatrixInformation(matrix2.data);

        int rows = matrix1.rows;
        int cols = matrix1.cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix1.data[i][j] != matrix2.data[i][j]) {
                    System.out.println("Matrices are not equal: " + matrix1.data[i][j] + " : " + matrix2.data[i][j]);
                    return false;
                }
            }
        }
        return true;
    }

    static void printMatrixInformation(int[][] matrix) {
        System.out.println("Matrix length: " + matrix.length + " : " + matrix[0].length);
    }

    static void printFoundNullValue(int target, int i, int j) {
        System.out.println("Target " + target + " found at index " + i + " and " + j);
    }

    static void printStandardMultiplicationInformation(int sizes, long addTimes) {
        System.out.println("Standard Multiplication - Size: " + sizes + ", time: " + addTimes);
    }

    static void printStrassenMultiplicationInformation(int sizes, long mulTimes) {
        System.out.println("Strassen Multiplication - Size: " + sizes + ", time: " + mulTimes);
    }

    public static void main(String[] args) throws InterruptedException {
        int[] sizes = {9000, 10000};
        long[][] mulTimes = new long[sizes.length][sizes.length];
        long[][] strTimes = new long[sizes.length][sizes.length];
        long[][] addTimes = new long[sizes.length][sizes.length];

        for (int i = 0; i < sizes.length; i++) {

            int[][] A = generateRandomMatrix(sizes[i]);
            int[][] B = generateRandomMatrix(sizes[i]);

            MatrixStrassenOptimized A1 = new MatrixStrassenOptimized(A);
            MatrixStrassenOptimized B1 = new MatrixStrassenOptimized(B);
            MatrixStrassenOptimized C1;
            MatrixStrassenOptimized C2;
            MatrixStrassenOptimized C3;

            long start = System.currentTimeMillis();
            C1 = MatrixStrassenOptimized.addMatricesThreadSupport(A1.data, B1.data);
            long end = System.currentTimeMillis();
            addTimes[i][i] = end - start;

            start = System.currentTimeMillis();
            C2 = MatrixStrassenOptimized.multiply(A1, B1, true);
            end = System.currentTimeMillis();
            mulTimes[i][i] = end - start;

            printStandardMultiplicationInformation(sizes[i], mulTimes[i][i]);
            MatrixStrassenOptimized.searchMatrix(C1, 0);

            start = System.currentTimeMillis();
            C3 = MatrixStrassenOptimized.strassenMultiply(A1, B1);
            end = System.currentTimeMillis();
            strTimes[i][i] = end - start;

            printStrassenMultiplicationInformation(sizes[i], strTimes[i][i]);
            MatrixStrassenOptimized.searchMatrix(C2, 0);

            System.out.println("Matrix C1 and C2 are equal: " + MatrixStrassenOptimized.compareMatrices(C2, C3));

        }
    }
}