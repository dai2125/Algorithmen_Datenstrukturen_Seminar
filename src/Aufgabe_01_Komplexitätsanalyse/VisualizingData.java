package Aufgabe_01_Komplexitätsanalyse;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VisualizingData extends JPanel {

    int[] sizes;
    long[][] addTimes;
    long[][] mulTimes;
    long[][] strTimes;
    int minSize;
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    VisualizingData(int[] sizes, long[][] addTimes, long[][] mulTimes, long[][] strTimes) {
        this.sizes = sizes;
        this.addTimes = addTimes;
        this.mulTimes = mulTimes;
        this.strTimes = strTimes;
        minSize = sizes[0];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int maxSize = sizes[sizes.length - 1];
        int maxTime = (int) addTimes[addTimes.length - 1][addTimes.length - 1];

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
        Label label;

        for (int i = 0; i < addTimes.length; i++) {
            g2.setColor(colors[0]);
            g2.drawLine(startX, startY, startX + sizes[i] * (width - 100) / maxSize, startY - (int) (addTimes[i][i] * (height - 100) / maxTime));
            //g2.drawLine(startX, startY, startX + sizes[i] * (width - 100) / maxSize, startY - (int) (mulTimes[i][i] * (height - 100) / maxTime));
            y = y + 25;
            label = new Label("Calculation Add: " + (i + 1) + ", n: " + sizes[i] + ", time: " + addTimes[i][i]);
            g2.drawString(label.getText(), 75, y);

        }

        for (int i = 0; i < mulTimes.length; i++) {
            g2.setColor(colors[1]);
            g2.drawLine(startX, startY, startX + sizes[i] * (width - 100) / maxSize, startY - (int) (mulTimes[i][i] * (height - 100) / maxTime));
            y = y + 25;
            label = new Label("Calculation Standard Mul: " + (i + 1) + ", n: " + sizes[i] + ", time: " + mulTimes[i][i]);
            g2.drawString(label.getText(), 75, y);
        }

        for (int i = 0; i < strTimes.length; i++) {
            g2.setColor(colors[3]);
            g2.drawLine(startX, startY, startX + sizes[i] * (width - 100) / maxSize, startY - (int) (strTimes[i][i] * (height - 100) / maxTime));
            y = y + 25;
            label = new Label("Calculation Strassen Mul: " + (i + 1) + ", n: " + sizes[i] + ", time: " + strTimes[i][i]);
            g2.drawString(label.getText(), 75, y);

        }
    }

    public static void main(String[] args) throws InterruptedException {

        int[] sizes = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000};
        long[][] addTimes = new long[3][3];
        long[][] mulTimes = new long[4][4];
        long[][] strTimes = new long[8][8];

        sizes[0] = 1000;
        sizes[1] = 2000;
        sizes[2] = 3000;
        sizes[3] = 4000;

        addTimes[0][0] = 3151;
        addTimes[1][1] = 88784;
        addTimes[2][2] = 383076;

        mulTimes[0][0] = 419;
        mulTimes[1][1] = 4318;
        mulTimes[2][2] = 23039;
        mulTimes[3][3] = 76818;

        strTimes[0][0] = 475;
        strTimes[1][1] = 2476;
        strTimes[2][2] = 17852;
        strTimes[3][3] = 19331;
        strTimes[4][4] = 148058;
        strTimes[5][5] = 150339;
        strTimes[6][6] = 161902;
        strTimes[7][7] = 137544;

        JFrame frame = new JFrame("n3 Chart");
        VisualizingData chart = new VisualizingData(sizes, addTimes, mulTimes, strTimes);
        chart.setBackground(Color.WHITE);
        frame.add(chart);
        frame.setSize(1850, 1100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
