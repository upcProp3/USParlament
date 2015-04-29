package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.usParlament.domain.Congress;
import es.upc.fib.prop.usParlament.domain.MP;
import es.upc.fib.prop.usParlament.domain.State;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by ondrej on 29.4.15.
 */
public class LouvainAlgorithmDriver {

	private static Graph g = new Congress();
	private static int mpId = 1;

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
			System.out.println("1 - add node");
			System.out.println("2 - add edge");
			System.out.println("3 - remove node");
			System.out.println("4 - remove edge");
			System.out.println("5 - show graph");
			System.out.println("7 - calculate best partition");
			//TODO: System.out.println("6 - calculate modularity");
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
				case 7:
					calculateBestPartitions();
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

	private static void addNode(Scanner reader) {
		System.out.println("Set name: ");
		String name = reader.next();
		System.out.println("Set state (by two letter): ");
		State state;
		while(true) {
			try {
				state = State.valueOf(reader.next().toUpperCase());
				break;
			} catch (Exception e) {
				reader.nextLine();
				System.out.println("State has to be given by two letters.");
				continue;
			}
		}
		int dist;
		while(true) {
			try {
				dist = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.out.println("District has to be integer. Try it again.");
				continue;
			}
		}
		MP mp = new MP(name, state, dist);
		mp.setId(mpId);
		mpId ++;
		g.addNode(mp);
	}

	private static void addEdge(Scanner reader) {

	}

	private static void removeNode(Scanner reader) {

	}

	private static void removeEdge(Scanner reader) {

	}

	private static void showGraph() {

	}

	private static void calculateBestPartitions() {

	}

	private static void prepareData() {

	}
}
