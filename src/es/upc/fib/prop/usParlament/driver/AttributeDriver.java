package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.shared13.Graph;
import es.upc.fib.prop.usParlament.domain.Attribute;
import es.upc.fib.prop.usParlament.domain.AttributeDefinition;
import es.upc.fib.prop.usParlament.domain.MP;
import es.upc.fib.prop.usParlament.domain.State;

import java.io.IOException;
import java.util.*;

/**
 * Created by ondrej on 22.4.15.
 */
public class AttributeDriver {

	private static List<MP> mps = new ArrayList<>();;
	private static List<AttributeDefinition> definitions = new ArrayList<>();;

	public static void main(String[] args)
	{
		System.out.println("=====================================");
		System.out.println("   A T T R I B U T E   D R I V E R ");

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
					showMPs();
					break;
				case 2:
					showDefinitions();
					break;
				case 3:
					addDefinition(reader);
					break;
				case 4:
					removeDefinition(reader);
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

	private static void removeDefinition(Scanner reader) {
		System.out.println("Define id: ");
		int id;
		while(true) {
			try {
				id = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.out.println("Id has to be integer. Try it again.");
				continue;
			}
		}
		for (Iterator<AttributeDefinition> i = definitions.listIterator(); i.hasNext(); ) {
			AttributeDefinition def = i.next();
			if (def.getId() == id) {
				i.remove();
			}
		}
	}

	private static void addDefinition(Scanner reader) {
		System.out.println("Set name: ");
		String name = reader.next();
		System.out.println("Set importance: ");
		int importance;
		while(true) {
			try {
				importance = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.out.println("Importance has to be integer. Try it again.");
				continue;
			}
		}
		System.out.println("Set id: ");
		int id;
		while(true) {
			try {
				id = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.out.println("id has to be integer. Try it again.");
				continue;
			}
		}
		AttributeDefinition def = new AttributeDefinition(name, importance);
		def.setId(id);
		definitions.add(def);
	}

	private static void showMPs() {
		for (MP mp : mps) {
			System.out.println("-------------------------------------");
			System.out.println(mp);
		}
	}

	private static void showDefinitions() {
		for (AttributeDefinition def : definitions) {
			System.out.println("-------------------------------------");
			System.out.println(def);
		}
	}

	private static void prepareData() {
		MP mp1 = new MP("Ondrej Velisek", State.FL, 1);
		mp1.setId(1);
		MP mp2 = new MP("Alex Miro", State.NY, 1);
		mp2.setId(2);
		MP mp3 = new MP("Miquel Jubert", State.LA, 1);
		mp3.setId(4);
		MP mp4 = new MP("Alex Sacrest", State.NY, 2);
		mp4.setId(8);

		mps.add(mp1);
		mps.add(mp2);
		mps.add(mp3);
		mps.add(mp4);


		AttributeDefinition sex = new AttributeDefinition("sex", 1);
		sex.setId(3);
		AttributeDefinition religion = new AttributeDefinition("religion", 3);
		religion.setId(6);
		AttributeDefinition age = new AttributeDefinition("age", 2);
		age.setId(9);

		definitions.add(sex);
		definitions.add(religion);
		definitions.add(age);


		mp1.addAttribute(new Attribute(sex, "male"));
		mp1.addAttribute(new Attribute(religion, "atheist"));
		mp1.addAttribute(new Attribute(age, "21"));
		mp2.addAttribute(new Attribute(sex, "male"));
		mp2.addAttribute(new Attribute(religion, "catolic"));
		mp3.addAttribute(new Attribute(sex, "male"));
		mp3.addAttribute(new Attribute(religion, "catolic"));
		mp4.addAttribute(new Attribute(sex, "male"));
		mp4.addAttribute(new Attribute(age, "28"));
	}

}
