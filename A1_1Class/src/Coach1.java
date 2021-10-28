import java.util.Scanner;
import java.util.Arrays;
import java.util.Arrays;

public class Coach1 {
//	declare global variables
	private int rows;
	private int totalSeats;
	private int soldSeats;
	private double dollarsEarned;
	private String destination;
	private double standardPrice;
	private double pensionPrice;
	private double frequentPrice;
	private Scanner sc;
	private char[] seats;
	
	public Coach1() {
		sc = new Scanner(System.in);
//		initialize global variables with default values
		this.soldSeats = 0;
		this.dollarsEarned = 0.00;		
		this.getCoachInfo();
		
//		initialize/declare variables used in loop
		boolean progopen = true;
		int choice;
		while (progopen) {
			this.dislplayMainMenu();
			
			choice = Integer.parseInt(sc.nextLine());
			
			switch (choice) {
			case 1: 
				this.displaySeats();
				break;
			case 2:
				this.bookSeat();
				break;
			case 3:
				this.refundSeat();
				break;
			case 4: 
				this.displayReport();
				break;
			case 5: 
				progopen = false;
				break;
			default : 
				System.out.println("Invalid selection");
			}
		}
		sc.close();
	}
	
	public void dislplayMainMenu( ) {
		System.out.println();
		System.out.println("*** DCA Coach Main Menu ***");
		System.out.println("	1: Display avilable seats");
		System.out.println("	2: Book seat");
		System.out.println("	3: Refund seat");
		System.out.println("	4: Display report");
		System.out.println("	5: Exit");
		System.out.print("Please enter your selection: ");

	}
	
	public void getCoachInfo() {
//		get rows and set num of seats and seat map
		System.out.print("Please enter the number of rows of seats: ");
		this.rows = Integer.parseInt(sc.nextLine());
		this.totalSeats = rows * 4;
		this.seats = new char[this.totalSeats];
		Arrays.fill(seats, '-');
		
//		get destination
		System.out.print("Please enter the destination: ");
		destination = sc.nextLine();
		
//		get prices
		System.out.print("Please enter the cost of a standard booking: ");
		this.standardPrice = Double.parseDouble(sc.nextLine());
		System.out.print("Please enter the cost of a pensioner booking: ");
		this.pensionPrice = Double.parseDouble(sc.nextLine());
		System.out.print("Please enter the cost of a frequent passenger booking: ");
		this.frequentPrice = Double.parseDouble(sc.nextLine());
	}
	
	public int remainingSeats() {
		int remainingSeats = this.totalSeats - this.soldSeats;
		return remainingSeats;
	}
	
	public void displaySeats() {
		displaySeatMap();
		System.out.println("Number of available seats: " + this.remainingSeats()); 
	}
	
	public void bookSeat() {
//		get number of seats and make sure is valid
		System.out.println("Please enter the number of seats to book: ");
		int seatsToBook = Integer.parseInt(sc.nextLine());
		if (seatsToBook > this.remainingSeats()) {
			String error = "Unable to book " + seatsToBook;
			error += " seats. Only " + this.remainingSeats() + " seats remaining";
			System.out.println(error);
			return;
		}
		int seatNumber = 0;
		int standards = 0;
		int pensions = 0;
		int frequents = 0;
		int seatChoice;
		boolean invalidChoice;
		String standardSeats = " Seats: ";
		String pensionSeats = " Seats: ";
		String frequentSeats = " Seats: ";
		char seatType;
//		get seat type
		while (seatNumber < seatsToBook) {
			this.displayBookingMenu(seatNumber);
			seatType = sc.nextLine().charAt(0);
			while (seatType != 'S' &&
					seatType != 'P' &&
					seatType != 'F') {
				System.out.println("Invalid selection");
				this.displayBookingMenu(seatNumber);
				seatType = sc.nextLine().charAt(0);
			}
//			get specific seat
			do {
				invalidChoice = false;
				displaySeatMap();
				System.out.println("Please select your seat: ");
				seatChoice = Integer.parseInt(sc.nextLine());
				if (seatChoice >= this.totalSeats || seatChoice < 0 || this.seats[seatChoice] != '-') {
					invalidChoice = true;
					System.out.println("Invalid choice");
				}
			}
			while (invalidChoice);
			switch (seatType) {
			case 'S': 
				standardSeats += seatChoice + ", ";
				standards ++;
				break;
			case 'P': 
				pensionSeats += seatChoice + ", ";
				pensions ++;
				break;
			case 'F': 
				frequentSeats += seatChoice + ", ";
				frequents ++;
				break;
			}
			this.seats[seatChoice] = seatType;
			seatNumber ++;
		}
		
		this.printReciept(seatsToBook, standards, pensions, frequents, standardSeats, pensionSeats, frequentSeats);
		this.soldSeats += seatsToBook;
		this.dollarsEarned += totalSale(standards, pensions, frequents);
	}
	
