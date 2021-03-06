
public class Item_A {
	private static int numOfItems = 0;
	private static double totalIncome = 0.00;
	private static String nextID = "1";

	public static int getNumItems() {
		return numOfItems;
	}

	public static double getTotalIncome() {
		return totalIncome;
	}

	private static void incrementNextId() {
		int id = Integer.parseInt(Item_A.nextID);
		int newNextID = id + 1;
		Item_A.nextID = "" + newNextID;
	}

	private String title;
	private String description;
	private double price;
	private String id;
	private int hired = 0;
	private String[] hireDetails = new String[2];

	public Item_A(String[] details) {
		this.title = details[0];
		this.description = details[1];
		this.price = Double.parseDouble(details[2]);
		Item_A.numOfItems++;
		this.id = Item_A.nextID;
		Item_A.incrementNextId();
		System.out.println("Added item " + this.id);
	}

	public void displayItem() {
		System.out.println("ID		: " + this.id);
		System.out.println("Title		: " + this.title);
		System.out.println("Description	: " + this.description);
		System.out.printf("Price/week	: $%.2f\n", this.price);
		System.out.println("Status		: " + this.forHire());
		if (this.hired == 1) {
			System.out.printf("Hired by	: %s\n", this.hireDetails[0]);
			System.out.printf("Hire period	: %s weeks\n", this.hireDetails[1]);
		}
	}

	public boolean hireItem(String customerID, int numWeeks) {
//		return false if item is already hired
		if (this.hired == 1) {
			return false;
		}

		this.hired = 1;
		this.hireDetails[0] = customerID;
		this.hireDetails[1] = "" + numWeeks;
		this.printReceipt(customerID, numWeeks);
		return true;
	}

	public boolean returnItem() {
//		return false if item is not hired
		if (this.hired == 0) {
			return false;
		}

		this.hired = 0;
		return true;
	}

	private void printReceipt(String customerID, int numWeeks) {
		System.out.printf("%-12s : %s\n", "Customer", customerID);
		System.out.printf("%-12s : %s\n", "Item ID", this.id);
		System.out.printf("%-12s : %s\n", "Title", this.title);
		System.out.printf("%-12s : %d weeks\n", "hire period", numWeeks);
		double cost = this.price * numWeeks;
		Item_A.totalIncome += cost;
		System.out.printf("%-12s : $%.2f\n", "Total cost", cost);
	}

	private String forHire() {
		String status = "Available";
		if (this.hired == 1) {
			status = "Hired";
		}
		return status;
	}

	public String getID() {
		return this.id;
	}

	public int getStatus() {
		return this.hired;
	}

	public void listItem() {
		System.out.printf("%-5s : %-15s : %s\n", this.id, this.title, this.forHire());
	}

}
