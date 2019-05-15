package org.fasttrackit.onlineshopapi.transfer.product;

public class GetProductsRequest {

    private String partialName;
    private Double minimumPrice;
    private Double maximumPrice;
    private Integer minimumQuantity;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(Double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public Double getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(Double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    @Override
    public String toString() {
        return "GetProductsRequest{" +
                "partialName='" + partialName + '\'' +
                ", mininumPrice=" + minimumPrice +
                ", maximumPrice=" + maximumPrice +
                ", minimumQuantity=" + minimumQuantity +
                '}';
    }
}
