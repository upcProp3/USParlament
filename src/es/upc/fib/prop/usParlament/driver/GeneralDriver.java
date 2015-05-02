package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.usParlament.domain.*;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by aleixsacrest on 30/04/2015.
 */
public class GeneralDriver {
    static Congress c = new Congress();
    static WeightAlgorithm wa = new WeightAlgorithm(c);
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) {
        Boolean seguir = true;
        while(seguir) {
            System.out.println("What do you want to do? Enter the key and press Enter\n");
            System.out.println("1-MP management\n2-Attribute Definitions management\n3-Compute Weights");
            System.out.println("4-Show all MP's and its Attributes\n5-Show congress\n6-Apply Louvain\nany other number-EXIT");
            Integer num = null;
            readCommand(num);
            switch (num) {
                case 1:
                    mpManagement();
                    break;
                case 2:
                    attributeManagement();
                    break;
                case 3:
                    computeWeights();
                    break;
                case 4:
                    for (MP p : c.getMPs()) {
                        System.out.println(p+":");
                            for (Attribute a: p.getAttributes()) {
                                System.out.println(a);
                            }
                    }
                    break;
                case 5:
                    System.out.println("The US Congress");
                    System.out.println(c);
                    break;
                case 6:
                    applyLouvain(); //TODO:
                    break;
                default:
                    seguir = false;
                    break;
            }
        }
    }
    public static void mpManagement() {
        Boolean seguir = true;
        while(seguir) {
            System.out.println("Node Management:");
            System.out.println("1-Enter MP's\n2-Erase MP\n3-Get an MP with its attributes\n4-Get all MP's\n5-Get Common Attributes\nany other number-EXIT");
            Integer num = read.nextInt();
            readCommand(num);
            String fullname;
            String st;
            State estat;
            Integer dist;
            Boolean readmp = true;
            MP p1;
            switch(num) {
                case 1:
                    System.out.println("Enter the full name, the state and the district of the MP's\nafter entering anything hit enter\nwhen you're done instead of the name enter 0");
                    while(true) {
                        System.out.print("fullname: ");
                        fullname = read.nextLine();
                        if (fullname.equals("0")) {System.out.println('\n'); break;}
                        System.out.print("state: ");
                        try {
                            st = read.nextLine();
                            estat = State.valueOf(st);
                            System.out.print("district :");
                            dist = read.nextInt();
                            MP p = new MP(fullname, estat, dist);
                            if (c.getMP(estat, dist) == null) c.addNode(p);
                            else System.out.println("There's an MP already assigned to this state and district");
                            System.out.println("MP added successfully");
                            break;
                        } catch (IllegalArgumentException i) {
                            System.out.println("ERROR: Enter a valid state and district please. The MP hasn't been saved, try it again.");
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter the MP state and district whose info you want to show:");
                    st = null;
                    estat = null;
                    dist = 0;
                    readmp = true;
                    readMP(st, estat, dist, readmp);
                    read.nextLine();
                    if (!readmp) break;
                    c.removeNode(c.getMP(estat, dist));
                    break;
                case 3:
                    System.out.println("Enter the MP state and district whose info you want to show:");
                    st = null;
                    estat = null;
                    dist = 0;
                    readmp = true;
                    readMP(st, estat, dist, readmp);
                    read.nextLine();
                    if (!readmp) break;
                    p1 = null;
                    p1 = c.getMP(estat, dist);
                    if (p1.equals(null)) {
                        System.out.println("there's not such MP in the congress");
                        break;
                    }
                    System.out.println(p1);
                    System.out.println('\n');
                    break;
                case 4:
                    System.out.println("These are all MP's in the congress:");
                    Collection<MP> mps = c.getMPs();
                    for (MP p : mps) System.out.println(p);
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Enter the MP's you want to compare:");
                    System.out.println("MP#1");
                    st = null;
                    estat = null;
                    dist = 0;
                    readmp = true;
                    readMP(st, estat, dist, readmp);
                    read.nextLine();
                    if (!readmp) break;
                    p1 = null;
                    p1 = c.getMP(estat, dist);
                    Boolean found = false;
                    if (p1.equals(null)) {System.out.println("there's not such MP in the congress"); break;}
                    System.out.print("MP#2:");
                    st = null;
                    estat = null;
                    dist = 0;
                    readmp = true;
                    readMP(st, estat, dist, readmp);
                    read.nextLine();
                    if (!readmp) break;
                    MP p2 = null;
                    p2 = c.getMP(estat, dist);
                    if (p2.equals(null)) {System.out.println("there's not such MP in the congress"); break;}
                    for (Attribute a : wa.getCommonAttributes(p1, p2)) System.out.println(a);
                    System.out.println();
                    break;
                default:
                    seguir = false;
                    break;
            }
        }
    }
    public static void attributeManagement() {
        Boolean seguir = true;
        while(seguir) {
            System.out.println("Attribute Management");
            System.out.println("1-Add Attributes\n2-Add new type of attribute\n3-Delete Attribute\n4-Change the value of an attribute\n" +
                    "5-Get the importance of a type of attributes\n6-Set importance to a type of attributes\nany other number-EXIT");
            Integer num = null;
            readCommand(num);
            read.nextLine();
            String fullname;
            String st;
            State estat;
            Integer dist;
            Boolean readmp;
            MP p1;
            AttrDefinition def;
            switch(num) {
                case 1:
                    System.out.println("Enter the MP to whom you want to add attributes:");
                    st = null;
                    estat = null;
                    dist = 0;
                    readmp = true;
                    readMP(st, estat, dist, readmp);
                    read.nextLine();
                    if (!readmp) break;
                    p1 = null;
                    p1 = c.getMP(estat, dist);
                    if (p1.equals(null)) {
                        System.out.println("there's not such MP in the congress");
                        break;
                    }
                    System.out.println("Enter the info of the attributes you want to add to " + p1.getFullname() + " (type & value");
                    System.out.println("when you're done enter 0 insted of the type");
                    while (true) {
                        System.out.println("type of attribute: ");
                        //read.nextLine();
                        String d = read.nextLine();
                        def = c.getAttrDef(d);
                        if (d.equals("0")) break;
                        while (def==null) {
                            System.out.println("This attribute doesn't exist, please enter a valid attribute name:");
                            d = read.nextLine();
                            def = c.getAttrDef(d);
                        }
                        System.out.println("value : ");
                        //read.nextLine();
                        String val = read.nextLine();
                        Attribute a = new Attribute(def, val);
                        p1.addAttribute(a);
                        System.out.println("attribute added");
                    }
                    break;
                case 2:
                    System.out.println("Enter the type info");
                    System.out.println("name: ");
                    //read.nextLine();
                    String name = read.nextLine();
                    System.out.println("importance: ");
                    Integer importance = read.nextInt();
                    read.nextLine();
                    while (importance < 0 || importance > 3) {
                        System.out.print("The importance must be an integer between 0 and 3\n"
                                + "Enter the importance again:");
                        importance = read.nextInt();
                    }
                    def = new AttrDefinition(name, importance);
                    c.addAttrDef(def);
                    System.out.println('\n');
                    break;
                case 3:
                    System.out.println("Enter the MP to whom you want to delete an attribute:");
                    st = null;
                    estat = null;
                    dist = 0;
                    readmp = true;
                    readMP(st, estat, dist, readmp);
                    read.nextLine();
                    if (!readmp) break;
                    p1 = null;
                    p1 = c.getMP(estat, dist);
                    if (p1.equals(null)) {
                        System.out.println("there's not such MP in the congress");
                        break;
                    }
                    System.out.println("Enter the info of the attribute you want to delete of " + p1.getFullname() + " (type & value");
                    System.out.print("type of attribute: ");
                    //read.nextLine();
                    def = c.getAttrDef(read.nextLine());
                    while (def==null) {
                        System.out.println("This attribute doesn't exist, please enter a valid attribute name:");
                        def = c.getAttrDef(read.nextLine());
                    }
                    p1.removeAttribute(def);
                    break;
                case 4:
                    System.out.println("Enter the MP's to whom you want to change the value of an attribute:");
                    st = null;
                    estat = null;
                    dist = 0;
                    readmp = true;
                    readMP(st, estat, dist, readmp);
                    read.nextLine();
                    if (!readmp) break;
                    p1 = null;
                    p1 = c.getMP(estat, dist);
                    if (p1.equals(null)) {
                        System.out.println("there's not such MP in the congress");
                        break;
                    }
                    System.out.println("Enter the info of the attribute you want to change of " + p1.getFullname() + " (type & value)");
                    System.out.print("type of attribute: ");
                    //read.nextLine();
                    def = c.getAttrDef(read.nextLine());
                    while (def==null) {
                        System.out.println("This attribute doesn't exist, please enter a valid attribute name:");
                        def = c.getAttrDef(read.nextLine());
                    }
                    System.out.print("new value: ");
                    //read.nextLine();
                    Object o = read.nextLine();
                    Boolean found = false;
                    for (Attribute a : p1.getAttributes()) {
                        if (a.getDefinition().equals(def)) {
                            a.setValue(o);
                            found = true;
                            break;
                        }
                    }
                    if (found) System.out.println("value changed\n");
                    else System.out.println("attribute not found\n");
                    break;
                case 5:
                    System.out.println("Enter the type: ");
                    //read.nextLine();
                    def = c.getAttrDef(read.nextLine());
                    System.out.println();
                    while (def==null) {
                        System.out.println("This attribute doesn't exist, please enter a valid attribute name:");
                        def = c.getAttrDef(read.nextLine());
                    }
                    System.out.println(def);
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Enter the type: ");
                    //read.nextLine();
                    def = c.getAttrDef(read.nextLine());
                    while (def==null) {
                        System.out.println("This attribute doesn't exist, please enter a valid attribute name:");
                        def = c.getAttrDef(read.nextLine());
                    }
                    System.out.println("actual importance is " + def.getImportance());
                    System.out.print("new importance: ");
                    Integer imp = read.nextInt();
                    read.nextLine();
                    while (imp < 0 || imp > 3) {
                        System.out.print("The importance must be an integer between 0 and 3\n"
                                + "Enter the importance again:");
                        imp = read.nextInt();
                    }
                    def.setImportance(imp);
                    System.out.println("importance changed\n");
                    break;
                default:
                    seguir = false;
                    break;
            }
        }
    }
    public static void computeWeights() {
        Boolean seguir = true;
        while(seguir) {
            System.out.println("Computing weights:\n");
            System.out.println("1-Compute the weights of the relationships of the whole congress\nany other number-EXIT");
            Integer num = null;
            readCommand(num);
            switch(num) {
                case 1:
                    wa.computeAllWeights();
                    System.out.println("How the congress remains");
                    System.out.println(c);
                    break;
                default:
                    seguir = false;
                    break;
            }
        }
    }
    public static void readCommand(Integer num) {
        while (true) {
            try {
                num = read.nextInt();
                break;
            } catch (InputMismatchException e) {
                read.nextLine();
                System.out.println("ERROR: Operation codes must be integers.");
                continue;
            }
        }
    }
    public static void readMP(String st, State estat, int d, Boolean readmp) {
        System.out.print("state: ");
        try {
            st = read.nextLine();
            estat = State.valueOf(st);
            System.out.print("district :");
            d = read.nextInt();
            readmp = true;
        } catch (IllegalArgumentException i) {
            System.out.println("ERROR: Enter a valid state and district please. The MP hasn't been processed, try it again.");
            readmp = false;
        }
    }

    public static void applyLouvain() {}
}
