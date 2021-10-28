import java.util.Arrays;
import java.util.Scanner;

//main class used to interact directly with the user
public class StageC {
	private Scanner sc;
	private Coach coach;

	public StageC() {
		sc = new Scanner(System.in);
//		get required info and create coach
		int rows = this.getCoachRows();
		String destination = this.getCoachdestination();
		double[] prices = new double[3];
		prices = this.getCoachprices();
		coach = new Coach(rows, destination, prices);

//		initialize/declare variables used in loop
		boolean progopen = true;
		int choice;
//		run menu till closed
		while (progopen) {
			this.dislplayMainMenu();
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1:
				coach.displaySeats();
				break;
			case 2:
				this.bookSeats();
				break;
			case 3:
				this.refundSeat();
				break;
			case 4:
				coach.displayReport();
				break;
			case 5:
				progopen = false;
				break;
			default:
				System.out.println("Invalid selection");
			}
		}
		sc.close();
	}

//display the menu to the user
	public void dislplayMainMenu() {
		System.out.println();
		System.out.println("*** DCA Coach Main Menu ***");
		System.out.println("	1: Display avilable seats");
		System.out.println("	2: Book seat");
		System.out.println("	3: Refund seat");
		System.out.println("	4: Display report");
		System.out.println("	5: Exit");
		System.out.print("Please enter your selection: ");

	}

//get number of rows in the coach
	public int getCoachRows() {
		System.out.print("Please enter the number of rows of seats: ");
		int rows = Integer.parseInt(this.sc.nextLine());
		return rows;
	}

//get destination of the coach
	public String getCoachdestination() {
		System.out.print("Please enter the destination: ");
		String destination = sc.nextLine();
		return destination;
	}

//get prices for seats on the coach
	public double[] getCoachprices() {
		double[] prices = new double[3];
		System.out.print("Please enter the cost of a standard booking: ");
		prices[0] = Double.parseDouble(sc.nextLine());
		System.out.print("Please enter the cost of a pensioner booking: ");
		prices[1] = Double.parseDouble(sc.nextLine());
		System.out.print("Please enter the cost of a frequent passenger booking: ");
		prices[2] = Double.parseDouble(sc.nextLine());
		return prices;
	}

//book seats on the coach
	public void bookSeats() {
//		get number of seats and make sure is valid
		System.out.println("Please enter the number of seats to book: ");
		int seatsToBook = Integer.parseInt(sc.nextLine());
		if (seatsToBook > coach.remainingSeats() || seatsToBook < 1) {
			String error = "Unable to book " + seatsToBook;
			error += " seats. Only " + coach.remainingSeats() + " seats remaining";
			System.out.println(error);
			return;
		}
		int bookingNumber = 0;
		int[] seatTypes = new int[3];
		int seatNumber;
		boolean invalidChoice;
		String[] seatNums = new String[3];
		Arrays.fill(seatNums, " Seats: ");
		char type;
//		book seats until the desired number is booked
		while (bookingNumber < seatsToBook) {
//			get seat type and validate
			coach.displayBookingMenu(bookingNumber);
			type = sc.nextLine().charAt(0);
			while (type != 'S' && type != 'P' && type != 'F') {
				System.out.println("Invalid selection");
				coach.displayBookingMenu(bookingNumber);
				type = sc.nextLine().charAt(0);
			}
//			get specific seat and validate
			do {
				invalidChoice = false;
				coach.displaySeatMap();
				System.out.println("Please select your seat: ");
				seatNumber = Integer.parseInt(sc.nextLine());
				if (seatNumber >= coach.getTotalSeats() || seatNumber < 0 || coach.seatTaken(seatNumber)) {
					invalidChoice = true;
					System.out.println("Invalid choice");
				}
			} while (invalidChoice);
			switch (type) {
			case 'S':
				seatNums[0] += seatNumber + ", ";
				seatTypes[0]++;
				break;
			case 'P':
				seatNums[1] += seatNumber + ", ";
				seatTypes[1]++;
				break;
			case 'F':
				seatNums[2] += seatNumber + ", ";
				seatTypes[2]++;
				break;
			}
			coach.buySeat(type, seatNumber);
			bookingNumber++;
		}

		coach.printReciept(seatsToBook, seatTypes, seatNums);
	}

// refund seats on the coach
	public void refundSeat() {
//		get number of seats to refund and validate
		System.out.println("Please enter the number of seats to refund: ");
		int seatsToRefund = Integer.parseInt(sc.nextLine());
		if (seatsToRefund > coach.getSoldSeats() || seatsToRefund < 1) {
			String error = "Unable to refund " + seatsToRefund;
			error += " seats. Only " + coach.getSoldSeats() + " seats sold";
			System.out.println(error);
			return;
		}

		int refundNumber = 0;
		int[] seatTypes = new int[3];
		int seatNumber;
		String[] seatNums = new String[3];
		Arrays.fill(seatNums, " Seats: ");
		boolean invalidChoice;
//		refund desired number of seatsseats
		while (refundNumber < seatsToRefund) {
//			get seat to refund and validate
			do {
				invalidChoice = false;
				System.out.println("" + refundNumber + " seats refunded");
				coach.displaySeatMap();
				System.out.println("Please selectseat to refund: ");
				seatNumber = Integer.parseInt(sc.nextLine());
				if (seatNumber >= coach.getTotalSeats() || seatNumber < 0 || coach.seatEmpty(seatNumber)) {
					invalidChoice = true;
					System.out.println("Invalid selection");
				}
			} while (invalidChoice);
			switch (coach.getSeatType(seatNumber)) {
			case 'S':
				seatNums[0] += seatNumber + ", ";
				seatTypes[0]++;
				break;
			case 'P':
				seatNums[1] += seatNumber + ", ";
				seatTypes[1]++;
				break;
			case 'F':
				seatNums[2] += seatNumber + ", ";
				seatTypes[2]++;
				break;
			}
			coach.refundSeat(seatNumber);
			refundNumber++;
		}
		coach.printReciept(seatsToRefund, seatTypes, seatNums);

	}

	public static void main(String[] args) {
		StageC coach = new StageC();
	}

}
