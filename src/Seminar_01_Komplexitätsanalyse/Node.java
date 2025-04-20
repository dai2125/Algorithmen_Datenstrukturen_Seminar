package Seminar_01_Komplexitätsanalyse;

public class Node {

    int top;
    int left;
    int value;
    int topSum;
    int leftSum;

    public Node(int top, int left, int valueT, int valueL) {
        this.top = top;
        this.left = left;

        this.value = valueT * valueL;

        if(top == 0) {
            this.topSum = valueT;
        }
        if(left == 0) {
            this.leftSum = valueL;
        }
//        this.topSum = valueT;
//        this.leftSum = valueL;
//        System.out.println("Node: [" + top + "][" + left + "], product: " + value + ", valueT: " + valueT + ", valueL: " + valueL);
    }

    public Node(int top, int left, int valueT, int valueL, boolean addition) {
        this.top = top;
        this.left = left;

        this.value = valueT * valueL;

        if(top == 0) {
            this.topSum = valueT;
        }
        if(left == 0) {
            this.leftSum = valueL;
        }
    }

    public int getValue() {
        return value;
    }

    public int getLeftSum() {
        return leftSum;
    }

    public int getTopSum() {
        return topSum;
    }

    public void printNode() {
        System.out.println("Node: " + top + "" + left + ", value= " + value);
    }
}
