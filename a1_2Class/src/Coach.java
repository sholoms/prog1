import java.util.Arrays;

// class to create and modify a coach
public class Coach {
	private int rows;
	private int totalSeats;
	private int soldSeats;
	private double dollarsEarned;
	private String destination;
	private double standardPrice;
	private double pensionPrice;
	private double frequentPrice;
	private char[] seats;

//	create coach
	public Coach(int rows, String destination, double[] prices) {
		this.rows = rows;
		this.destination = destination;
		this.standardPrice = prices[0];
		this.pensionPrice = prices[1];
		this.frequentPrice = prices[2];
		this.soldSeats = 0;
		this.dollarsEarned = 0.00;
		this.seats = new char[rows * 4];
		Arrays.fill(seats, '-');
	}

// return number of empty seats
	public int remainingSeats() {
		int remainingSeats = this.getTotalSeats() - this.soldSeats;
		return remainingSeats;
	}

// show a map of the seating
	public void displaySeatMap() {
		String seatMap = "\n";
		char seat;
		for (int i = 0; i < this.seats.length; i++) {
			seat = (this.seats[i] != '-' ? 'B' : '-');
			seatMap += ("" + i + ":" + seat + "	");
			if (i % 4 == 3) {
				seatMap += "\n";
			}
		}
		System.out.println(seatMap);
	}

// show user which seats are booked and the number of remaining seats
	public void displaySeats() {
		this.displaySeatMap();
		System.out.println("Number of available seats: " + this.remainingSeats());
	}

// tell user total seats sold, percentage of seats sold and average paid per seat sold
	public void displayReport() {
		System.out.println("Number of sales			: " + this.soldSeats);
		double percentSold = this.soldSeats * 100.00 / this.getTotalSeats();
		System.out.printf("Percentage of seats sold	: %.2f \n", percentSold);
		double averagePrice = this.dollarsEarned / this.soldSeats;
		System.out.printf("Average price			: %.2f \n", averagePrice);
	}

// display booking menu to user
	public void displayBookingMenu(int seatsPurchased) {
		System.out.println("Seats purchased: " + seatsPurchased);
		System.out.printf("  S : Standard seat	$%.2f\n", this.standardPrice);
		System.out.printf("  P : Pension seat	$%.2f\n", this.pensionPrice);
		System.out.printf("  F : Frequent seat	$%.2f\n", this.frequentPrice);
		System.out.print("Please select a seat type: ");
	}

// prints a receipt for a transaction
	public void printReciept(int seatsBooked, int[] seatTypes, String[] seatNums) {
		System.out.println("Reciept");
		System.out.println("*******");
		System.out.println();
		System.out.println("Destination: " + this.destination);
		System.out.println("Number of seats booked: " + seatsBooked);
		double standardTotal = this.standardPrice * seatTypes[0];
		double pensionTotal = this.pensionPrice * seatTypes[1];
		double frequentTotal = this.frequentPrice * seatTypes[2];
		double total = standardTotal + pensionTotal + frequentTotal;

		System.out.printf("%d * Standard @ $%.2f	= $%.2f ", seatTypes[0], this.standardPrice, standardTotal);
		System.out.println(seatNums[0]);
		System.out.printf("%d * Pension @ $%.2f	= $%.2f ", seatTypes[1], this.pensionPrice, pensionTotal);
		System.out.println(seatNums[1]);
		System.out.printf("%d * Frequent @ $%.2f	= $%.2f ", seatTypes[2], this.frequentPrice, frequentTotal);
		System.out.println(seatNums[2]);
		System.out.printf("			------\n");
		System.out.printf("		Total 	: $%.2f\n", total);
	}

// books a single seat 
	public void buySeat(char seatType, int seatNum) {
		this.seats[seatNum] = seatType;
		this.soldSeats++;
		switch (seatType) {
		case 'S':
			this.dollarsEarned += this.standardPrice;
			break;
		case 'P':
			this.dollarsEarned += this.pensionPrice;
			break;
		case 'F':
			this.dollarsEarned += this.frequentPrice;
			break;
		}
	}

// refunds a single seat
	public void refundSeat(int seatNum) {
		char type = this.seats[seatNum];
		switch (type) {
		case 'S':
			this.dollarsEarned -= this.standardPrice;
			break;
		case 'P':
			this.dollarsEarned -= this.pensionPrice;
			break;
		case 'F':
			this.dollarsEarned -= this.frequentPrice;
			break;
		}
		this.seats[seatNum] = '-';
		this.soldSeats--;
	}

// checks to see if a seat is taken
	public boolean seatTaken(int seatNum) {
		boolean taken = true;
		if (seats[seatNum] == '-') {
			taken = false;
		}
		return taken;
	}

// checks to see if a seat is empty
	public boolean seatEmpty(int seatNum) {
		boolean empty = true;
		if (seats[seatNum] != '-') {
			empty = false;
		}
		return empty;
	}

	public char getSeatType(int seatNum) {
		return this.seats[seatNum];
	}

	public int getTotalSeats() {
		return this.seats.length;
	}

	public int getSoldSeats() {
		return this.soldSeats;
	}
}
