package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.usParlament.domain.MP;
import es.upc.fib.prop.usParlament.domain.TGraph;

import java.util.Scanner;

/**
 * Created by miquel on 7/04/15.
 */
public class MPDriver
{
    public static void main(String[] args)
    {
        Graph g = new TGraph();
        Scanner reader = new Scanner(System.in);
        while(true){
            int input;
            System.out.println("Entra 1 per crear MP, 2 per imprimir");
            input = reader.nextInt();
            if(input == 1) {
                System.out.println("Entra el nom ,l'estat i el districte(numero) en aquest ordre");
                System.out.println(reader.nextLine());
                g.addNode(new MP(reader.nextLine(), reader.nextLine(), Integer.parseInt(reader.nextLine())));
            }else if(input == 2) {
                System.out.println(g);
            }else{
                System.out.println("Comanda dsconeguda");
            }


        }

    }
}
