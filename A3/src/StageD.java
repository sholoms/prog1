import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class StageD {
	private Scanner sc;
	private ArrayList<Item> holdings = new ArrayList<Item>();
	PrintWriter pw = null;

	public StageD() {
		this.sc = new Scanner(System.in);
		this.runMenu();

		this.sc.close();
	}

	private void runMenu() {
		boolean progopen = true;
		int choice;

		this.loadFile();
//		run menu till closed
		while (progopen) {
			this.dislplayMainMenu();
			choice = Integer.parseInt(this.sc.nextLine());
			System.out.println();
			switch (choice) {
			case 1:
				this.addItem();
				break;
			case 2:
				if (this.checkItemsExist()) {
					this.viewItem();
				}
				break;
			case 3:
				if (this.checkItemsExist()) {
					this.listItems();
				}
				break;
			case 4:
				if (this.checkItemsExist()) {
					this.hireItem();
				}
				break;
			case 5:
				if (this.checkItemsExist()) {
					this.returnItem();
				}
				break;
			case 6:
				this.saveToFile();
				progopen = false;
				break;
			default:
				System.out.println("Error - Invalid selection");
			}
		}
	}

	private void loadFile() {
		Scanner tempSc = null;
		try {
			tempSc = new Scanner(new FileReader("data.txt"));
			System.out.println("Loading from file");
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}

		if (tempSc != null) {
			Item.setTotalIncome(Double.parseDouble(tempSc.nextLine()));
			while (tempSc.hasNext()) {
				String[] itemDetails = tempSc.nextLine().split(":");
//				check the type of item and react accordingly
				Item item = null;
				switch (Integer.parseInt(itemDetails[2])) {
//				toy
				case 1:
					item = new Toy(itemDetails);
					break;
//				dress-ups
				case 2:
					item = new DressUp(itemDetails);
					break;
//				play equipment
				case 3:
					item = new PlayEquipment(itemDetails);
					break;
				}
				this.holdings.add(item);
			}
			tempSc.close();
		}
	}

	private void saveToFile() {
		try {
			pw = new PrintWriter("data.txt");
		} catch (FileNotFoundException e) {
			System.out.println("I/O error");
		}

		if (pw != null) {
			pw.println(Item.getTotalIncome());
			for (Item i : this.holdings) {
				pw.println(i.toString());
			}
			pw.close();
			System.out.println("Data saved");
		}
	}

	private void returnItem() {
		System.out.print("Enter item ID: ");
		String id = this.sc.nextLine();
		int itemIndex = this.findItemIndex(id);
		if (itemIndex < 0) {
			System.out.println("Error - no item with the ID " + id + " could be found");
		}

		try {
			this.holdings.get(itemIndex).returnItem();
			System.out.println("Return succesful");
		} catch (HiringException e) {
			System.out.println(e.getMessage());
		}
	}

	private void hireItem() {
		System.out.print("Enter item ID: ");
		String id = this.sc.nextLine();
		int itemIndex = this.findItemIndex(id);
		if (itemIndex < 0) {
			System.out.println("Error - no item with the ID " + id + " could be found");
			return;
		}

		System.out.print("Enter customer ID: ");
		String custID = this.sc.nextLine();
		System.out.print("Enter the number of weeks to hire: ");
		int numWeeks = Integer.parseInt(this.sc.nextLine());
		try {
			this.holdings.get(itemIndex).hireItem(custID, numWeeks);
		} catch (HiringException e) {
			System.out.println(e.getMessage());
		}
	}

	private void listItems() {
		System.out.printf("%-5s : %-15s : %s\n", "ID", "Title", "Status");
		System.out.println("-----------------------------------");
		for (int i = 0; i < Item.getNumItems(); i++) {
			holdings.get(i).listItem();
		}
		System.out.println("-----------------------------------");
//		add up the number of hired items
		int qtyHired = 0;
		for (int i = 0; i < Item.getNumItems(); i++) {
			qtyHired += holdings.get(i).getStatus();
		}
		System.out.printf("%-12s : %d\n", "Qty hired", qtyHired);
		System.out.printf("%-12s : $%.2f\n", "Total income", Item.getTotalIncome());
	}

	private void addItem() {

		String[] details = this.getDetails();
		int type = getType();
		Item item = null;
		switch (type) {
//		toy
		case 1:
			String category = "";
			try {
				System.out.print("Please enter the toy category (construction/ride-on/sport): ");
				category = this.sc.nextLine();
				item = new Toy(details, category);

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				System.out.println("Unable to add item");
			}

			break;
//		dress-ups
		case 2:
			System.out.print("Please enter the number of pieces: ");
			int numPieces = Integer.parseInt(this.sc.nextLine());
			System.out.print("Please enter the genre: ");
			String genre = this.sc.nextLine();
			System.out.print("Please enter the size: ");
			String size = this.sc.nextLine();
			item = new DressUp(details, genre, numPieces, size);
			break;
//		play equipment
		case 3:
			int[] dimensions = new int[4];
			System.out.print("Please enter the wight (grams): ");
			dimensions[0] = Integer.parseInt(this.sc.nextLine());
			System.out.print("Please enter the hight (cm): ");
			dimensions[1] = Integer.parseInt(this.sc.nextLine());
			System.out.print("Please enter the width (cm): ");
			dimensions[2] = Integer.parseInt(this.sc.nextLine());
			System.out.print("Please enter the depth (cm): ");
			dimensions[3] = Integer.parseInt(this.sc.nextLine());
			System.out.print("Please enter the price: ");
			double price = Double.parseDouble(this.sc.nextLine());
			item = new PlayEquipment(details, dimensions, price);
			break;
		}
		this.holdings.add(item);
	}

	private int getType() {
		System.out.println("	1: Toy");
		System.out.println("	2: Dress-Ups");
		System.out.println("	3: Play Equipment");
		System.out.print("Please enter the item type: ");
		int type = Integer.parseInt(this.sc.nextLine());
		return type;
	}

	private String[] getDetails() {
		String[] details = new String[2];
		System.out.print("Please enter the item title: ");
		details[0] = this.sc.nextLine();
		System.out.print("Please enter the item description: ");
		details[1] = this.sc.nextLine();
		return details;
	}

	private boolean checkItemsExist() {
		boolean itemsExist = true;
		if (Item.getNumItems() == 0) {
			itemsExist = false;
			System.out.println("Error - There are no items in the system");
		}
		return itemsExist;
	}

	private void viewItem() {
		System.out.print("Enter item ID: ");
		String id = this.sc.nextLine();
		int itemIndex = this.findItemIndex(id);
		if (itemIndex < 0) {
			System.out.println("Error - no item with the ID " + id + " could be found");
		} else {
			this.holdings.get(itemIndex).displayItem();
		}
	}

	private int findItemIndex(String id) {
		int itemIndex = -1;
		for (int i = 0; i < Item.getNumItems(); i++) {
			if (this.holdings.get(i).getID().equals(id)) {
				itemIndex = i;
			}
		}
		return itemIndex;
	}

	private void dislplayMainMenu() {
		System.out.println();
		System.out.println("*** ChildzPlay Toy Hire ***");
		System.out.println("	1: Add Item");
		System.out.println("	2: Veiw item");
		System.out.println("	3: List items");
		System.out.println("	4: Hire item");
		System.out.println("	5: Return item");
		System.out.println("	6: Exit");
		System.out.print("Please enter your selection: ");

	}

	public static void main(String[] args) {
		StageD event = new StageD();
	}

}
