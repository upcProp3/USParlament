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
        while(opcode != 0){
            if(opcode == 1) { //Add MP
                System.out.println("You must specify his/her full name, state and district\n");
                System.out.println(reader.nextLine());
                c.addNode(new MP(reader.nextLine(), State.valueOf(reader.nextLine().toUpperCase()), Integer.parseInt(reader.nextLine())));
            } else if(opcode == 2) { //Add attribute definition
                System.out.println("You must specify the attribute's name and importance (0-3)\n");
                System.out.println(reader.nextLine());
                AttrDefinition ad = new AttrDefinition(reader.nextLine(), reader.nextInt());
                as.addAttributeDef(ad);
            } else if(opcode == 3) { //Add attribute value to an MP
                System.out.println("You must specify the attribute's name and value, and the MP's state and district\n");
                System.out.println(reader.nextLine());
                AttrDefinition ad = as.getAttributeDef(reader.nextLine());
                Attribute a = new Attribute(ad, reader.nextLine());
                MP m = c.getMP(State.valueOf(reader.nextLine().toUpperCase()), reader.nextInt());
                m.addAttribute(a);
            } else if (opcode == 4) { //Delete MP
                System.out.println("You must specify the MP's state and district\n");
                System.out.println(reader.nextLine());
                MP m = c.getMP(State.valueOf(reader.nextLine().toUpperCase()), reader.nextInt());
                c.deleteMP(m);
                c.removeNode(m);
            } else if (opcode == 5) { //Delete attribute definition
                System.out.println("You must specify the attribute's name\n");
                System.out.println(reader.nextLine());
                String name = reader.nextLine();
                AttrDefinition ad = as.getAttributeDef(name);
                for (MP m : c.getMPs()) {
                    if (m.hasAttribute(ad)) m.removeAttribute(ad);
                }
                as.removeAttributeDef(ad);
            } else if (opcode == 6) { //Delete attribute vale of an MP
                System.out.println("You must specify the attribute's name, and the MP's state and district\n");
                System.out.println(reader.nextLine());
                String name = reader.nextLine();
                MP m = c.getMP(State.valueOf(reader.nextLine().toUpperCase()), reader.nextInt());
                AttrDefinition ad = as.getAttributeDef(name);
                if (m.hasAttribute(ad)) m.removeAttribute(ad);
                else System.out.println("This MP had no assigned value for that attribute\n");
            } else if (opcode == 7) { //Print MP information
                System.out.println("You must specify the MP's state and district\n");
                System.out.println(reader.nextLine());
                MP m = c.getMP(State.valueOf(reader.nextLine().toUpperCase()), reader.nextInt());
                m.toString();
            } else if (opcode == 8) { //Print attribute information
                System.out.println("You must specify the attribute's name\n");
                System.out.println(reader.nextLine());
                AttrDefinition ad = as.getAttributeDef(reader.nextLine());
                ad.toString();
            } else if (opcode == 9) { //Modify attribute importance
                System.out.println("You must specify the attribute's name and the new importance value (0-3)\n");
                System.out.println(reader.nextLine());
                AttrDefinition ad = as.getAttributeDef(reader.nextLine());
                ad.setImportance(reader.nextInt());
            } else if (opcode == 99) { //Options menu
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
            }
            System.out.println();
            opcode = reader.nextInt();
        }
        System.out.println("Successful exit\n");
    }
}
