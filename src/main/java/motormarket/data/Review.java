package motormarket.data;

import java.time.LocalDate;

/**
 * Represents a Review posted by a user within the marketplace.
 * This class maps directly to the recensioni table and handles mutually 
 * exclusive feedback targeting either a specific spare part or a vehicle model.
 * @author YourName
 * @version 1.0
 */
public class Review {

    /**
     * Defines whether the review targets a vehicle model or a spare part.
     */
    public enum ReviewType {
        /** The review evaluates a specific vehicle model. */
        AUTO("Auto"),
        /** The review evaluates a spare part. */
        PEZZO("Pezzo");

        /** The literal string representation stored within the database column. */
        private final String dbValue;

        ReviewType(String dbValue) {
            this.dbValue = dbValue;
        }

        /**
         * Gets the raw string representation stored in the database.
         * * @return The database string representation of the review type.
         */
        public String getDbValue() {
            return dbValue;
        }

        /**
         * Converts a raw input string into its corresponding ReviewType enum constant.
         * * @param text The string name of the review type to convert.
         * @return The matching {@link ReviewType} enum constant.
         * @throws IllegalArgumentException If the provided text does not match any review type.
         */
        public static ReviewType fromString(String text) {
            for (ReviewType type : ReviewType.values()) {
                if (type.getDbValue().equalsIgnoreCase(text) || type.name().equalsIgnoreCase(text)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid review type: " + text);
        }
    }

    private int reviewId;
    private int rating;
    private String title;
    private LocalDate reviewDate;
    private String text;
    private ReviewType reviewType; 
    private int userId;
    private Integer partId;
    private Integer modelId;

    /**
     * Complete constructor containing all database mapped fields.
     * @param reviewId   The unique identifier for the review (IdRecensione).
     * @param rating     The numerical score/vote assigned by the user.
     * @param title      The brief title or summary of the review.
     * @param reviewDate The date when the review was written.
     * @param text       The full body text of the review.
     * @param reviewType The strongly-typed category constant (AUTO or PEZZO).
     * @param userId     The foreign key reference to the user who wrote the review (IdUtente).
     * @param partId     The foreign key reference to the spare part, or {@code null}.
     * @param modelId    The foreign key reference to the vehicle model, or {@code null}.
     */
    public Review(int reviewId, int rating, String title, LocalDate reviewDate, String text, ReviewType reviewType, int userId, Integer partId, Integer modelId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.title = title;
        this.reviewDate = reviewDate;
        this.text = text;
        this.reviewType = reviewType;
        this.userId = userId;
        this.partId = partId;
        this.modelId = modelId;
    }

    

    public int getReviewId() {
        return reviewId;
    }

    public int getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public String getText() {
        return text;
    }

    public ReviewType getReviewType() {
        return reviewType;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getPartId() {
        return partId;
    }

    public Integer getModelId() {
        return modelId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", reviewDate=" + reviewDate +
                ", reviewType=" + reviewType +
                ", userId=" + userId +
                ", partId=" + partId +
                ", modelId=" + modelId +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Review} entities.
     */
    public static final class DAO {

    }

}
