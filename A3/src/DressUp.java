
public final class DressUp extends Item {
	private String genre;
	private int pieces;
	private String size;
	private String[] hireDetails = new String[2];

	public DressUp(String[] details, String genre, int pieces, String size) {
		super(details);

		this.genre = genre;
		this.size = size;
		this.pieces = pieces;
		this.price = 3.5;
		System.out.println("Added item " + this.id);
	}

	public DressUp(String[] details) {
		super(details);

		this.hired = Integer.parseInt(details[3]);
		this.price = Double.parseDouble(details[4]);
		this.pieces = Integer.parseInt(details[5]);
		this.genre = details[6];
		this.size = details[7];
		if (this.hired == 1) {
			this.hireDetails[0] = details[8];
			this.hireDetails[1] = details[9];
		}
	}

	public String toString() {
		String stringItem = "";
		stringItem += this.title + ":";
		stringItem += this.description + ":";
		stringItem += "2" + ":";
		stringItem += this.hired + ":";
		stringItem += this.price + ":";
		stringItem += this.pieces + ":";
		stringItem += this.genre + ":";
		stringItem += this.size + ":";
		stringItem += this.hireDetails[0] + ":";
		stringItem += this.hireDetails[1];
		return stringItem;
	}

	public void displayItem() {
		super.displayItem();

		System.out.printf("Price		: $%.2f per piece/week + $3\n", this.price);
		System.out.println("Pieces		: " + this.pieces);
		System.out.println("Genre		: " + this.genre);
		System.out.println("Size		: " + this.size);
// 		if the dress up is hired print the hire details
		if (this.hired == 1) {
			System.out.printf("Hired by	: %s\n", this.hireDetails[0]);
			System.out.printf("Hire period	: %s weeks\n", this.hireDetails[1]);
		}
	}

	@Override
	public void hireItem(String customerID, int numWeeks) throws HiringException {
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
		double price;
		price = (3.5 * this.pieces * Integer.parseInt(this.hireDetails[1]) + 3);
		return price;
	}

}
