package motormarket.data;

/**
 * Represents an individual Car instance registered in the marketplace.
 * This class maps directly to the automobili table and holds references to structural,
 * technical, and stylistic attributes.
 */
public class Car {

    /**
     * Defines the transmission types available for the vehicle.
     */
    public enum TransmissionType {
        /** Represents a manual transmission system (stick shift). */
        MANUALE("Manuale"),
        
        /** Represents an automatic transmission system. */
        AUTOMATICO("Automatico");

        private final String dbValue;

        TransmissionType(String dbValue) {
            this.dbValue = dbValue;
        }

        /**
         * Gets the raw string representation stored in the database.
         * @return The database string representation of the transmission type.
         */
        public String getDbValue() {
            return dbValue;
        }

        /**
         * Converts a raw input string into its corresponding TransmissionType enum constant.
         * <p>
         * Matches against both the custom database value and the programmatic identifier name 
         * in a case-insensitive manner to provide safe, flexible parsing.
         * </p>
         * @param text The string transmission type to look up.
         * @return The matching {@link TransmissionType} enum constant.
         * @throws IllegalArgumentException If the provided text does not map to any supported transmission.
         */
        public static TransmissionType fromString(String text) {
            for (TransmissionType type : TransmissionType.values()) {
                if (type.getDbValue().equalsIgnoreCase(text) || type.name().equalsIgnoreCase(text)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid transmission type: " + text);
        }
    }

    /**
     * Defines the current availability state of the vehicle instance.
     */
    public enum Availability {
        /** The vehicle is currently available for sale or interactions. */
        DISPONIBILE("Disponibile"),
        
        /** The vehicle has been reserved, removed, or is otherwise unavailable. */
        NON_DISPONIBILE("NonDisponibile");

        private final String dbValue;

        Availability(String dbValue) {
            this.dbValue = dbValue;
        }

        /**
         * Gets the raw string representation stored in the database.
         * * @return The database string representation of the availability status.
         */
        public String getDbValue() {
            return dbValue;
        }

        /**
         * Converts a raw input string into its corresponding Availability enum constant.
         * <p>
         * Matches against both the custom database value and the programmatic identifier name 
         * in a case-insensitive manner to provide safe, flexible parsing.
         * </p>
         * * @param text The string status identifier to look up.
         * @return The matching {@link Availability} enum constant.
         * @throws IllegalArgumentException If the provided text does not map to a valid state.
         */
        public static Availability fromString(String text) {
            // Iterate through all defined availability states
            for (Availability availability : Availability.values()) {
                // Check for a case-insensitive match against either the DB literal value or the Enum constant name
                if (availability.getDbValue().equalsIgnoreCase(text) || availability.name().equalsIgnoreCase(text)) {
                    return availability;
                }
            }
            // Fallback exception when an unsupported status token is processed
            throw new IllegalArgumentException("Invalid availability: " + text);
        }
    }

    private int carId;
    private String vin;
    private String licensePlate;
    private int firstRegistrationYear;
    private int horsepower;
    private TransmissionType transmissionType;
    private Availability availability;
    private CarModel carModel;
    private FuelType fuelType;
    private Color interiorColor;
    private Color exteriorColor;
    private User user;
    
    /**
     * Complete Constructor containing all fields.
     * @param carId                 The unique auto-incremented database ID (IdAuto).
     * @param vin                   The unique 17-character Vehicle Identification Number (VIN).
     * @param licensePlate          The 7-character vehicle registration plate (Targa).
     * @param firstRegistrationYear The year the car was first registered.
     * @param horsepower            The engine power output value (Potenza).
     * @param transmissionType      The transmission technology system (MANUALE or AUTOMATICO).
     * @param availability          The market presence status (DISPONIBILE or NON_DISPONIBILE).
     * @param carModel              The {@link CarModel} instance mapping the vehicle's model taxonomy.
     * @param fuelType              The {@link FuelType} instance mapping the engine fuel setup.
     * @param interiorColor         The {@link Color} instance representing internal cabin upholstery styling.
     * @param exteriorColor         The {@link Color} instance representing external body paint styling.
     * @param user                  The {@link User} instance representing the owner account who listed the vehicle.
     */
    public Car(int carId, String vin, String licensePlate, int firstRegistrationYear, int horsepower, 
               TransmissionType transmissionType, Availability availability, CarModel carModel, 
               FuelType fuelType, Color interiorColor, Color exteriorColor, User user) {
        this.carId = carId;
        this.vin = vin;
        this.licensePlate = licensePlate;
        this.firstRegistrationYear = firstRegistrationYear;
        this.horsepower = horsepower;
        this.transmissionType = transmissionType;
        this.availability = availability;
        this.carModel = carModel;
        this.fuelType = fuelType;
        this.interiorColor = interiorColor;
        this.exteriorColor = exteriorColor;
        this.user = user;
    }

    
    public int getCarId() {
        return carId;
    }

    public String getVin() {
        return vin;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getFirstRegistrationYear() {
        return firstRegistrationYear;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public Availability getAvailability() {
        return availability;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public Color getInteriorColor() {
        return interiorColor;
    }

    public Color getExteriorColor() {
        return exteriorColor;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", vin='" + vin + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", firstRegistrationYear=" + firstRegistrationYear +
                ", horsepower=" + horsepower +
                ", transmissionType=" + transmissionType +
                ", availability=" + availability +
                ", carModel=" + (carModel != null ? carModel.getModelId() : "null") +
                ", fuelType=" + (fuelType != null ? fuelType.getFuelTypeId() : "null") +
                ", user=" + (user != null ? user.getUserId() : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Car} entities.
     */
    public static final class DAO {
    
    }
}
