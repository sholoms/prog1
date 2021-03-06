
public class TtfEvent {
	private String title;
	private String description;
	private double[] prices;
	protected int seatsSold;

	public TtfEvent(String[] details, double[] prices) {
		this.title = details[0];
		this.description = details[1];
		this.prices = new double[3];
		this.prices[0] = prices[0];
		this.prices[1] = prices[1];
		this.prices[2] = prices[2];
		this.seatsSold = 0;
	}

	public String getTitile() {
		return this.title;
	}

	public void displayEvent() {
		System.out.println("Title			: " + this.title);
		System.out.println("Description		: " + this.description);
		System.out.printf("Price adult		: $%.2f\n", prices[0]);
		System.out.printf("Price concession	: $%.2f\n", prices[1]);
		System.out.printf("Price child		: $%.2f\n", prices[2]);
		System.out.println("Total ticket sales	: " + this.seatsSold);
	}

	public void displayBookingMenu() {
		System.out.println("Please choose from the following seat types:");
		System.out.printf("  1: adult\n");
		System.out.printf("  2: concession\n");
		System.out.printf("  3: child\n");
		System.out.print("Enter choice: ");
	}

	public boolean bookTicket(String ticketType, String name) {
		boolean ticketBooked = false;
		if (ticketType != null) {
			this.seatsSold++;
			this.printTicket(name, ticketType);
			ticketBooked = true;
		}
		return ticketBooked;
	}

	protected void printTicket(String name, String type) {
		System.out.println();
		System.out.println("Title			: " + this.title);
		System.out.println("ticket holder		: " + name);
		System.out.printf("Price			: $%.2f\n", this.getprice(type));
	}

	protected double getprice(String type) {
		double price = 0;
		switch (type) {
		case "adult":
			price = this.prices[0];
			break;
		case "concession":
			price = this.prices[1];
			break;
		case "child":
			price = this.prices[2];
		}
		return price;
	}

//	doubles the size of a string array
	public String[] increaseArraySize(String[] smallArray) {
		String[] bigArray = new String[(smallArray.length * 2)];
		int i = 0;
		while (i < smallArray.length) {
			bigArray[i] = smallArray[i];
			i++;
		}
		return bigArray;
	}
}
