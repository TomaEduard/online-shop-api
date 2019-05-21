package org.fasttrackit.onlineshopapi.domain;

import javax.persistence.*;

@Entity
public class Review {

    //by default fara (strategy = GenerationType.IDENTITY) se va crea o tabela sepaerat in db in care se
    // tine evidenta cum s-au generat id'urile. Pentru a scapa de acea tabela in pluc trebuie acest strategy=..
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
