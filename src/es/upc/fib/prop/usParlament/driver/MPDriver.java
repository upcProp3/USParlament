package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.usParlament.domain.Congress;
import es.upc.fib.prop.usParlament.domain.MP;
import es.upc.fib.prop.usParlament.domain.State;

import java.util.Scanner;

/**
 * Created by miquel on 7/04/15.
 */
public class MPDriver
{
    public static void main(String[] args)
    {
        Graph g = new Congress();
        Scanner reader = new Scanner(System.in);
        Boolean active = true;
        while(active){
            int input;
            System.out.println("Enter 1 to create MP, 2 to print the Congress,3 to print the state abbreviations, 0 to exit");
            input = reader.nextInt();
            switch(input) {
                case 1:
                    System.out.println("Enter the name of the MP");
                    reader.nextLine();
                    String nom = reader.nextLine();
                    System.out.println("Enter the state abbreviature");
                    State estat = State.valueOf(reader.nextLine().toUpperCase());
                    System.out.println("Enter the district number");
                    Integer distr = Integer.parseInt(reader.nextLine());
                    System.out.println(g.addNode(new MP(nom,estat,distr)));
                    break;
                case 2:
                    System.out.println(g);
                    break;
                case 3:
                    int i = 0;
                    for(State s:State.values()){
                        System.out.print(s+",");
                        if(i++>10) {
                            System.out.println();
                            i=0;
                        }
                    }
                    System.out.println();
                    break;
                case 0:
                    active = false;
                    System.out.println("End of driver");
                    break;
                default:
                    System.out.println("Unknown Command");
                    break;
            }
        }

    }
}
