package es.upc.fib.prop.usParlament.driver;

        import es.upc.fib.prop.shared13.Edge;
import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.shared13.Node;
import es.upc.fib.prop.usParlament.domain.ComparingAlgorithm;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Ondrej Velisek on 29.4.15.
 */
public class CompareDriver {

    private static Graph g = new TGraph();

    public static void main(String[] args)
    {
        System.out.println("==================================");
        System.out.println("   C O M P A R E   D R I V E R ");

        prepareData();

        Scanner reader = new Scanner(System.in);
        int input;
        boolean active = true;
        while(active) {
            System.out.println("==================================");
            System.out.println("1 - add node");
            System.out.println("2 - add edge");
            System.out.println("3 - remove node");
            System.out.println("4 - remove edge");
            System.out.println("5 - show graph");
            System.out.println("6 - compare two partitions");
            System.out.println("0 - exit");

            try {
                input = reader.nextInt();
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.out.println("Wrong input. Try it again.");
                continue;
            }
            switch (input) {
                case 1:
                    addNode(reader);
                    break;
                case 2:
                    addEdge(reader);
                    break;
                case 3:
                    removeNode(reader);
                    break;
                case 4:
                    removeEdge(reader);
                    break;
                case 5:
                    showGraph();
                    break;
                case 6:
                    comparePartitions(reader);
                    break;
                case 0:
                    System.out.println("Bye Bye");
                    active = false;
                    break;
                default:
                    break;
            }

        }

    }

    private static void comparePartitions(Scanner reader) {
        Map<Node,Integer> part1 = new HashMap<>();
        Map<Node,Integer> part2 = new HashMap<>();
        System.out.println("Set partition for each node (as a integer).");
        for (Node n : g.getNodes()) {
            System.out.println("Node: " + n);
            int num;
            while(true) {
                try {
                    num = reader.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    reader.nextLine();
                    System.out.println("Partition has to be integer. Try it again.");
                    continue;
                }
            }
            part1.put(n, num);
        }System.out.println("Set partition for each node (as a integer).");
        for (Node n : g.getNodes()) {
            System.out.println("Node: " + n);
            int num;
            while(true) {
                try {
                    num = reader.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    reader.nextLine();
                    System.out.println("Partition has to be integer. Try it again.");
                    continue;
                }
            }
            part2.put(n, num);
        }
        ComparingAlgorithm c = new ComparingAlgorithm(g,part1,part2);
        System.out.println(c);

    }

    private static void addNode(Scanner reader) {
        System.out.println("Set ID: ");
        int id;
        while(true) {
            try {
                id = reader.nextInt();
                break;
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.out.println("ID has to be integer. Try it again.");
                continue;
            }
        }
        Node n = new TNode(id);
        if (!g.addNode(n)) {
            System.out.println("Node with ID " +id+ " already exists in graph.");
        }
    }

    private static void addEdge(Scanner reader) {
        System.out.println("Select first node by id: ");
        int id;
        Node n1;
        while(true) {
            try {
                id = reader.nextInt();
                n1 = new TNode(id);
                if (g.getNodes().contains(n1)) {
                    break;
                } else {
                    System.out.println("Node with ID " +id+ " doesn't exist. Try it again.");
                    continue;
                }
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.out.println("Id has to be integer. Try it again.");
                continue;
            }
        }
        Node n2;
        System.out.println("Select second node by id: ");
        while(true) {
            try {
                id = reader.nextInt();
                n2 = new TNode(id);
                if (g.getNodes().contains(n1)) {
                    break;
                } else {
                    System.out.println("Node with ID " +id+ " doesn't exist. Try it again.");
                    continue;
                }
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.out.println("Id has to be integer. Try it again.");
                continue;
            }
        }
        System.out.println("Set weight: ");
        double weight;
        while(true) {
            try {
                weight = reader.nextDouble();
                break;
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.out.println("Weight has to be number. Try it again.");
                continue;
            }
        }
        Edge rel = new TEdge(n1, n2, weight);
        g.addEdge(rel);
    }

    private static void removeNode(Scanner reader) {
        System.out.println("Select node to remove by id: ");
        int id;
        Node n;
        while(true) {
            try {
                id = reader.nextInt();
                n = new TNode(id);
                if (g.getNodes().contains(n)) {
                    break;
                } else {
                    System.out.println("Node with ID " +id+ " doesn't exist. Try it again.");
                    continue;
                }
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.out.println("Id has to be integer. Try it again.");
                continue;
            }
        }
        g.removeNode(n);
    }

    private static void removeEdge(Scanner reader) {
        System.out.println("Select first node by id: ");
        int id;
        Node n1;
        while(true) {
            try {
                id = reader.nextInt();
                n1 = new TNode(id);
                if (g.getNodes().contains(n1)) {
                    break;
                } else {
                    System.out.println("Node with ID " +id+ " doesn't exist. Try it again.");
                    continue;
                }
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.out.println("Id has to be integer. Try it again.");
                continue;
            }
        }
        Node n2;
        System.out.println("Select second node by id: ");
        while(true) {
            try {
                id = reader.nextInt();
                n2 = new TNode(id);
                if (g.getNodes().contains(n1)) {
                    break;
                } else {
                    System.out.println("Node with ID " +id+ " doesn't exist. Try it again.");
                    continue;
                }
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.out.println("Id has to be integer. Try it again.");
                continue;
            }
        }
        if (!g.removeEdge(n1, n2)) {
            System.out.println("Edge doesn't exist");
        }
    }

    private static void showGraph() {
        System.out.println("=====================================");
        System.out.println(g);
    }


    private static void prepareData() {

    }
}
