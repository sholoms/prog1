
public abstract class Item {
	protected static int numOfItems = 0;
	private static double totalIncome = 0.00;
	protected static String nextID = "1";

	public static int getNumItems() {
		return numOfItems;
	}

	public static double getTotalIncome() {
		return totalIncome;
	}

	public static void setTotalIncome(Double income) {
		Item.totalIncome = income;
	}

	private static void incrementNextId() {
		int id = Integer.parseInt(Item.nextID);
		int newNextID = id + 1;
		Item.nextID = "" + newNextID;
	}

	protected String title;
	protected String description;
	protected double price;
	protected String id;
	protected int hired = 0;

	public Item(String[] details) {
		this.title = details[0];
		this.description = details[1];
		Item.numOfItems++;
		this.id = Item.nextID;
		Item.incrementNextId();
	}

	public void displayItem() {
		System.out.println("ID		: " + this.id);
		System.out.println("Title		: " + this.title);
		System.out.println("Description	: " + this.description);
		System.out.println("Status		: " + this.forHire());
	}

	public abstract void hireItem(String customerID, int numWeeks) throws HiringException;

	protected abstract double determinePrice();

	public void returnItem() throws HiringException {
//		throws an exception if not hired
		if (this.hired == 0) {
			throw new HiringException("Error - item with the ID " + id + " is not currently hired");
		}

		this.hired = 0;
	}

	protected void printReceipt(String customerID, int numWeeks, double cost) {
		System.out.printf("%-12s : %s\n", "Customer", customerID);
		System.out.printf("%-12s : %s\n", "Item ID", this.id);
		System.out.printf("%-12s : %s\n", "Title", this.title);
		System.out.printf("%-12s : %d weeks\n", "hire period", numWeeks);
		Item.totalIncome += cost;
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
