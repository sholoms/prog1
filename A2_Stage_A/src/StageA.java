import java.util.Arrays;
import java.util.Scanner;

public class StageA {
	private TtfEvent1[] ttfEvents;
	private int totalEvents;
	private int maxEvents;
	private Scanner sc;

	public StageA() {
		this.sc = new Scanner(System.in);
		this.getMaxEvents();
		this.totalEvents = 0;
		this.runMenu();

		this.sc.close();
	}

	private void getMaxEvents() {
		System.out.println("Please enter the maximum number of events: ");
		int max = Integer.parseInt(sc.nextLine());
		this.ttfEvents = new TtfEvent1[max];
		this.maxEvents = max;
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
				this.addEvent();
				break;
			case 2:
				if (this.checkEventsExist()) {
					this.viewEvent();
				}
				break;
			case 3:
				if (this.checkEventsExist()) {
					this.listEvents();
				}
				break;
			case 4:
				if (this.checkEventsExist()) {
					this.makeBooking();
				}
				break;
			case 5:
				this.refundBooking();
				if (this.checkEventsExist()) {
					this.makeBooking();
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

	private boolean checkEventsExist() {
		boolean valid = true;
		if (this.totalEvents == 0) {
			valid = false;
			System.out.println("Error - There are no events in the system");
		}
		return valid;
	}

	private void refundBooking() {
		// TODO Auto-generated method stub

	}

	private void makeBooking() {
//		get the event and make sure it exists
		System.out.print("Please enter the title of the event you: ");
		String title = sc.nextLine();
		int eventIndex = this.findEventIndex(title);
		if (eventIndex < 0) {
			System.out.println("Error - no event with the name " + title + " could be found");
			return;
		}

		System.out.print("Please enter the number of tickets required: ");
		int tickets = Integer.parseInt(sc.nextLine());
			for(int i = 0; i < tickets; i++) {
			this.ttfEvents[eventIndex].displayBookingMenu();
			int choice = Integer.parseInt(sc.nextLine());
			String type = null;
			switch (choice) {
			case 1:
				type = "adult";
				break;
			case 2:
				type = "concession";
				break;
			case 3:
				type = "child";
				break;
			}
			System.out.print("Please enter the name: ");
			String name = sc.nextLine();
			if (this.ttfEvents[eventIndex].bookTicket(type, name) == false) {
				System.out.println("Error - Invalid seat choice");
			}
		}
	}

	private void listEvents() {
		String[] titles = this.eventTitles();
		for (int i = 0; i < titles.length; i++) {
			System.out.println(titles[i]);
		}
	}

	private String[] eventTitles() {
		String[] titles = new String[this.totalEvents];
		for (int i = 0; i < this.totalEvents; i++) {
			titles[i] = this.ttfEvents[i].getTitile();
		}
		return titles;
	}

	private void viewEvent() {
		System.out.print("Enter event title: ");
		String title = sc.nextLine();
		int eventIndex = this.findEventIndex(title);
		if (eventIndex < 0) {
			System.out.println("Error - no event with the name " + title + " could be found");
		} else {
			this.ttfEvents[eventIndex].displayEvent();
		}
	}

	private int findEventIndex(String title) {
		String[] titles = this.eventTitles();
		int eventIndex = -1;
		for (int i = 0; i < titles.length; i++) {
			if (titles[i].equals(title)) {
				eventIndex = i;
			}

		}
		return eventIndex;
	}

	private void addEvent() {
//		guard statement to prevent adding to many events
		if (this.totalEvents == this.maxEvents) {
			System.out.println("Error - Have reached maximum events");
			return;
		}

		String[] details = this.getDetails();
		double[] prices = this.getPrices();
		this.ttfEvents[this.totalEvents] = new TtfEvent1(details, prices);
		this.totalEvents++;
	}

	private String[] getDetails() {
		String[] details = new String[2];
		System.out.print("Please enter the event title: ");
		details[0] = sc.nextLine();
		System.out.print("Please enter the event description: ");
		details[1] = sc.nextLine();
		return details;
	}

	private double[] getPrices() {
		double[] prices = new double[3];
		System.out.print("Please enter the cost of an adult ticket: ");
		prices[0] = Double.parseDouble(sc.nextLine());
		System.out.print("Please enter the cost of a concession ticket: ");
		prices[1] = Double.parseDouble(sc.nextLine());
		System.out.print("Please enter the cost of a child ticket: ");
		prices[2] = Double.parseDouble(sc.nextLine());
		return prices;
	}

	private void dislplayMainMenu() {
		System.out.println();
		System.out.println("*** Taradale Folk Festival Main Menu ***");
		System.out.println("	1: Add event");
		System.out.println("	2: Veiw event");
		System.out.println("	3: List events");
		System.out.println("	4: Make booking");
		System.out.println("	5: Refund booking");
		System.out.println("	6: Exit");
		System.out.print("Please enter your selection: ");

	}

	public static void main(String[] args) {
		StageA event = new StageA();
	}
}
