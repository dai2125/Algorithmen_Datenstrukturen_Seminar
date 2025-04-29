package Aufgabe_01_Komplexitätsanalyse;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

public class Matrix<T extends Number> extends JPanel {

    private int rows = 0;
    private int cols = 0;
    private T[] matrix = null;
    long[][] addTimes;
    long[][] mulTimes;
    int minSize;
    int[] sizes;

    private final Comparator<T> comparator = new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
            int result = 0;
            if (o1 instanceof BigDecimal || o2 instanceof BigDecimal) {
                BigDecimal c1 = (BigDecimal)o1;
                BigDecimal c2 = (BigDecimal)o2;
                result = c1.compareTo(c2);
            } else if (o1 instanceof BigInteger || o2 instanceof BigInteger) {
                BigInteger c1 = (BigInteger)o1;
                BigInteger c2 = (BigInteger)o2;
                result = c1.compareTo(c2);
            } else if (o1 instanceof Long || o2 instanceof Long) {
                Long c1 = o1.longValue();
                Long c2 = o2.longValue();
                result = c1.compareTo(c2);
            } else if (o1 instanceof Double || o2 instanceof Double) {
                Double c1 = o1.doubleValue();
                Double c2 = o2.doubleValue();
                result = c1.compareTo(c2);
            } else if (o1 instanceof Float || o2 instanceof Float) {
                Float c1 = o1.floatValue();
                Float c2 = o2.floatValue();
                result = c1.compareTo(c2);
            } else {
                Integer c1 = o1.intValue();
                Integer c2 = o2.intValue();
                result = c1.compareTo(c2);
            }
            return result;
        }
    };

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = (T[]) new Number[rows * cols];
    }

    public Matrix(int rows, int cols, T[][] matrix) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = (T[]) new Number[rows * cols];
        for (int r=0; r<rows; r++)
            for (int c=0; c<cols; c++)
                this.matrix[getIndex(r,c)] = matrix[r][c];
    }

    public Matrix(int[] sizes, long[][] addTimes, long[][] mulTimes) {
        this.sizes = sizes;
        this.addTimes = addTimes;
        this.mulTimes = mulTimes;
        minSize = sizes[0];
        this.matrix = (T[]) new Number[rows * cols];
    }

    private int getIndex(int row, int col) {
        if (row == 0)
            return col;
        return ((row * cols) + col);
    }

    public T get(int row, int col) {
        return matrix[getIndex(row, col)];
    }

    public T[] getRow(int row) {
        T[] result = (T[]) new Number[cols];
        for (int c = 0; c < cols; c++) {
            result[c] = this.get(row, c);
        }
        return result;
    }

    public T[] getColumn(int col) {
        T[] result = (T[]) new Number[rows];
        for (int r = 0; r < rows; r++) {
            result[r] = this.get(r, col);
        }
        return result;
    }

    public void set(int row, int col, T value) {
        matrix[getIndex(row, col)] = value;
    }

    public Matrix<T> identity() throws Exception{
        if (this.rows != this.cols)
            throw new Exception("Matrix should be a square");

        final T element = this.get(0, 0);
        final T zero;
        final T one;
        if (element instanceof BigDecimal) {
            zero = (T)BigDecimal.ZERO;
            one = (T)BigDecimal.ONE;
        } else if (element instanceof BigInteger) {
            zero = (T)BigInteger.ZERO;
            one = (T)BigInteger.ONE;
        } else if (element instanceof Long) {
            zero = (T)new Long(0);
            one = (T)new Long(1);
        } else if (element instanceof Double) {
            zero = (T)new Double(0);
            one = (T)new Double(1);
        } else if (element instanceof Float) {
            zero = (T)new Float(0);
            one = (T)new Float(1);
        } else {
            zero = (T)new Integer(0);
            one = (T)new Integer(1);
        }

        final T array[][] = (T[][])new Number[this.rows][this.cols];
        for(int i = 0; i < this.rows; ++i) {
            for(int j = 0 ; j < this.cols; ++j) {
                array[i][j] = zero;
            }
        }

        final Matrix<T> identityMatrix = new Matrix<T>(this.rows, this.cols, array);
        for(int i = 0; i < this.rows;++i) {
            identityMatrix.set(i, i, one);
        }
        return identityMatrix;
    }

    public Matrix<T> add(Matrix<T> input) {
        Matrix<T> output = new Matrix<T>(this.rows, this.cols);
        if ((this.cols != input.cols) || (this.rows != input.rows))
            return output;
        for (int r = 0; r < output.rows; r++) {
            for (int c = 0; c < output.cols; c++) {
                for (int i = 0; i < cols; i++) {
                    T m1 = this.get(r, c);
                    T m2 = input.get(r, c);
                    T result;
                    if (m1 instanceof BigDecimal || m2 instanceof BigDecimal) {
                        BigDecimal result2 = ((BigDecimal)m1).add((BigDecimal)m2);
                        result = (T)result2;
                    } else if (m1 instanceof BigInteger || m2 instanceof BigInteger) {
                        BigInteger result2 = ((BigInteger)m1).add((BigInteger)m2);
                        result = (T)result2;
                    } else if (m1 instanceof Long || m2 instanceof Long) {
                        Long result2 = (m1.longValue() + m2.longValue());
                        result = (T)result2;
                    } else if (m1 instanceof Double || m2 instanceof Double) {
                        Double result2 = (m1.doubleValue() + m2.doubleValue());
                        result = (T)result2;
                    } else if (m1 instanceof Float || m2 instanceof Float) {
                        Float result2 = (m1.floatValue() + m2.floatValue());
                        result = (T)result2;
                    } else if (m1 instanceof Integer || m2 instanceof Integer) {
                        Integer result2 = (m1.intValue() + m2.intValue());
                        result = (T)result2;
                    } else {
                        Integer result2 = (int) (m1.intValue() + m2.intValue());
                        result = (T) result2;
                    }
                    output.set(r, c, result);
                }
            }
        }
        return output;
    }

    public Matrix<T> subtract(Matrix<T> input) {
        Matrix<T> output = new Matrix<T>(this.rows, this.cols);
        if ((this.cols != input.cols) || (this.rows != input.rows))
            return output;

        for (int r = 0; r < output.rows; r++) {
            for (int c = 0; c < output.cols; c++) {
                for (int i = 0; i < cols; i++) {
                    T m1 = this.get(r, c);
                    T m2 = input.get(r, c);
                    T result;
                    if (m1 instanceof BigDecimal || m2 instanceof BigDecimal) {
                        BigDecimal result2 = ((BigDecimal)m1).subtract((BigDecimal)m2);
                        result = (T)result2;
                    } else if (m1 instanceof BigInteger || m2 instanceof BigInteger) {
                        BigInteger result2 = ((BigInteger)m1).subtract((BigInteger)m2);
                        result = (T)result2;
                    } else if (m1 instanceof Long || m2 instanceof Long) {
                        Long result2 = (m1.longValue() - m2.longValue());
                        result = (T)result2;
                    } else if (m1 instanceof Double || m2 instanceof Double) {
                        Double result2 = (m1.doubleValue() - m2.doubleValue());
                        result = (T)result2;
                    } else if (m1 instanceof Float || m2 instanceof Float) {
                        Float result2 = (m1.floatValue() - m2.floatValue());
                        result = (T)result2;
                    } else {
                        // Integer
                        Integer result2 = (m1.intValue() - m2.intValue());
                        result = (T)result2;
                    }
                    output.set(r, c, result);
                }
            }
        }
        return output;
    }

    public Matrix<T> multiply(Matrix<T> input) {
        Matrix<T> output = new Matrix<T>(this.rows, input.cols);
        if (this.cols != input.rows)
            return output;

        for (int r = 0; r < output.rows; r++) {
            for (int c = 0; c < output.cols; c++) {
                T[] row = getRow(r);
                T[] column = input.getColumn(c);
                T test = row[0];
                if (test instanceof BigDecimal) {
                    BigDecimal result = BigDecimal.ZERO;
                    for (int i = 0; i < cols; i++) {
                        T m1 = row[i];
                        T m2 = column[i];
                        BigDecimal result2 = ((BigDecimal)m1).multiply(((BigDecimal)m2));
                        result = result.add(result2);
                    }
                    output.set(r, c, (T)result);
                } else if (test instanceof BigInteger) {
                    BigInteger result = BigInteger.ZERO;
                    for (int i = 0; i < cols; i++) {
                        T m1 = row[i];
                        T m2 = column[i];
                        BigInteger result2 = ((BigInteger)m1).multiply(((BigInteger)m2));
                        result = result.add(result2);
                    }
                    output.set(r, c, (T)result);
                } else if (test instanceof Long) {
                    Long result = 0l;
                    for (int i = 0; i < cols; i++) {
                        T m1 = row[i];
                        T m2 = column[i];
                        Long result2 = m1.longValue() * m2.longValue();
                        result = result+result2;
                    }
                    output.set(r, c, (T)result);
                } else if (test instanceof Double) {
                    Double result = 0d;
                    for (int i = 0; i < cols; i++) {
                        T m1 = row[i];
                        T m2 = column[i];
                        Double result2 = m1.doubleValue() * m2.doubleValue();
                        result = result+result2;
                    }
                    output.set(r, c, (T)result);
                } else if (test instanceof Float) {
                    Float result = 0f;
                    for (int i = 0; i < cols; i++) {
                        T m1 = row[i];
                        T m2 = column[i];
                        Float result2 = m1.floatValue() * m2.floatValue();
                        result = result+result2;
                    }
                    output.set(r, c, (T)result);
                } else {
                    // Integer
                    Integer result = 0;
                    for (int i = 0; i < cols; i++) {
                        T m1 = row[i];
                        T m2 = column[i];
                        Integer result2 = m1.intValue() * m2.intValue();
                        result = result+result2;
                    }
                    output.set(r, c, (T)result);
                }
            }
        }
        return output;
    }

    public void copy(Matrix<T> m) {
        for (int r = 0; r < m.rows; r++) {
            for (int c = 0; c < m.cols; c++) {
                set(r, c, m.get(r, c));
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = this.rows + this.cols;
        for (T t : matrix)
            hash += t.intValue();
        return 31 * hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Matrix))
            return false;

        Matrix<T> m = (Matrix<T>) obj;
        if (this.rows != m.rows)
            return false;
        if (this.cols != m.cols)
            return false;
        for (int i=0; i<matrix.length; i++) {
            T t1 = matrix[i];
            T t2 = m.matrix[i];
            int result = comparator.compare(t1, t2);
            if (result!=0)
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Matrix:\n");
        for (int r = 0; r < rows; r++) {
            builder.append("row=[").append(r).append("] ");
            for (int c = 0; c < cols; c++) {
                builder.append(this.get(r, c)).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    static int[][] generateRandomMatrix(int n) {
        int[][] matrix = new int[n][n];
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = rand.nextInt(1, 100);
        return matrix;
    }

    boolean containsNullValue() {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                if(this.get(i, j) == null || this.get(i, j).intValue() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean compareMatrices(Matrix m1, Matrix m2) {
        Random random = new Random();
        int temp = (m1.rows + m1.cols) / 100;

        for(int i = 0; i < temp; i++) {
            int row = random.nextInt(0, m1.rows);
            int col = random.nextInt(0, m1.cols);
            if(!Objects.equals(m1.get(row, col), m2.get(row, col))) {
                return false;
            }
        }
        return true;
    }

    static void printMatrix(Matrix matrix) {
        for (int r = 0; r < matrix.rows; r++) {
            for (int c = 0; c < matrix.cols; c++) {
                System.out.print(matrix.get(r, c) + " ");
            }
            System.out.println();
        }
    }

    static void printMatrix(Matrix matrix, int fromRow, int fromCol, int toRow, int toCol) {
        if(fromRow > toRow || fromCol > toCol || fromRow < 0 || fromCol < 0 || toRow > matrix.rows || toCol > matrix.cols) {
            return;
        }

        for (; fromRow < toRow; fromRow++) {
            for (; fromCol < toCol; fromCol++) {
                System.out.print(matrix.get(fromRow, fromCol) + " ");
            }
            System.out.println();
        }
    }

    static void addRandomValues(Matrix matrix) {
        Random random = new Random();

        for(int r = 0; r < matrix.rows; r++) {
            for(int c = 0; c < matrix.cols; c++) {
                matrix.set(r, c, random.nextInt(1, 100));
            }
        }
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

    public static void main(String[] args) {

        int[] sizes = {500, 1000, 1200};
        long[][] addTimes = new long[sizes.length][sizes.length];
        long[][] mulTimes = new long[sizes.length][sizes.length];

        for (int i = 0; i < sizes.length; i++) {

            Matrix matrix1 = new Matrix(sizes[i], sizes[i]);
            Matrix matrix2 = new Matrix(sizes[i], sizes[i]);
            Matrix result1 = new Matrix(sizes[i], sizes[i]);

            addRandomValues(matrix1);
            System.out.println("matrix1 containsNullValue(): " + matrix1.containsNullValue());

            addRandomValues(matrix2);
            System.out.println("matrix2 containsNullValue(): " + matrix2.containsNullValue());

            long start = System.currentTimeMillis();
            result1 = matrix1.add(matrix2);
            long end = System.currentTimeMillis();
            addTimes[i][i] = end - start;

            System.out.println("addTimes[i]: " + Arrays.toString(addTimes[i]));
            System.out.println("Result1 containsNullValue(): " + result1.containsNullValue());

            start = System.currentTimeMillis();
            result1 = matrix1.multiply(matrix2);
            end = System.currentTimeMillis();
            mulTimes[i][i] = end - start;

            System.out.println("mulTimes[i]: " + Arrays.toString(mulTimes[i]));
            System.out.println("Result1 containsNullValue(): " + result1.containsNullValue());
        }

        JFrame frame = new JFrame("n3 Diagram");
        Matrix chart = new Matrix(sizes, addTimes, mulTimes);
        frame.add(chart);
        frame.setSize(1850, 1100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}