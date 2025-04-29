package Aufgabe_01_Komplexitätsanalyse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Panel extends JPanel {

    private int rows = 0;
    private int cols = 0;
    long[][] addTimes;
    long[][] mulTimes;
    int minSize;
    int[] sizes;

    static ArrayList<Long> addList = new ArrayList();
    static ArrayList<Long> mulList = new ArrayList();
    static ArrayList<Integer> sizeList = new ArrayList();


    public Panel(int[] sizes, long[][] addTimes, long[][] mulTimes) {
        this.sizes = sizes;
        this.addTimes = addTimes;
        this.mulTimes = mulTimes;
        minSize = sizes[0];
    }

    public Panel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int maxSize = 10000;
        int maxTime = 1200;
        Color[] colors = {Color.GREEN, Color.BLUE, Color.MAGENTA, Color.RED, Color.YELLOW, Color.ORANGE};

        g2.drawLine(50, height - 50, width - 50, height - 50);
        g2.drawLine(50, height - 50, 50, 50);

//        for (int i = 0; i < sizeList.size(); i++) {
//            int x = 50 + (sizeList.get(i).intValue() * (width - 100) / maxSize);
//            g2.drawLine(x, height - 50, x, height - 45);
//            g2.drawString(Integer.toString(sizeList.get(i).intValue()), x - 10, height - 30);
//        }

        int[] nSize = {100, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
        for (int label : nSize) {
            int x = 50 + (label * (width - 100) / maxSize);
            g2.drawLine(x, height - 50, x, height - 45);
            g2.drawString(Integer.toString(label), x - 15, height - 30);
        }

        for (int i = 0; i < sizeList.size(); i++) {
            int x = 50 + (sizeList.get(i).intValue() * (width - 100) / maxSize);
            g2.drawLine(x, height - 50, x, height - 45);
            g2.setColor(colors[i]);
            g2.drawString("" + sizeList.get(i).intValue(), x - 15, height - 15);
        }



        for (int i = 0; i < addList.size(); i++) {
            int y = height - 50 - ((addList.get(i).intValue() / 1000) * (height - 100) / maxTime);
            g2.drawLine(45, y, 50, y);
            g2.drawString("" + (addList.get(i).intValue() / 1000), 5, y + 5);
        }

        int startX = 50;
        int startY = height - 50;


        int y = 50;
        int a = 0;
        int b = 3;
        Label label;

        for (int i = 0; i < sizeList.size(); i++) {
            g2.setColor(colors[i]);
            g2.drawLine(startX, startY, startX + sizeList.get(i).intValue() * (width - 100) / maxSize, startY - ((addList.get(i).intValue() / 1000) * (height - 100) / maxTime));
            g2.drawLine(startX, startY, startX + sizeList.get(i).intValue() * (width - 100) / maxSize, startY - ((mulList.get(i).intValue() / 1000) * (height - 100) / maxTime));
            y = y + 25;
            label = new Label("Calculation Add: " + (i + 1) + ", n: " + sizeList.get(i).intValue() + ", time: " + (addList.get(i).intValue() / 1000) + " seconds");
            g2.drawString(label.getText(), 75, y);

            y = y + 25;
            label = new Label("Calculation Mul: " + (i + 1) + ", n: " + sizeList.get(i).intValue() + ", time: " + (mulList.get(i).intValue() / 1000) + " seconds");
            g2.drawString(label.getText(), 75, y);
        }
    }

    public void updateData(int[] sizes, long[][] addTimes, long[][] mulTimes) {
        this.sizes = sizes;
        this.addTimes = addTimes;
        this.mulTimes = mulTimes;
        repaint(); // ruft paintComponent() korrekt auf
    }

    public void updateData(ArrayList sizeList, ArrayList addList, ArrayList mulList) {
        this.sizeList = sizeList;
        //initializeSizeList();
        this.addList = addList;
        this.mulList = mulList;
        repaint(); // ruft paintComponent() korrekt auf
    }

    static boolean validateTextField(String text) {
        if(!text.matches("\\d+")) {
            return false;
        }
        int value = Integer.parseInt(text);
        return value <= 10000;
    }


    public static void main(String[] args) {

        JFrame frame = new JFrame("n3 Diagram");
        frame.setSize(1850, 1100);
        Panel chart = new Panel();

        JLabel loadingLabel = new JLabel("Loading...");
        loadingLabel.setVisible(false);


        chart.setLayout(new BorderLayout());
        chart.setBackground(Color.WHITE);

        //frame.add(chart);
        //frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TextField textField1 = new TextField("", 10);
        textField1.setSize(50, 25);


        Button buttonCalculate = new Button("Calculate");
        //buttonCalculate.setSize(30, 25);

        Button buttonClear = new Button("Clear");
        //buttonClear.setSize(30, 25);

        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Matrix (n):"), gbc);

        gbc.gridx = 1;
        controlPanel.add(textField1, gbc);

        gbc.gridx = 2;
        //gbc.gridy = 1;
        //gbc.gridwidth = 4;
        controlPanel.add(loadingLabel, gbc);

        gbc.gridx = 2;
        controlPanel.add(buttonCalculate, gbc);

        gbc.gridx = 3;
        controlPanel.add(buttonClear, gbc);

        buttonCalculate.addActionListener(e -> {

            if (!validateTextField(textField1.getText())) {
                textField1.setForeground(Color.RED);
                textField1.setText("Invalid Input");

                Timer timer = new Timer(2000, evt -> {
                    textField1.setForeground(Color.BLACK);
                    textField1.setText("");
                    textField1.setEditable(true);
                });
                timer.setRepeats(false);
                timer.start();

                textField1.setEditable(false);
                return;
            }
            int n = Integer.parseInt(textField1.getText());

            loadingLabel.setVisible(true);
            buttonCalculate.setEnabled(false);
            buttonClear.setEnabled(false);
            textField1.setEnabled(false);


            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    Matrix matrix1 = new Matrix(n, n);
                    Matrix matrix2 = new Matrix(n, n);

                    Matrix.addRandomValues(matrix1);
                    Matrix.addRandomValues(matrix2);

                    long start = System.currentTimeMillis();
                    Matrix resultAdd = matrix1.add(matrix2);
                    long end = System.currentTimeMillis();
                    Panel.addList.add(end - start);

                    start = System.currentTimeMillis();
                    Matrix resultMul = matrix1.multiply(matrix2);
                    end = System.currentTimeMillis();
                    Panel.mulList.add(end - start);

                    Panel.sizeList.add(n);
                    return null;
                }

                @Override
                protected void done() {
                    chart.updateData(Panel.sizeList, Panel.addList, Panel.mulList);
                    loadingLabel.setVisible(false);
                    buttonCalculate.setEnabled(true);
                    buttonClear.setEnabled(true);
                    textField1.setEnabled(true);
                }
            };

            worker.execute();
        });

        buttonClear.addActionListener(e -> {
            Panel.sizeList.clear();
            Panel.addList.clear();
            Panel.mulList.clear();
            chart.updateData(Panel.sizeList, Panel.addList, Panel.mulList);
        });

        // Hauptlayout
        frame.setLayout(new BorderLayout());
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(chart, BorderLayout.CENTER);
        frame.setVisible(true);

    }
//        int[] sizes = {500, 750, 1000};
//        long[][] addTimes = new long[sizes.length][sizes.length];
//        long[][] mulTimes = new long[sizes.length][sizes.length];
//
//        for (int i = 0; i < sizes.length; i++) {
//
//            Matrix matrix1 = new Matrix(sizes[i], sizes[i]);
//            Matrix matrix2 = new Matrix(sizes[i], sizes[i]);
//            Matrix result1 = new Matrix(sizes[i], sizes[i]);
//
//            matrix1.addRandomValues(matrix1);
//            matrix2.addRandomValues(matrix2);
//
//            long start = System.currentTimeMillis();
//            result1 = matrix1.add2(result1);
//            long end = System.currentTimeMillis();
//            addTimes[i][i] = end - start;
//
//            System.out.println(result1.containsNullValue());
//
//            start = System.currentTimeMillis();
//            result1 = matrix1.add(matrix2);
//            end = System.currentTimeMillis();
//            mulTimes[i][i] = end - start;
//
//            System.out.println(result1.containsNullValue());
//        }
//
//        JFrame frame = new JFrame("n3 Diagram");
//        Panel chart = new Panel(sizes, addTimes, mulTimes);
//        frame.add(chart);
//        frame.setSize(800, 600);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
}
