import java.util.*;
import java.text.SimpleDateFormat;

public class BookingSystem {
	private static List<Booking> bookings = new ArrayList<>();

    public static Booking bookTicket(Member member, String movieId, String showTime, int numberOfTickets) {
        Movie movie = MovieDatabase.getMovie(movieId);
        if (movie == null) {
            System.out.println("找不到電影");
            return null;
        }

        List<String> schedules = MovieDatabase.getSchedules(movieId);
        if (!schedules.contains(showTime)) {
            System.out.println("找不到指定場次");
            return null;
        }

        int bookedSeats = 0;
        for (Booking b : bookings) {
            if (b.getMovieId().equals(movieId) && b.getShowTime().equals(showTime)) {
                bookedSeats += b.getNumberOfTickets();
            }
        }

        if (bookedSeats + numberOfTickets > 100) {
            System.out.println("座位不足");
            return null;
        }

        int age = member.getAge();
        String rating = movie.getRating();
        if (!isAgeAllowed(age, rating)) {
            System.out.println("會員年齡不符合電影分級");
            return null;
        }

        String bookingId = "BKG" + (bookings.size() + 1);
        Booking booking = new Booking(bookingId, member.getUid(), movieId, showTime, numberOfTickets);
        bookings.add(booking);
        return booking;
    }

    public static boolean cancelBooking(Member member, String bookingId) {
        Booking booking = null;
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                booking = b;
                break;
            }
        }
        if (booking == null) {
            return false;
        }

        if (!booking.getMemberId().equals(member.getUid())) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date showTime = sdf.parse(booking.getShowTime());
            Date now = new Date();
            long diffInMillis = showTime.getTime() - now.getTime();
            long diffInMinutes = diffInMillis / (60 * 1000);

            if (diffInMinutes <= 30) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        bookings.remove(booking);
        return true;
    }

    public static List<Booking> getBookingHistory(Member member) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getMemberId().equals(member.getUid())) {
                result.add(booking);
            }
        }
        return result;
    }

    private static boolean isAgeAllowed(int age, String rating) {
        switch (rating) {
            case "G": return true;
            case "PG": return age >= 10;
            case "PG-13": return age >= 13;
            case "R": return age >= 18;
            default: return true;
        }
    }
    public static String getBookingStatus(String bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                return "訂票成功";
            }
        }
        return "查無此訂票";
    }

    public static boolean updateBookingStatus(String bookingId, String newStatus) {
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                return true;
            }
        }
        return false;
    }

}