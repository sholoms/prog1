/*
 * SP1 2021 Assignment 1 sample solution.
 * Not at all an efficient solution but a level
 * expected by week 3 course material.
 */
import java.util.Scanner;

public class StageC {

	private Scanner sc = new Scanner(System.in);
	private Coach coach;

	public static void main(String[] args) {
		StageC stageC = new StageC();
		stageC.configureCoach();
		stageC.runMenu();
	}

	// Create and configure the coach
	private void configureCoach() {
		int numRows;
		double costStandard, costPensioner, costFrequent;
		String destination;

		System.out.print("Please enter the number of rows of seats : ");
		numRows = Integer.parseInt(sc.nextLine());

		System.out.print("Please enter the coach destination :");
		destination = sc.nextLine();

		System.out.print("Please enter the cost of a standard booking :");
		costStandard = Double.parseDouble(sc.nextLine());

		System.out.print("Please enter the cost of a pensioner booking :");
		costPensioner = Double.parseDouble(sc.nextLine());

		System.out.print("Please enter the cost of a frequent passenger booking :");
		costFrequent = Double.parseDouble(sc.nextLine());

		coach = new Coach(numRows, destination, costStandard, costPensioner, costFrequent);
	}

	// Perform the main menu
	private void runMenu() {
		int selection;
		do {
			displayMenu();
			selection = Integer.parseInt(sc.nextLine());
			processSelection(selection);
		} while (selection != 5);
	}

	private void displayMenu() {
		System.out.println("\n*** DCA Coach Booking Menu ***");
		System.out.println("  1: Display available seats");
		System.out.println("  2: Book seat");
		System.out.println("  3: Refund seat");
		System.out.println("  4: Display report");
		System.out.println("  5: Exit");
		System.out.print("Please enter your selection: ");
	}

	// Calls the appropriate menu method based on user input
	private void processSelection(int selection) {
		switch (selection) {
		case 1:
			coach.displayAvailableSeats();
			break;
		case 2:
			bookSeats();
			break;
		case 3:
			refundSeats();
			break;
		case 4:
			coach.salesReport();
			break;
		case 5:
			System.out.println("Bye");
			break;
		default:
			System.out.println("Invalid selection");
		}

	}

	// Attempt to book coach seats
	private void bookSeats() {
		int numSeats;

		System.out.print("Please enter the number of seats to book: ");
		numSeats = Integer.parseInt(sc.nextLine());
		
		// Reject attempt to book more seats than are available
		if (numSeats > coach.numSeatsAvailable()) {
			System.out.printf("Unable to book %d seats. Only %d available\n",
								numSeats, coach.numSeatsAvailable());
			return;
		}
		
		// Book each of the requested seats
		for (int i = 0; i < numSeats; i++) {
			int seatPos;
			String seatType;
			boolean bookingResult;
			System.out.println("Seats purchased: " + i);
			do {
				System.out.print("Please enter the seat position to book: ");
				seatPos = Integer.parseInt(sc.nextLine());
				coach.printCosts();
				// Validate the seat type
				do {
					System.out.print("Please select a seat type: ");
					seatType = sc.nextLine();
					if (!seatType.equals("S") && !seatType.equals("P") && 
							!seatType.equals("F"))
						System.out.println("Must be one of S/P/F. Try again.");
				} while (!seatType.equals("S") && !seatType.equals("P") && 
						!seatType.equals("F"));

				// Attempt to book the seat
				bookingResult = coach.bookSeat(seatPos, seatType);
				if (bookingResult == false)
					System.out.println("Seat position isn't available. Please try again");
			} while (bookingResult == false);
		}
		coach.printReceipt();
	}

	// Attempt to refund coach seats
	private void refundSeats() {
		int numSeats;

		System.out.print("Please enter the number of seats to refund: ");
		numSeats = Integer.parseInt(sc.nextLine());
		
		// Reject attempt to refund more seats than are available
		if (numSeats > coach.numSeatsBooked()) {
			System.out.printf("Unable to refund %d seats. Only %d booked\n", 
					numSeats, coach.numSeatsBooked());
			return;
		}
		// Refund each of the requested seats
		for (int i = 0; i < numSeats; i++) {
			int seatPos;
			boolean refundResult;
			System.out.println("Seats refunded: " + i);
			do {
				System.out.print("Please enter the seat position to refund: ");
				seatPos = Integer.parseInt(sc.nextLine());
				// Attempt to refund seat
				refundResult = coach.refundSeat(seatPos);
				if (refundResult == false)
					System.out.println("Seat position wasn't booked. Please try again");
			} while (refundResult == false);
		}
		coach.printReceipt();
	}
}
