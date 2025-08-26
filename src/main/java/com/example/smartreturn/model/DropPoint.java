package com.example.smartreturn.model;

import jakarta.persistence.*;

@Entity
public class DropPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String contactNumber;
    private String code; // short code for droppoint

    // getters & setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getAddress(){return address;}
    public void setAddress(String address){this.address=address;}
    public String getContactNumber(){return contactNumber;}
    public void setContactNumber(String contactNumber){this.contactNumber=contactNumber;}
    public String getCode(){return code;}
    public void setCode(String code){this.code=code;}
}

