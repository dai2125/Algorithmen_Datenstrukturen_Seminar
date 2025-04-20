package Seminar_01_Komplexitätsanalyse;


public class Graph {

    Node node;
    int vertices;
    Node[] topParents;
    Node[] leftParents;
    Node[][] graph;

    public Graph(int rows, int cols) {
        graph = new Node[rows][cols];
        topParents = new Node[cols];
        leftParents = new Node[rows];
    }

    void addNode(int top, int left, int[][] arr1, int[][] arr2) {
        int valueT;
        int valueL;
//        System.out.println(top + " " + left + " " + arr1[0].length + " " + arr2[0].length);

        if(top == 0) {
            // has parent
            valueT = sumTop(arr1, left);
//            System.out.println("sumTop: "+ valueT);
//            topParents[left] = new Node(top, left, valueT);
        } else {
//            System.out.println("topParents[left].getValue() = " + topParents[left].getValue());
//            valueT = topParents[left].getValue();
            valueT = graph[0][left].getTopSum();
//            System.out.println("valueT = " + valueT + ", graph[0][left]: " + graph[0][left].getTopSum());
//            System.out.println("left: " + left);
        }

        if(left == 0) {
            valueL = sumLeft(arr2, top);
//            System.out.println("sumLeft: "+ valueL);
//            leftParents[top] = new Node(top, left, valueL);
        } else {
//            System.out.println("leftParents[top].getValue() = " + leftParents[top].getValue());
//            valueL = leftParents[top].getValue();
            valueL = graph[top][0].getLeftSum();
//            System.out.println("valueL = " + valueL + ", graph[top][0]: " + graph[top][0].getLeftSum());

        }

//        graph[top][left] = new Node(top, left, valueT * valueL);
//        graph[top][left] = new Node(top, left, valueT * valueL, valueT, valueL);
        graph[top][left] = new Node(top, left, valueT, valueL);

//        System.out.println("graph[top][left]: " + graph[top][left].getValue() + " "+ top + " " + left + " " + valueL + " " + valueT);
//        Node node = new Node(top, left, valueT * valueL);
    }

    void additionNode(int top, int left, int[][] arr1, int[][] arr2) {
        int valueT;
        int valueL;

        if(top == 0) {
            valueT = sumTop(arr1, left);
        } else {
            valueT = graph[0][left].getTopSum();
        }

        if(left == 0) {
            valueL = sumLeft(arr2, top);
        } else {
            valueL = graph[top][0].getLeftSum();
        }
        graph[top][left] = new Node(top, left, valueT, valueL, true);
    }

    int sumTop(int[][] array, int col) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i][col];
        }
//        System.out.println("sumTop = " + sum);
        return sum;
    }

    int sumLeft(int[][] array, int row) {
        int sum = 0;
        for (int i = 0; i < array[row].length; i++) {
            sum += array[row][i];
        }
//        System.out.println("sumLeft = " + sum);
        return sum;
    }

    public boolean check() {
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[i].length; j++) {
                if(graph[i][j].getValue() == 0) {
                    System.out.printf("i=%d, j=%d\n", i, j);
                    return true;
                }
            }
        }
        return false;
    }

    public void print() {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                System.out.print(graph[i][j].getValue() + "\t");
            }
            System.out.println();
        }
    }

    public void printTopParents() {
        System.out.print("TopParents: ");
        for (Node node : topParents) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();
    }

    public void printLeftParents() {
        System.out.print("LeftParents: ");
        for (Node node : leftParents) {
            System.out.print(node.getValue() + " ");
        }
        System.out.println();
    }

    public void printFirstNineElements() {
        System.out.println("FirstNineElements ANS: ");
        for(int k = 0; k < 3; k++) {
            System.out.print("\t\t" + graph[0][k].getTopSum() + " ");
        }
        System.out.println();
        for(int i = 0; i < 3; i++) {
            System.out.print(graph[i][0].getLeftSum() + "\t");
            for(int j = 0; j < 3; j++) {
                System.out.print("\t" + graph[i][j].getValue() + " ");

//                System.out.print("[" + i + "][" + j + "]=" + graph[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }
}
