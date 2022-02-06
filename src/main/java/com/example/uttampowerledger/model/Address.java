package com.example.uttampowerledger.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "postcode")
    private int postcode;
    @Column(name = "suburb")
    private String suburb;

    public Address() {

    }

    public Address(int postcode, String suburb) {
        this.postcode = postcode;
        this.suburb = suburb;
    }


    public long getId() { return id; }

    public int getPostcode() { return postcode; }

    public String getSuburb() { return suburb; }

    public void setpostcode(int postcode) { this.postcode = postcode; }

    public void setSuburb(String suburb) { this.suburb = suburb; }
}
