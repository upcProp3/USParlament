package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.usParlament.domain.*;

import java.util.Scanner;

/**
 * Created by miquel on 7/04/15.
 * Contributions of alex.
 *
 * This driver allows you to, basically, play with MPs, attributes definitions
 * and the relationship between MPs and attributes (values).
 * One can:
 *  ADD MPs, attributes definitions and values
 *  DELETE MPs, attributes definitions and values
 *  PRINT MPs, attributes information and the hole congress of MPs
 *  MODIFY attributes definitions
 *
 */
public class MPDriver {

    public static void main(String[] args) {

        System.out.println("=====================");
        System.out.println("MP & ATTRIBUTE DRIVER");
        System.out.println("=====================");

        System.out.println("Options:");
        System.out.println("USA districts:" +
                "\tAL, AK, AZ, AR, CA, CO, CT, DE, FL, GA,\n" +
                "\tHI, ID, IL, IN, IA, KS, KY, LA, ME, MD,\n" +
                "\tMA, MI, MN, MS, MO, MT, NE, NV, NH, NJ,\n" +
                "\tNM, NY, NC, ND, OH, OK, OR, PA, RI, SC,\n" +
                "\tSD, TN, TX, UT, VT, VA, WA, WV, WI, WY");
        System.out.println("1: Add MP");
        System.out.println("2: Add attribute definition");
        System.out.println("3: Add attribute value to an MP");
        System.out.println("4: Delete MP");
        System.out.println("5: Delete attribute definiton");
        System.out.println("6: Delete attribute value of an MP");
        System.out.println("7: Print MP information");
        System.out.println("8: Print attribute information");
        System.out.println("9: Print the congress");
        System.out.println("10: Print the attribute definition list");
        System.out.println("11: Modify attribute importance");
        System.out.println("99: Show options menu");
        System.out.println("0: Exit");

        //INITIALIZATION START

        Congress c = new Congress();
        Scanner reader = new Scanner(System.in);
        int opcode;
        String name;
        MP m;
        State s;
        int distr;
        int imp;
        AttrDefinition ad;
        opcode = reader.nextInt();
        Object value;

        //INITIALIZATION END

        while(opcode != 0) {
            reader.nextLine();
            switch (opcode) {
                case 1: //Add MP
                    System.out.println("Adding MP");
                    System.out.println("Enter the name:");
                    name = reader.nextLine();
                    System.out.println("Enter the state:");
                    s = State.valueOf(reader.nextLine().toUpperCase());
                    System.out.println("Enter the district number:");
                    distr = Integer.parseInt(reader.nextLine());
                    m = new MP(name, s, distr);
                    if (c.getMP(s, distr) == null) c.addNode(m);
                    else System.out.println("There's an MP already assigned to this state and district");
                    break;
                case 2: //Add attribute definition
                    System.out.println("Adding AttrDefinition");
                    System.out.println("Enter the name:");
                    name = reader.nextLine();
                    System.out.println("Enter the importance (integer between 0 and 3):");
                    imp = Integer.parseInt(reader.nextLine());
                    while (imp < 0 || imp > 3) {
                        System.out.print("The importance must be an integer between 0 and 3\n"
                                         + "Enter the importance again:");
                        imp = Integer.parseInt(reader.nextLine());
                    }
                    ad = new AttrDefinition(name, imp);
                    c.addAttrDef(ad);
                    break;
                case 3: //Add attribute value to an MP
                    System.out.println("Adding attribute value to an MP");
                    System.out.println("Enter attribute name:");
                    name = reader.nextLine();
                    System.out.println("Enter attribute value:");
                    value = reader.nextLine();
                    System.out.println("Enter MP state:");
                    s = State.valueOf(reader.nextLine().toUpperCase());
                    System.out.println("Enter MP district number:");
                    distr = Integer.parseInt(reader.nextLine());
                    ad = c.getAttrDef(name);
                    Attribute a = new Attribute(ad, value);
                    m = c.getMP(s, distr);
                    m.addAttribute(a);
                    break;
                case 4: //Delete MP
                    System.out.println("Deleting MP");
                    System.out.println("Enter MP state:");
                    s = State.valueOf(reader.nextLine().toUpperCase());
                    System.out.println("Enter MP district number:");
                    distr = Integer.parseInt(reader.nextLine());
                    m = c.getMP(s, distr);
                    c.removeNode(m);
                    break;
                case 5: //Delete attribute definition
                    System.out.println("Deleting attribute definition");
                    System.out.println("Enter attribute name:");
                    name = reader.nextLine();
                    ad = c.getAttrDef(name);
                    for (MP m1 : c.getMPs()) {
                        if (m1.hasAttribute(ad)) m1.removeAttribute(ad);
                    }
                    c.removeAttrDef(ad);
                    break;
                case 6: //Delete attribute value of an MP
                    System.out.println("Deleting attribute value of an MP");
                    System.out.println("Enter attribute name");
                    name = reader.nextLine();
                    System.out.println("Enter MP state:");
                    s = State.valueOf(reader.nextLine().toUpperCase());
                    System.out.println("Enter MP district number:");
                    distr = Integer.parseInt(reader.nextLine());
                    ad = c.getAttrDef(name);
                    m = c.getMP(s, distr);
                    if (m.hasAttribute(ad)) m.removeAttribute(ad);
                    else System.out.println("This MP had no assigned value for that attribute\n");
                    break;
                case 7: //Print MP information
                    System.out.println("Printing an MP");
                    System.out.println("Enter MP state:");
                    s = State.valueOf(reader.nextLine().toUpperCase());
                    System.out.println("Enter MP district number:");
                    distr = Integer.parseInt(reader.nextLine());
                    m = c.getMP(s, distr);
                    System.out.println(m);
                    break;
                case 8: //Print attribute information
                    System.out.println("Printing attribute information");
                    System.out.println("Enter attribute name");
                    name = reader.nextLine();
                    ad = c.getAttrDef(name);
                    System.out.println(ad);
                    break;
                case 9: //Print the congress
                    System.out.println("Printing the congress");
                    System.out.println(c);
                    break;
                case 10: //Print attribute definition list
                    System.out.println("Printing attribute definition list");
                    String out = c.printAttrDefList();
                    System.out.println(out);
                    break;
                case 11: //Modify attribute importance
                    System.out.println("Modifying attribute importance");
                    System.out.println("Enter attribute name");
                    name = reader.nextLine();
                    System.out.println("Enter the new importance (integer between 0 and 3):");
                    imp = Integer.parseInt(reader.nextLine());
                    while (imp < 0 || imp > 3) {
                        System.out.print("The importance must be an integer between 0 and 3\n"
                                + "Enter the importance again:");
                        imp = Integer.parseInt(reader.nextLine());
                    }
                    ad = c.getAttrDef(name);
                    ad.setImportance(imp);
                    break;
                case 99: //Options menu
                    System.out.println("Options:");
                    System.out.println("USA districts:" +
                            "\tAL, AK, AZ, AR, CA, CO, CT, DE, FL, GA,\n" +
                            "\tHI, ID, IL, IN, IA, KS, KY, LA, ME, MD,\n" +
                            "\tMA, MI, MN, MS, MO, MT, NE, NV, NH, NJ,\n" +
                            "\tNM, NY, NC, ND, OH, OK, OR, PA, RI, SC,\n" +
                            "\tSD, TN, TX, UT, VT, VA, WA, WV, WI, WY");
                    System.out.println("1: Add MP");
                    System.out.println("2: Add attribute definition");
                    System.out.println("3: Add attribute value to an MP");
                    System.out.println("4: Delete MP");
                    System.out.println("5: Delete attribute definiton");
                    System.out.println("6: Delete attribute value of an MP");
                    System.out.println("7: Print MP information");
                    System.out.println("8: Print attribute information");
                    System.out.println("9: Print the congress");
                    System.out.println("10: Print the attribute definition list");
                    System.out.println("11: Modify attribute importance");
                    System.out.println("99: Show options menu");
                    System.out.println("0: Exit");
                    break;
                default:
                    System.out.println("Invalid operation code");
            }
            System.out.println("Operation finished, enter 99 to see option list or enter next command");
            opcode = reader.nextInt();
        }
        System.out.println("Successful exit");
    }
}