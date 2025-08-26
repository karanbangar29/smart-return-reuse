package com.example.smartreturn.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ReturnRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private String customerId;
    private String status; // REQUESTED, PICKED_UP, RECEIVED_AT_WAREHOUSE, REFURBISHED, RESOLD, DISCARDED
    private LocalDateTime requestedAt = LocalDateTime.now();
    private String packagingQr;

    // getters & setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getOrderId(){return orderId;}
    public void setOrderId(String orderId){this.orderId=orderId;}
    public String getCustomerId(){return customerId;}
    public void setCustomerId(String customerId){this.customerId=customerId;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
    public LocalDateTime getRequestedAt(){return requestedAt;}
    public void setRequestedAt(LocalDateTime requestedAt){this.requestedAt=requestedAt;}
    public String getPackagingQr(){return packagingQr;}
    public void setPackagingQr(String packagingQr){this.packagingQr=packagingQr;}
}

