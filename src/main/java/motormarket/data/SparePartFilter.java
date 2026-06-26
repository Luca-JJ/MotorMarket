package motormarket.data;

/**
 * This class rappresent the settings for the search of the listings
 */
public class SparePartFilter {
    private Integer modelId;
    private Double minPrice;
    private Double maxPrice;
    private int page;

    // Default Constructor (Initializes search to the first page, everything else remains null)
    public SparePartFilter() {
        this.page = 1; //aggiustare in futuro, idea all'utente viene mostrato solo il nome, nel dietro si memeorizza anche l'id che va gestito qui
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
