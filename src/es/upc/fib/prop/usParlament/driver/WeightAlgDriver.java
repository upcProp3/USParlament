package es.upc.fib.prop.usParlament.driver;

import org.w3c.dom.Attr;

import es.upc.fib.prop.usParlament.domain.*;
import es.upc.fib.prop.usParlament.driver.*;

import java.awt.SystemTray;
import java.util.*;

/**
 * Created by aleixsacrest on 30/04/2015.
 */
public class WeightAlgDriver {
    static Congress c = new Congress();
    //static Set<AttrDefinition> s = new HashSet<>();
    static WeightAlgorithm wa = new WeightAlgorithm(c);
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        Boolean seguir = true;
        while(seguir) {
            System.out.println("What do you want to do? Enter the key and press Enter\n");
            System.out.println("1-MP management\n2-Attribute Definitions management\n3-Compute Weights");
            System.out.println("4-Show all MP's and its Attributes\nany other key-EXIT");
            Integer num = read.nextInt();
            switch (num) {
                case 1:
                    MP_management();
                    break;
                case 2:
                    Attribute_management();
                    break;
                case 3:
                    //ComputeWeights();
                    break;
                case 4:
                    for (MP p : c.getMPs()) {
                        System.out.println(p+":");
                            for (Attribute a: p.getAttributes()) {
                                System.out.println(a);
                            }
                    }
                    break;
                default:
                    seguir = false;
                    break;
            }
        }
    }
    public static void MP_management() {
        Boolean seguir = true;
        Scanner read = new Scanner(System.in);
        while(seguir) {
            System.out.println("Node Management:");
            System.out.println("1-Enter MP's\n2-Erase MP\n3-Get an MP with its attributes\n4-Get all MP's\n5-Get Common Attributes\nany other key-EXIT");
            Integer num = read.nextInt();
            String fullname;
            String st;
            State estat;
            Integer dist;
            MP p1;
            switch(num) {
                case 1:
                    System.out.println("Enter the full name, the state and the district of the MP's\nafter entering anything hit enter\nwhen you're done instead of the name");
                    while(true) {
                        System.out.print("fullname: ");
                        read.nextLine();
                        fullname = read.nextLine();
                        if (fullname.equals("0")) {System.out.println('\n'); break;}
                        System.out.print("state: ");
                        st = read.next();
                        estat = State.valueOf(st);
                        System.out.print("district :");
                        dist = read.nextInt();
                        MP p = new MP(fullname, estat, dist);
                        c.addNode(p);
                        System.out.println("MP added successfully");
                    }
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    System.out.println("Enter the MP state and district whose info you want to show:");
                    System.out.print("state: ");
                    st = read.next();
                    estat = State.valueOf(st);
                    System.out.print("district: ");
                    dist = read.nextInt();
                    read.nextLine();
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
                    System.out.print("state1: ");
                    st = read.next();
                    estat = State.valueOf(st);
                    System.out.print("district1: ");
                    dist = read.nextInt();
                    p1 = null;
                    p1 = c.getMP(estat, dist);
                    Boolean found = false;
                    if (p1.equals(null)) {System.out.println("there's not such MP in the congress"); break;}
                    System.out.print("state2: ");
                    st = read.next();
                    estat = State.valueOf(st);
                    System.out.print("district2: ");
                    dist = read.nextInt();
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
    public static void Attribute_management() {
        Boolean seguir = true;
        Scanner read = new Scanner(System.in);
        while(seguir) {
            System.out.println("Attribute Management");
            System.out.println("1-Add Attributes\n2-Add new type of attribute\n3-Delete Attribute\n4-Change the value of an attribute\n" +
                    "5-Get the importance of a type of attributes\n6-Set importance to a type of attributes\nany other key-EXIT");
            Integer num = read.nextInt();
            read.nextLine();
            String fullname;
            String st;
            State estat;
            Integer dist;
            MP p1;
            AttrDefinition def;
            switch(num) {
                case 1:
                    System.out.println("Enter the MP's to whom you want to add attributes:");
                    System.out.print("state: ");
                    st = read.next();
                    estat = State.valueOf(st);
                    System.out.print("district: ");
                    dist = read.nextInt();
                    read.nextLine();
                    p1 = null;
                    p1 = c.getMP(estat, dist);
                    if (p1.equals(null)) {
                        System.out.println("there's not such MP in the congress");
                        break;
                    }
                    System.out.println("Enter the info of the attributes you want to add to " + p1.getFullname() + " (type & value");
                    System.out.println("when you're done enter 0 insted of the type");
                    while (true) {
                        System.out.print("type of attribute: ");
                        //read.nextLine();
                        String d = read.nextLine();
                        def = c.getAttrDef(d);
                        if (d.equals("0")) break;
                        System.out.print("value : ");
                        //read.nextLine();
                        String val = read.nextLine();
                        Attribute a = new Attribute(def, val);
                        p1.addAttribute(a);
                        System.out.println("attribute added");
                    }
                    break;
                case 2:
                    System.out.println("Enter the type info");
                    System.out.print("name: ");
                    //read.nextLine();
                    String name = read.nextLine();
                    System.out.print("importance: ");
                    Integer importance = read.nextInt();
                    read.nextLine();
                    def = new AttrDefinition(name, importance);
                    c.addAttrDef(def);
                    System.out.println('\n');
                    break;
                case 3:
                    System.out.println("Enter the MP's to whom you want to delete an attribute:");
                    System.out.print("state: ");
                    st = read.next();
                    estat = State.valueOf(st);
                    System.out.print("district: ");
                    dist = read.nextInt();
                    read.nextLine();
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
                    p1.removeAttribute(def);
                    break;
                case 4:
                    System.out.println("Enter the MP's to whom you want to change the value of an attribute:");
                    System.out.print("state: ");
                    st = read.next();
                    estat = State.valueOf(st);
                    System.out.print("district: ");
                    dist = read.nextInt();
                    read.nextLine();
                    p1 = null;
                    p1 = c.getMP(estat, dist);
                    if (p1.equals(null)) {
                        System.out.println("there's not such MP in the congress");
                        break;
                    }
                    System.out.println("Enter the info of the attribute you want to change of " + p1.getFullname() + " (type & value");
                    System.out.print("type of attribute: ");
                    //read.nextLine();
                    def = c.getAttrDef(read.nextLine());
                    System.out.print("new value: ");
                    //read.nextLine();
                    String o = read.nextLine();
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
                    if(c.hasAttrDef(def)) System.out.println(def);
                    else System.out.println("There's no type of attributes defined");
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Enter the type: ");
                    //read.nextLine();
                    def = c.getAttrDef(read.nextLine());
                    System.out.println("actual importance is "+def.getImportance());
                    if(c.hasAttrDef(def)) {
                        System.out.print("new importance: ");
                        def.setImportance(read.nextInt());
                        System.out.println("importance changed\n");
                    }
                    else System.out.println("There's no type of attributes defined");
                    break;
                default:
                    seguir = false;
                    break;
            }
        }
    }
    public static void Compute_Weights() {
        Boolean seguir = true;
        Scanner read = new Scanner(System.in);
        while(seguir) {
            System.out.println("Computing weights:\n");
            System.out.println();
            Integer num = read.nextInt();
            read.nextLine();
        }
    }
}
