package org.fasttrackit.onlineshopapi.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Cart {

    @Id
    private long id;

    // LAZY aduce informatiile doar cand avem neovie de ele, EAGER le aduce pe toate
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId     // setezi idul cart'ului identic cu al customerului
    private Customer customer;

    // declaram tipul relatiei @ManyToMany
    // cascade(ce fel de actiuni se se transmita la entitatea product
    @ManyToMany (cascade = CascadeType.MERGE)
    // stabilim detalii despre tabela din mijloc ce se va crea(numele tabelei celor 2 coloane
    @JoinTable(name = "cart_product",       // joinul intre 2 tabele
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>(); // Set sunt colectii de elemente unice

    public void addProduct(Product product) {
        // adaugam produsul in set'ul de mai sus (adaugam produsul in lista produselor din cos
        products.add(product);
        // (adaugam relatia cu cosul de cumparaturi in entitatea product
        product.getCarts().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getCarts().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id &&
                Objects.equals(customer, cart.customer) &&
                Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, products);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }


}
