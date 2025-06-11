public class Booking {
	private String bookingId;
    private String memberId;
    private String movieId;
    private String showTime;
    private int numberOfTickets;

    public Booking(String bookingId, String memberId, String movieId, String showTime, int numberOfTickets) {
        this.bookingId = bookingId;
        this.memberId = memberId;
        this.movieId = movieId;
        this.showTime = showTime;
        this.numberOfTickets = numberOfTickets;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getShowTime() {
        return showTime;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }
}