
public final class Toy extends Item {
	private String category;
	private String[] hireDetails = new String[2];

	public Toy(String[] details, String category) {
		super(details);
		switch (category) {
		case "construction":
			this.category = "Construction";
			this.price = 5.45;
			break;
		case "ride-on":
			this.category = "Ride-On";
			this.price = 8.00;
			break;
		case "sport":
			this.category = "Sport";
			this.price = 6.50;
			break;
		default:
			Item.nextID = this.id;
			Item.numOfItems -= 1;
			throw new IllegalArgumentException("Invalid category");
		}
		System.out.println("Added item " + this.id);
	}

	public Toy(String[] details) {
		super(details);

		this.hired = Integer.parseInt(details[3]);
		this.price = Double.parseDouble(details[4]);
		this.category = details[5];
		if (this.hired == 1) {
			this.hireDetails[0] = details[6];
			this.hireDetails[1] = details[7];
		}
	}

	public String toString() {
		String stringItem = "";
		stringItem += this.title + ":";
		stringItem += this.description + ":";
		stringItem += "1" + ":";
		stringItem += this.hired + ":";
		stringItem += this.price + ":";
		stringItem += this.category + ":";
		stringItem += this.hireDetails[0] + ":";
		stringItem += this.hireDetails[1];
		return stringItem;
	}

	public void displayItem() {
		super.displayItem();
		System.out.printf("Price/week	: $%.2f\n", this.price);
		System.out.println("Category	: " + this.category);
// 		if the toy is hired print the hire details
		if (this.hired == 1) {
			System.out.printf("Hired by	: %s\n", this.hireDetails[0]);
			System.out.printf("Hire period	: %s weeks\n", this.hireDetails[1]);
		}
	}

	public void hireItem(String customerID, int numWeeks) throws HiringException {
//		return false if item is already hired
		if (this.hired == 1) {
			throw new HiringException("Error - item with the ID " + this.id + " is currently unavailable");
		}

		this.hired = 1;
		this.hireDetails[0] = customerID;
		this.hireDetails[1] = "" + numWeeks;
		double cost = this.determinePrice();
		super.printReceipt(customerID, numWeeks, cost);
	}

	@Override
	protected double determinePrice() {
		double cost = this.price * Integer.parseInt(this.hireDetails[1]);
		return cost;
	}
}