	public double totalSale (int standards, int pensions, int frequents) {
		double totalDollars = 0.00;
		totalDollars += standards * this.standardPrice;
		totalDollars += pensions * this.pensionPrice;
		totalDollars += frequents * this.frequentPrice;
		return totalDollars;
	}
	
	public void displayBookingMenu(int seatsPurchased) {
		System.out.println("Seats purchased: " + seatsPurchased);
		System.out.printf("  S : Standard seat	$%.2f\n", this.standardPrice);
		System.out.printf("  P : Pension seat	$%.2f\n", this.pensionPrice);
		System.out.printf("  F : Frequent seat	$%.2f\n", this.frequentPrice);
		System.out.print("Please select a seat type: ");
	}
	
	public void refundSeat() {
		System.out.println("Please enter the number of seats to refund: ");
		int seatsToRefund = Integer.parseInt(sc.nextLine());
		if (seatsToRefund > this.soldSeats) {
			String error = "Unable to refund " + seatsToRefund;
			error += " seats. Only " + this.soldSeats + " seats sold";
			System.out.println(error);
			return;
		}
		
		int refundNumber = 0;
		int standards = 0;
		int pensions = 0;
		int frequents = 0;
		int seatNumber;
		String standardSeats = " Seats: ";
		String pensionSeats = " Seats: ";
		String frequentSeats = " Seats: ";
		boolean invalidChoice;
		while (refundNumber < seatsToRefund) {
			do {
				invalidChoice = false;
				this.displaySeatMap();
				System.out.println("Please selectseat to refund: ");
				seatNumber = Integer.parseInt(sc.nextLine());
				if (seatNumber >= this.totalSeats || seatNumber < 0 || this.seats[seatNumber] == '-') {
					invalidChoice = true;
					System.out.println("Invalid selection");
				}
			}
			while (invalidChoice);
			switch (this.seats[seatNumber]) {
			case 'S': 
				standardSeats += seatNumber + ", ";
				standards ++;
				refundNumber ++;
				this.seats[seatNumber] = '-';
				break;
			case 'P': 
				pensionSeats += seatNumber + ", ";
				pensions ++;
				refundNumber ++;
				this.seats[seatNumber] = '-';
				break;
			case 'F': 
				frequentSeats += seatNumber + ", ";
				frequents ++;
				refundNumber ++;
				this.seats[seatNumber] = '-';
				break;
			}

		}
		this.printReciept(seatsToRefund, standards, pensions, frequents, standardSeats, pensionSeats, frequentSeats);
		this.soldSeats -= seatsToRefund;
		this.dollarsEarned -= this.totalSale(standards, pensions, frequents);
	}
	
	public void displayReport() {
		System.out.println("Number of sales			: " + this.soldSeats);
		double percentSold = this.soldSeats * 100 / this.totalSeats;
		System.out.printf("Percentage of seats sold	: %.2f \n", percentSold);
		double averagePrice = this.dollarsEarned / this.soldSeats;
		System.out.printf("Average price			: %.2f \n", averagePrice);
	}
	
	public void printReciept(int seatsBooked, int standards, int pensions, int frequents,
			String standardNums, String pensionNums, String frequentNums) {
		System.out.println("Reciept");
		System.out.println("*******");
		System.out.println();
		System.out.println("Destination: " + this.destination);
		System.out.println("Number of seats booked: " + seatsBooked);
		double standardTotal = this.standardPrice * standards;
		double pensionTotal = this.pensionPrice * pensions;
		double frequentTotal = this.frequentPrice * frequents;
		double total = standardTotal + pensionTotal + frequentTotal;

		System.out.printf("%d * Standard @ $%.2f	= $%.2f ", standards, this.standardPrice, standardTotal);
		System.out.println(standardNums);
		System.out.printf("%d * Pension @ $%.2f	= $%.2f ", pensions, this.pensionPrice, pensionTotal);
		System.out.println(pensionNums);
		System.out.printf("%d * Frequent @ $%.2f	= $%.2f ", frequents, this.frequentPrice, frequentTotal);
		System.out.println(frequentNums);
		System.out.printf("			------\n");
		System.out.printf("		Total 	: $%.2f\n", total);
	}
	
	public void displaySeatMap() {
		String seatMap = "\n";
		for(int i = 0; i < this.totalSeats; i++) {
			seatMap += ("" + i + ":" + this.seats[i] + "	");
			if (i % 4 == 3) {
				seatMap += "\n";
			}
		}
		System.out.println(seatMap);
	}
	
	public static void main(String[] args) {
		Coach1 coach = new Coach1();
	}

}
 	

