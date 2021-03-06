import java.util.Scanner;

public class Stage_A {
	private Scanner sc;
	private Item_A[] holdings = new Item_A[5];

	public Stage_A() {
		this.sc = new Scanner(System.in);
		this.runMenu();

		this.sc.close();
	}

	private void runMenu() {
		boolean progopen = true;
		int choice;
//		run menu till closed
		while (progopen) {
			this.dislplayMainMenu();
			choice = Integer.parseInt(sc.nextLine());
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
				progopen = false;
				break;
			default:
				System.out.println("Error - Invalid selection");
			}
		}
	}

	private void returnItem() {
		System.out.print("Enter item ID: ");
		String id = sc.nextLine();
		int itemIndex = this.findItemIndex(id);
		if (itemIndex < 0) {
			System.out.println("Error - no item with the ID " + id + " could be found");
		}
		
		if(this.holdings[itemIndex].returnItem()) {
			System.out.println("Return succesful");
		}
		else {
			System.out.println("Return failed");
			System.out.println("Error - item with the ID " + id + " is not currently hired");
		}
	}

	private void hireItem() {
		System.out.print("Enter item ID: ");
		String id = sc.nextLine();
		int itemIndex = this.findItemIndex(id);
		if (itemIndex < 0) {
			System.out.println("Error - no item with the ID " + id + " could be found");
			return;
		}
		
		System.out.print("Enter customer ID: ");
		String custID = sc.nextLine();
		System.out.print("Enter the number of weeks to hire: ");
		int numWeeks = Integer.parseInt(sc.nextLine());
		boolean booked = this.holdings[itemIndex].hireItem(custID, numWeeks);
		if(!booked) {
			System.out.println("Error - item with the ID " + id + " is currently unavailable");
		}
	}

	private void listItems() {
		System.out.printf("%-5s : %-15s : %s\n", "ID", "Title", "Status");
		System.out.println("-----------------------------------");
		for (int i = 0; i < Item_A.getNumItems(); i++) {
			holdings[i].listItem();
		}
		System.out.println("-----------------------------------");
//		add up the number of hired items
		int qtyHired = 0;
		for (int i = 0; i < Item_A.getNumItems(); i++) {
			qtyHired += holdings[i].getStatus();
		}
		System.out.printf("%-12s : %d\n", "Qty hired", qtyHired);
		System.out.printf("%-12s : $%.2f\n", "Total income", Item_A.getTotalIncome());
	}

	private void addItem() {
		String[] details = this.getDetails();
		this.holdings[Item_A.getNumItems()] = new Item_A(details);
	}

	private String[] getDetails() {
		String[] details = new String[3];
		System.out.print("Please enter the item title: ");
		details[0] = sc.nextLine();
		System.out.print("Please enter the item description: ");
		details[1] = sc.nextLine();
		System.out.print("Please enter the price per week: ");
		details[2] = sc.nextLine();
		return details;
	}

	private boolean checkItemsExist() {
		boolean itemsExist = true;
		if (Item_A.getNumItems() == 0) {
			itemsExist = false;
			System.out.println("Error - There are no items in the system");
		}
		return itemsExist;
	}

	private void viewItem() {
		System.out.print("Enter item ID: ");
		String id = sc.nextLine();
		int itemIndex = this.findItemIndex(id);
		if (itemIndex < 0) {
			System.out.println("Error - no item with the ID " + id + " could be found");
		} else {
			this.holdings[itemIndex].displayItem();
		}
	}

	private int findItemIndex(String id) {
		int itemIndex = -1;
		for (int i = 0; i < Item_A.getNumItems(); i++) {
			if (this.holdings[i].getID().equals(id)) {
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
		Stage_A event = new Stage_A();
	}

}
