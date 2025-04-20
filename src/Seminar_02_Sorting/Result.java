package Seminar_02_Sorting;

public class Result implements Comparable<Result> {

    String name;
    int[] values;
    long time;
    int steps;
    int n;

    double avgCase = 0;
    double worstCase = 0;
    double bestCase = 0;

    public Result(String name, int[] values, long time, int steps, int n) {
        this.name = name;
        this.values = values;
        this.time = time;
        this.steps = steps;
        this.n = n;
        calculateCase(n);
    }

    public String getName() {
        return name;
    }

    public int[] getValues() {
        return values;
    }

    public long getTime() {
        return time;
    }

    public int getSteps() {
        return steps;
    }

    void calculateCase(int n) {
        switch(name) {
            case "MergeSort":
                double temp = Math.log(n) / Math.log(2);
                bestCase = avgCase = worstCase = n * temp;
                break;
            default:
                bestCase = (1 * n * (Math.log(n) / Math.log(2)));
                avgCase = (1.386 * n * (Math.log(n) / Math.log(2)));
                worstCase = Math.pow(n, 2);
                break;
        }
    }

    public double getBestCase() {
        return bestCase;
    }

    public double getWorstCase() {
        return worstCase;
    }

    public double getAverageCase() {
        return avgCase;
    }

    @Override
    public int compareTo(Result o) {
        return Integer.compare(this.values[4], o.values[4]);
    }

    @Override
    public String toString() {
        switch(name) {
            case "MergeSort":
                double temp = Math.log(n) / Math.log(2);
                bestCase = avgCase = worstCase = n * temp;
                break;
            default:
                bestCase = (1 * n * (Math.log(n) / Math.log(2)));
                avgCase = (1.386 * n * (Math.log(n) / Math.log(2)));
                worstCase = Math.pow(n, 2);
                break;
        }
        return "Time taken: " + time + " ns, steps = " + steps + " expected(best): " + String.format("%.0f", bestCase) + " expected(avg): " + String.format("%.0f", avgCase) + " expected(worst): " + String.format("%.0f", worstCase) + ", Algorithm: " + name;
    }
}
