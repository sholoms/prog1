
public final class PlayEquipment extends Item {
	private int[] dimensions = new int[4];
	private String[] hireDetails = new String[2];

	public PlayEquipment(String[] details, int[] dimensions, double price) {
		super(details);
		this.dimensions = dimensions;
		this.price = price;
		System.out.println("Added item " + this.id);
	}

	public PlayEquipment(String[] details) {
		super(details);

		this.hired = Integer.parseInt(details[3]);
		this.price = Double.parseDouble(details[4]);
		this.dimensions[0] = Integer.parseInt(details[5]);
		this.dimensions[1] = Integer.parseInt(details[6]);
		this.dimensions[2] = Integer.parseInt(details[7]);
		this.dimensions[3] = Integer.parseInt(details[8]);
		if (this.hired == 1) {
			this.hireDetails[0] = details[9];
			this.hireDetails[1] = details[10];
		}
	}

	public String toString() {
		String stringItem = "";
		stringItem += this.title + ":";
		stringItem += this.description + ":";
		stringItem += "3" + ":";
		stringItem += this.hired + ":";
		stringItem += this.price + ":";
		stringItem += this.dimensions[0] + ":";
		stringItem += this.dimensions[1] + ":";
		stringItem += this.dimensions[2] + ":";
		stringItem += this.dimensions[3] + ":";
		stringItem += this.hireDetails[0] + ":";
		stringItem += this.hireDetails[1];
		return stringItem;
	}

	@Override
	public void hireItem(String customerID, int numWeeks) throws HiringException {
		if (this.hired == 1) {
			throw new HiringException("Error - item with the ID " + this.id + " is currently unavailable");
		}

		this.hired = 1;
		this.hireDetails[0] = customerID;
		this.hireDetails[1] = "" + numWeeks;
		double cost = this.price * numWeeks;
		super.printReceipt(customerID, numWeeks, cost);
	}

	public void displayItem() {
		super.displayItem();

		System.out.println("Weight		: " + this.dimensions[0]);
		System.out.println("Hight		: " + this.dimensions[1]);
		System.out.println("Width		: " + this.dimensions[2]);
		System.out.println("Depth		: " + this.dimensions[3]);
		System.out.printf("Price		: $%.2f per week\n", this.price);
// 		if the equipment is hired print the hire details
		if (this.hired == 1) {
			System.out.printf("Hired by	: %s\n", this.hireDetails[0]);
			System.out.printf("Hire period	: %s weeks\n", this.hireDetails[1]);
		}
	}

	@Override
	protected double determinePrice() {
		// TODO Auto-generated method stub
		return 0;
	}

}
