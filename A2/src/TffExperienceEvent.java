
public class TffExperienceEvent extends TtfEvent{
	private int maxSeats;
	private String[] bookings;

	public TffExperienceEvent(String[] details, double[] prices) {
		super(details, prices);
		this.maxSeats = Integer.parseInt(details[3]);
		this.bookings = new String[maxSeats];
	}

	@Override
	public boolean bookTicket(String ticketType, String name) {
		boolean ticketValid = true;
		if (this.seatsSold == this.maxSeats){
			ticketValid = false;
		}
		if (ticketValid) {
			this.bookings[this.seatsSold] = name + "," + this.getprice(ticketType);
			super.bookTicket(ticketType, name);
		}
		return ticketValid;
	}
	
	@Override
	public void displayEvent() {
		super.displayEvent();
		System.out.println("Maximum seats		: " + this.maxSeats);
		System.out.println("Bookings		: ");
		String[] details;
		for(int i = 0; i < this.seatsSold; i++) {
			details = this.ticktDetails(i);
			System.out.printf("   %-20s : $%s\n", details[0], details[1]);
		}
	}
	
	public void refundTicket(String name) {
		if (this.seatsSold == 0) {
			System.out.println("Error - no seats to refund");
			return;
		}
//		make sure ticket exists and get name index
		int ticketIndex = this.findTicketIndex(name);
		if (ticketIndex < 0) {
			System.out.println("Error - no ticket with the name " + name + " could be found");
			return;
		}
		
		String[] details = this.ticktDetails(ticketIndex);
		System.out.println("Cancelled ticket:");
		System.out.printf("   %-20s : $%s\n", details[0], details[1]);
		this.removeTicket(ticketIndex);
	}
	

	private void removeTicket(int index) {
		for(int i = index; i  < this.seatsSold - 1; i ++) {
			this.bookings[i] = this.bookings[i + 1];
		}
		this.seatsSold --;
	}
	
	private String[] ticktDetails(int index) {
		String[] details = new String[2];
		details = this.bookings[index].split(",");
		return details;
	}
	
	private int findTicketIndex(String name) {
		int eventIndex = -1;
		for (int i = 0; i < this.seatsSold; i++) {
			if (this.bookings[i].split(",")[0].equals(name)) {
				eventIndex = i;
			}
		}
		return eventIndex;
	}
	

}
