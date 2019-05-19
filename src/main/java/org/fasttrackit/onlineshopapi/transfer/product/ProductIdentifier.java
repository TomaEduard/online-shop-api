package org.fasttrackit.onlineshopapi.transfer.product;

import java.util.Objects;

public class ProductIdentifier {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductIdentifier that = (ProductIdentifier) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductIdentifier{" +
                "id=" + id +
                '}';
    }
}
