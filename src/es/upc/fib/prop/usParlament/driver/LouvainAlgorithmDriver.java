package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.usParlament.domain.Congress;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by ondrej on 29.4.15.
 */
public class LouvainAlgorithmDriver {

	private static Graph g = new Congress();

	public static void main(String[] args)
	{
		System.out.println("=====================================");
		System.out.println("   L O U V A I N   D R I V E R ");

		prepareData();

		Scanner reader = new Scanner(System.in);
		int input;
		boolean active = true;
		while(active) {
			System.out.println("=====================================");
			System.out.println("1 - show list of MPs");
			System.out.println("2 - show list of attributes definitions");
			System.out.println("3 - add attribute definition");
			System.out.println("4 - remove attribute definition");
			System.out.println("5 - add attribute to MP");
			System.out.println("6 - remove attribute from MP");
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

	private static void prepareData() {

	}
}
