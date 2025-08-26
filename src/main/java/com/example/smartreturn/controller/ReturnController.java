package com.example.smartreturn.controller;

import com.example.smartreturn.model.ReturnRequest;
import com.example.smartreturn.service.ReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {
    private final ReturnService service;
    public ReturnController(ReturnService service){this.service=service;}

    @PostMapping("/request")
    public ResponseEntity<ReturnRequest> requestReturn(@RequestParam String orderId,
                                                       @RequestParam String customerId,
                                                       @RequestParam String packagingQr){
        ReturnRequest r = service.createReturn(orderId, customerId, packagingQr);
        return ResponseEntity.ok(r);
    }
}

