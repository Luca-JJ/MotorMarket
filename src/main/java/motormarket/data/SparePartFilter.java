package motormarket.data;

/**
 * This class rappresent the settings for the search of the listings
 */
public class SparePartFilter {
    private Integer producerId;
    private Double minPrice;
    private Double maxPrice;
    private int page;

    // Default Constructor (Initializes search to the first page, everything else remains null)
    public SparePartFilter() {
        this.page = 1; //aggiustare in futuro, idea all'utente viene mostrato solo il nome, nel dietro si memeorizza anche l'id che va gestito qui
    }

    
    /**
     * The complete Constructor
     * @param producerId
     * @param minPrice
     * @param maxPrice
     * @param page
     */
    public SparePartFilter(Integer producerId, Double minPrice, Double maxPrice, int page) {
        this.producerId = producerId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.page = page;
    }



    // Utility method to automatically calculate the OFFSET for the SQL query
    public int getOffset() {
        return (this.page - 1) * 20; //magic number
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
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

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    
}
