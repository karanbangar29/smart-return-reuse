package com.example.smartreturn.service;

import com.example.smartreturn.model.ReturnRequest;
import com.example.smartreturn.repository.ReturnRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class ReturnService {
    private final ReturnRequestRepository repo;

    public ReturnService(ReturnRequestRepository repo){this.repo=repo;}

    public ReturnRequest createReturn(String orderId, String customerId, String packagingQr){
        ReturnRequest r = new ReturnRequest();
        r.setOrderId(orderId);
        r.setCustomerId(customerId);
        r.setStatus("REQUESTED");
        r.setPackagingQr(packagingQr);
        repo.save(r);
        return r;
    }
}

