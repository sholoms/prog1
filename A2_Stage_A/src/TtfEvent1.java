import java.util.HashMap;

public class TtfEvent1 {
	private String title;
	private String description;
	private HashMap<String, Double> prices = new HashMap<String, Double>();
	private int seatsSold;

	public TtfEvent1(String[] details, double[] prices) {
		this.title = details[0];
		this.description = details[1];
		this.prices.put("adult", prices[0]);
		this.prices.put("concession", prices[01]);
		this.prices.put("child", prices[2]);
		this.seatsSold = 0;
	}

	public String getTitile() {
		return this.title;
	}

	public void displayEvent() {
		System.out.println("Title			: " + this.title);
		System.out.println("Description		: " + this.description);
		System.out.printf("Price adult		: $%.2f\n", this.prices.get("adult"));
		System.out.printf("Price concession	: $%.2f\n", this.prices.get("concession"));
		System.out.printf("Price child		: $%.2f\n", this.prices.get("child"));
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

	private void printTicket(String name, String type) {
		System.out.println();
		System.out.println("Title			: " + this.title);
		System.out.println("ticket holder		: " + name);
		System.out.printf("Price adult		: $%.2f\n", this.prices.get(type));
	}

}
