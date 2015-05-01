package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.usParlament.domain.Attribute;
import es.upc.fib.prop.usParlament.domain.AttrDefinition;
import es.upc.fib.prop.usParlament.domain.MP;
import es.upc.fib.prop.usParlament.domain.State;

import java.util.*;

/**
 * Created by ondrej on 22.4.15.
 */
public class AttributeDriver {

	private static List<MP> mps = new ArrayList<>();
	private static List<AttrDefinition> definitions = new ArrayList<>();
	private static Long attrId = (long) 10;
	private static Long defId = (long) 10;

	public static void main(String[] args)
	{
		System.err.println("=====================================");
		System.err.println("   A T T R I B U T E   D R I V E R ");

		Scanner reader = new Scanner(System.in);
		prepareData(reader);

		int input;
		boolean active = true;
		while(active) {
			System.err.println("=====================================");
			System.err.println("1 - add attribute definition");
			System.err.println("2 - add attribute to MP");
			System.err.println("3 - remove attribute definition");
			System.err.println("4 - remove attribute from MP");
			System.err.println("5 - show list of attributes definitions");
			System.err.println("6 - show list of MPs");
			System.err.println("0 - exit");
			try {
				input = reader.nextInt();
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.err.println("Wrong input. Try it again.");
				continue;
			}
			switch (input) {
				case 1:
					addDefinition(reader);
					break;
				case 2:
					addAttribute(reader);
					break;
				case 3:
					removeDefinition(reader);
					break;
				case 4:
					removeAttribute(reader);
					break;
				case 5:
					showDefinitions();
					break;
				case 6:
					showMPs();
					break;
				case 0:
					System.err.println("Bye Bye");
					active = false;
					break;
				default:
					break;
			}

		}

	}

	private static void removeAttribute(Scanner reader) {
		System.err.println("Put MP id: ");
		int mpId;
		while(true) {
			try {
				mpId = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.err.println("Id has to be integer. Try it again.");
				continue;
			}
		}

		MP mp = null;
		for (MP m : mps) {
			if (m.getId() == mpId) {
				mp = m;
			}
		}
		if (mp == null) {
			System.err.println("MP with id " + mpId + " does not exist.");
			return;
		}

		System.err.println("=====================================");
		System.err.println(mp);
		for (Attribute attr : mp.getAttributes()) {
			System.err.println("-------------------------------------");
			System.err.println(attr.getDefinition());
		}

		System.err.println("=====================================");
		System.err.println("Put attribute definition id: ");
		int defId;
		while(true) {
			try {
				defId = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.err.println("Id has to be integer. Try it again.");
				continue;
			}
		}

		AttrDefinition def = null;
		for (Attribute attr : mp.getAttributes()) {
			if (attr.getDefinition().getId() == defId) {
				def = attr.getDefinition();
			}
		}
		if (def == null) {
			System.err.println("MP " +mp.getFullname()+ " does not contains Attribute definition with id " + defId);
			return;
		}

		mp.removeAttribute(def);
		System.err.println("Removed successfully");
	}


	private static void addAttribute(Scanner reader) {
		System.err.println("Put MP id: ");
		int mpId;
		while(true) {
			try {
				mpId = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.err.println("Id has to be integer. Try it again.");
				continue;
			}
		}

		MP mp = null;
		for (MP m : mps) {
			if (m.getId() == mpId) {
				mp = m;
			}
		}
		if (mp == null) {
			System.err.println("MP with id " + mpId + " does not exist.");
			return;
		}

		System.err.println("=====================================");
		System.err.println(mp);
		showDefinitions();

		System.err.println("=====================================");
		System.err.println("Put attribute definition id: ");
		int defId;
		while(true) {
			try {
				defId = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.err.println("Id has to be integer. Try it again.");
				continue;
			}
		}

		AttrDefinition def = null;
		for (AttrDefinition d : definitions) {
			if (d.getId() == defId) {
				def = d;
			}
		}
		if (def == null) {
			System.err.println("Attribute definition with id " + defId + " does not exist.");
			return;
		}

		System.err.println("Set attribute value: ");
		String value = reader.next();
		mp.addAttribute(new Attribute(def, value));
	}

	private static void removeDefinition(Scanner reader) {
		System.err.println("Put attribute definition id: ");
		int id;
		while(true) {
			try {
				id = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.err.println("Id has to be integer. Try it again.");
				continue;
			}
		}
		for (Iterator<AttrDefinition> i = definitions.listIterator(); i.hasNext(); ) {
			AttrDefinition def = i.next();
			if (def.getId() == id) {
				i.remove();
			}
		}
	}

	private static void addDefinition(Scanner reader) {
		System.err.println("Set name: ");
		String name = reader.next();
		System.err.println("Set importance: ");
		int importance;
		while(true) {
			try {
				importance = reader.nextInt();
				break;
			} catch (InputMismatchException e) {
				reader.nextLine();
				System.err.println("Importance has to be integer. Try it again.");
				continue;
			}
		}
		AttrDefinition def = new AttrDefinition(name, importance);
		def.setId(defId);
		defId++;
		definitions.add(def);
	}

	private static void showMPs() {
		for (MP mp : mps) {
			System.out.print("-------------------------------------");
			System.out.println(mp);
		}
	}

	private static void showDefinitions() {
		for (AttrDefinition def : definitions) {
			System.out.print("-------------------------------------");
			System.out.println(def);
		}
	}

	private static void prepareData(Scanner reader) {

		MP mp1 = new MP("Ondrej Velisek", State.FL, 1);
		mp1.setId(1);
		MP mp2 = new MP("Alex Miro", State.NY, 1);
		mp2.setId(2);
		MP mp3 = new MP("Miquel Jubert", State.LA, 1);
		mp3.setId(3);
		MP mp4 = new MP("Alex Sacrest", State.NY, 2);
		mp4.setId(4);

		mps.add(mp1);
		mps.add(mp2);
		mps.add(mp3);
		mps.add(mp4);

		System.err.println("=====================================");
		System.err.println("Do you want to prepare some attribute data? (y/n)");

		while(true) {
			String input = reader.next();
			input = input.toUpperCase().trim();
			if (input.equals("Y")) {
				break;
			} else if (input.equals("N")) {
				return;
			} else {
				System.err.println("Please type only 'y' as yes or 'n' as no");
			}
		}


		AttrDefinition sex = new AttrDefinition("sex", 1);
		sex.setId(3);
		AttrDefinition religion = new AttrDefinition("religion", 3);
		religion.setId(6);
		AttrDefinition age = new AttrDefinition("age", 2);
		age.setId(9);

		definitions.add(sex);
		definitions.add(religion);
		definitions.add(age);


		mp1.addAttribute(new Attribute(sex, "male"));
		mp1.addAttribute(new Attribute(religion, "atheist"));
		mp1.addAttribute(new Attribute(age, "21"));
		mp2.addAttribute(new Attribute(sex, "female"));
		mp2.addAttribute(new Attribute(religion, "catolic"));
		mp3.addAttribute(new Attribute(sex, "male"));
		mp3.addAttribute(new Attribute(religion, "catolic"));
		mp4.addAttribute(new Attribute(sex, "male"));
		mp4.addAttribute(new Attribute(age, "28"));
	}

}
