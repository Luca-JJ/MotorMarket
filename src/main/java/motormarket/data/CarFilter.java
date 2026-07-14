package motormarket.data;

/**
 * This class rappresent the settings for the search of the listings
 */
public class CarFilter {
    private Integer modelId;
    private Integer fuelTypeId;
    private String transmissionType; // Maps to your ENUM ('Manuale','Automatico')
    private Integer exteriorColorId;
    private Integer interiorColorId;
    private Double minPrice;
    private Double maxPrice;
    private Integer minYear;
    private Integer maxYear;
    private Integer minPower;
    private Integer maxPower;
    private int page;

   // Default Constructor (Initializes search to the first page, everything else remains null)
    public CarFilter() {
        this.page = 1; 
    }

    // Full Constructor (Initializes all fields including page)
    public CarFilter(Integer modelId, Integer fuelTypeId, String transmissionType, 
                     Integer exteriorColorId, Integer interiorColorId, 
                     Double minPrice, Double maxPrice, 
                     Integer minYear, Integer maxYear, 
                     Integer minPower, Integer maxPower, int page) {
        this.modelId = modelId;
        this.fuelTypeId = fuelTypeId;
        this.transmissionType = transmissionType;
        this.exteriorColorId = exteriorColorId;
        this.interiorColorId = interiorColorId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.minPower = minPower;
        this.maxPower = maxPower;
        this.page = page;
    }
    
    // Utility method to automatically calculate the OFFSET for the SQL query
    public int getOffset() {
        return (this.page - 1) * 20; //magic number
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Integer fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public Integer getExteriorColorId() {
        return exteriorColorId;
    }

    public void setExteriorColorId(Integer exteriorColorId) {
        this.exteriorColorId = exteriorColorId;
    }

    public Integer getInteriorColorId() {
        return interiorColorId;
    }

    public void setInteriorColorId(Integer interiorColorId) {
        this.interiorColorId = interiorColorId;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinYear() {
        return minYear;
    }

    public void setMinYear(Integer minYear) {
        this.minYear = minYear;
    }

    public Integer getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(Integer maxYear) {
        this.maxYear = maxYear;
    }

    public Integer getMinPower() {
        return minPower;
    }

    public void setMinPower(Integer minPower) {
        this.minPower = minPower;
    }

    public Integer getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(Integer maxPower) {
        this.maxPower = maxPower;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    // toString method useful for logging and debugging backend server requests
    @Override
    public String toString() {
        return "CarFilter{" +
                "modelId=" + modelId +
                ", fuelTypeId=" + fuelTypeId +
                ", transmissionType='" + transmissionType + '\'' +
                ", exteriorColorId=" + exteriorColorId +
                ", interiorColorId=" + interiorColorId +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", minYear=" + minYear +
                ", maxYear=" + maxYear +
                ", minPower=" + minPower +
                ", maxPower=" + maxPower +
                ", page=" + page +
                '}';
    }
}
