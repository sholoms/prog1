/*
 * Sample solution for assignment 1 SP1 2021
 * Not at all the most efficient design or implementation, but
 * the standard expected by week 3 course material.
 */

public class Coach {
	private double costStandard, costPensioner, costFrequent, totalSale = 0;
	private int maxSeats, totalSeatsBooked = 0;
	// The number of seats of each seat category booked/refunded in a booking/refund
	private int nStandard = 0, nPensioner = 0, nFrequent = 0;
	// Strings for storing list of seat numbers booked/refunded in a booking/refund
	private String sStandard = "", sPensioner = "", sFrequent = "";
	// seats: used for storing seat status. One of '-' (available), 'S' (standard), 
	// 'P' (pensioner), 'F' (frequent)
	private char [] seats;
	private String destination;

	// Coach constructor
	public Coach(int nRows, String dest, double cStandard, double cPensioner, double cFrequent) {
		// Each seating row contains 4 seats
		maxSeats = nRows * 4;
		seats = new char[maxSeats];
		// Set all seats to 'available'
		for(int i = 0; i < seats.length; i++)
			seats[i] = '-';
		destination = dest;
		costStandard = cStandard;
		costPensioner = cPensioner;
		costFrequent = cFrequent;
	}
	
	public int numSeatsAvailable() {
		return maxSeats - totalSeatsBooked;
	}
	
	public int numSeatsBooked() {
		return totalSeatsBooked;
	}
	
	public void displayAvailableSeats() {

		// Display seating in rows of 4 seats
		for(int i = 0; i < seats.length; i++) {
			if (i % 4 == 0)
				System.out.println();
			// Display seat as either available '-' or booked 'B'
			System.out.printf("%2d:%c ",i,seats[i] == '-' ? '-': 'B');
		}
		System.out.printf("\nNumber of available seats: %d\n",maxSeats - totalSeatsBooked);
	}

	public void printCosts() {
		System.out.printf("  S : Standard seat  $%.2f\n",costStandard);
		System.out.printf("  P : Pensioner seat $%.2f\n",costPensioner);
		System.out.printf("  F : Frequent  seat $%.2f\n",costFrequent);
	}
	
	// Print out a receipt. Clears list of seat numbers booked/refunded in
	// that booking/refund
	public void printReceipt(){
		double totalCostStandard, totalCostPensioner, totalCostFrequent;
		totalCostStandard = nStandard * costStandard;
		totalCostPensioner = nPensioner * costPensioner;
		totalCostFrequent = nFrequent * costFrequent;
		
		System.out.println("\nReceipt\n*******\n");
		System.out.printf("Destination : %s\n",destination);
		System.out.printf( "Number of seats booked : %d\n",nStandard + nPensioner + nFrequent);
		System.out.printf( "  %2d * Standard  @ $%-6.2f = $%6.2f seat(s) %s\n",nStandard,
								costStandard,totalCostStandard,sStandard);
		System.out.printf( "  %2d * Pensioner @ $%-6.2f = $%6.2f seat(s) %s\n",nPensioner,
								costPensioner,totalCostPensioner,sPensioner);
		System.out.printf( "  %2d * Frequent  @ $%-6.2f = $%6.2f seat(s) %s\n",nFrequent,
								costFrequent,totalCostFrequent, sFrequent);
		System.out.printf( "                             ------\n");
		System.out.printf( "                     Total : $%6.2f\n",
				totalCostStandard + totalCostPensioner + totalCostFrequent);
		
		nStandard = 0;
		nPensioner = 0;
		nFrequent = 0;
		sStandard = "";
		sPensioner = "";
		sFrequent = "";
	}
	
	// Attempt to book a seat. 
	public boolean bookSeat(int seatPos, String seatType) {
		
		// Reject invalid seat number (location)
		if (seatPos < 0 || seatPos >= seats.length)
			return false;
		// Reject booking if seat isn't available
		if (seats[seatPos] != '-')
			return false;
		
		totalSeatsBooked++;
		switch(seatType) {
		case "S":
			seats[seatPos] = 'S';
			totalSale += costStandard;
			nStandard++;
			sStandard += (seatPos + ", ");
			break;
		case "P":
			seats[seatPos] = 'P';
			totalSale += costPensioner;
			nPensioner++;
			sPensioner += (seatPos + ", ");
			break;
		case "F":
			seats[seatPos] = 'F';
			totalSale += costFrequent;
			nFrequent++;
			sFrequent += (seatPos + ", ");
			break;	
		}
		return true;
	}

	// Attempt to refund a seat. 
    public boolean refundSeat(int seatPos) {
		char seatType;
		// Reject invalid seat number (location)
		if (seatPos < 0 || seatPos >= seats.length)
			return false;
		
		seatType = seats[seatPos];
		// Reject refund if seat isn't booked
		if (seatType == '-')
			return false;
		
		totalSeatsBooked--;
		switch(seatType) {
		case 'S':
			totalSale -= costStandard;
			nStandard++;
			sStandard += (seatPos + ", ");
			break;
		case 'P':
			totalSale -= costPensioner;
			nPensioner++;
			sPensioner += (seatPos + ", ");
			break;
		case 'F':
			totalSale -= costFrequent;
			nFrequent++;
			sFrequent += (seatPos + ", ");
			break;	
		}
		seats[seatPos] = '-';
		return true;
	}
		
	public void salesReport() {
		
		double averagePrice = 0, percentageSold = 0;
		
		if (totalSeatsBooked > 0) {
			averagePrice = ((double) totalSale) / totalSeatsBooked;
			percentageSold = (100.0 * totalSeatsBooked) / maxSeats;
		}
		System.out.printf("Number of sales     : %d\n",totalSeatsBooked);
		System.out.printf("Percent seats sold  : %-6.2f\n", percentageSold);
		System.out.printf("Average price       : $%-6.2f\n",averagePrice);
	}
}
