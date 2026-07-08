package motormarket.data;

import java.time.LocalDate;

/**
 * 
 * Rappresent a single review 
 */
public class ReviewDetail {

    private final String title;
    private final String text;
    private final int rating;
    private final LocalDate date;
    private final String firstName;
    private final String lastName;
    private final double averageRating;

    /**
     * A complete constructor
     * @param title
     * @param text
     * @param rating
     * @param date
     * @param firstName
     * @param lastName
     * @param averageRating
     */
    public ReviewDetail(String title, String text, int rating, LocalDate date, String firstName, String lastName, double averageRating) {
        this.title = title;
        this.text = text;
        this.rating = rating;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageRating = averageRating;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public String toString() {
        return String.format(
            "[%d/5 Stars] \"%s\" by %s %s on %s\n" +
            "   Content: %s\n" +
            "   [Global Product Average: %.2f/5]", 
            rating, title, firstName, lastName, date, text, averageRating
        );
    }

}
