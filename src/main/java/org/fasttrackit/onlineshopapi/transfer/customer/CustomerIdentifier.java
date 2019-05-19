package org.fasttrackit.onlineshopapi.transfer.customer;

import java.util.Objects;

public class CustomerIdentifier {

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
        CustomerIdentifier that = (CustomerIdentifier) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CustomerIdentifier{" +
                "id=" + id +
                '}';
    }
}
