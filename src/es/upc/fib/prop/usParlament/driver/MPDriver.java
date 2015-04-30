package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.usParlament.domain.*;

import java.util.Scanner;

/**
 * Created by miquel on 7/04/15.
 * Contributions of alex.
 * Drivers order: adding, removing, printing, other
 *
 */
public class MPDriver {
/*
    public static void main(String[] args) {

        System.out.println("MP & ATTRIBUTES DRIVER\n");
        System.out.println("----------------------\n");
        System.out.println();
        System.out.println("Options:\n");
        System.out.println("USA districts: \n" +
                "\tAL, AK, AZ, AR, CA, CO, CT, DE, FL, GA,\n" +
                "\tHI, ID, IL, IN, IA, KS, KY, LA, ME, MD,\n" +
                "\tMA, MI, MN, MS, MO, MT, NE, NV, NH, NJ,\n" +
                "\tNM, NY, NC, ND, OH, OK, OR, PA, RI, SC,\n" +
                "\tSD, TN, TX, UT, VT, VA, WA, WV, WI, WY\n");
        System.out.println("0: Exit\n");
        System.out.println("1: Add MP\n");
        System.out.println("2: Add attribute definition\n");
        System.out.println("3: Add attribute value to an MP\n");
        System.out.println("4: Delete MP\n");
        System.out.println("5: Delete attribute definition\n");
        System.out.println("6: Delete attribute value of an MP\n");
        System.out.println("7: Print MP information\n");
        System.out.println("8: Print attribute information\n");
        System.out.println("9: Modify attribute importance\n");
        System.out.println("99: Show options menu\n");
        System.out.println();

        Congress c = new Congress();
        AttrSet as = new AttrSet();

        Scanner reader = new Scanner(System.in);
        int opcode;
        opcode = reader.nextInt();
        while(opcode != 0) {
            switch (opcode) {
                case 1: //Add MP
                    System.out.println("You must specify his/her full name, state and district\n");
                    System.out.println(reader.nextLine());
                    c.addNode(new MP(reader.nextLine(), State.valueOf(reader.nextLine().toUpperCase()), Integer.parseInt(reader.nextLine())));
                    break;
                case 2: //Add attribute definition
                    System.out.println("You must specify the attribute's name and importance (0-3)\n");
                    System.out.println(reader.nextLine());
                    AttrDefinition ad = new AttrDefinition(reader.nextLine(), reader.nextInt());
                    as.addAttributeDef(ad);
                    break;
                case 3: //Add attribute value to an MP
                    System.out.println("You must specify the attribute's name and value, and the MP's state and district\n");
                    System.out.println(reader.nextLine());
                    AttrDefinition ad3 = as.getAttributeDef(reader.nextLine());
                    Attribute a = new Attribute(ad3, reader.nextLine());
                    MP m = c.getMP(State.valueOf(reader.nextLine().toUpperCase()), reader.nextInt());
                    m.addAttribute(a);
                    break;
                case 4: //Delete MP
                    System.out.println("You must specify the MP's state and district\n");
                    System.out.println(reader.nextLine());
                    MP m = c.getMP(State.valueOf(reader.nextLine().toUpperCase()), reader.nextInt());
                    c.deleteMP(m);
                    c.removeNode(m);
                    break;
                case 5: //Delete attribute definition
                    System.out.println("You must specify the attribute's name\n");
                    System.out.println(reader.nextLine());
                    String name = reader.nextLine();
                    AttrDefinition ad5 = as.getAttributeDef(name);
                    for (MP m : c.getMPs()) {
                        if (m.hasAttribute(ad5)) m.removeAttribute(ad5);
                    }
                    as.removeAttributeDef(ad5);
                    break;
                case 6: //Delete attribute vale of an MP
                    System.out.println("You must specify the attribute's name, and the MP's state and district\n");
                    System.out.println(reader.nextLine());
                    String name = reader.nextLine();
                    MP m = c.getMP(State.valueOf(reader.nextLine().toUpperCase()), reader.nextInt());
                    AttrDefinition ad6 = as.getAttributeDef(name);
                    if (m.hasAttribute(ad6)) m.removeAttribute(ad6);
                    else System.out.println("This MP had no assigned value for that attribute\n");
                    break;
                case 7: //Print MP information
                    System.out.println("You must specify the MP's state and district\n");
                    System.out.println(reader.nextLine());
                    MP m = c.getMP(State.valueOf(reader.nextLine().toUpperCase()), reader.nextInt());
                    m.toString();
                    break;
                case 8: //Print attribute information
                    System.out.println("You must specify the attribute's name\n");
                    System.out.println(reader.nextLine());
                    AttrDefinition ad8 = as.getAttributeDef(reader.nextLine());
                    ad8.toString();
                    break;
                case 9: //Modify attribute importance
                    System.out.println("You must specify the attribute's name and the new importance value (0-3)\n");
                    System.out.println(reader.nextLine());
                    AttrDefinition ad9 = as.getAttributeDef(reader.nextLine());
                    ad9.setImportance(reader.nextInt());
                    break;
                case 99: //Options menu
                    System.out.println("Options:\n");
                    System.out.println("USA districts:" +
                            "\tAL, AK, AZ, AR, CA, CO, CT, DE, FL, GA,\n" +
                            "\tHI, ID, IL, IN, IA, KS, KY, LA, ME, MD,\n" +
                            "\tMA, MI, MN, MS, MO, MT, NE, NV, NH, NJ,\n" +
                            "\tNM, NY, NC, ND, OH, OK, OR, PA, RI, SC,\n" +
                            "\tSD, TN, TX, UT, VT, VA, WA, WV, WI, WY\n");
                    System.out.println("0: Exit\n");
                    System.out.println("1: Add MP\n");
                    System.out.println("2: Add attribute definition\n");
                    System.out.println("3: Add attribute value to an MP\n");
                    System.out.println("4: Delete MP\n");
                    System.out.println("5: Delete attribute definiton\n");
                    System.out.println("6: Delete attribute value of an MP\n");
                    System.out.println("7: Print MP information\n");
                    System.out.println("8: Print attribute information\n");
                    System.out.println("9: Modify attribute's importance\n");
                    System.out.println("99: Show options menu\n");
                    break;
                default:
                    System.out.println("Invalid operation code\n");
            }
            System.out.println();
            opcode = reader.nextInt();
        }
        System.out.println("Successful exit\n");
    }
    */
}
