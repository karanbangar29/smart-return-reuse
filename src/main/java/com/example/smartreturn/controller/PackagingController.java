package com.example.smartreturn.controller;

import com.example.smartreturn.model.Packaging;
import com.example.smartreturn.service.PackagingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/packaging")
public class PackagingController {
    private final PackagingService service;
    public PackagingController(PackagingService service){this.service=service;}

    @PostMapping("/create")
    public ResponseEntity<Packaging> create(@RequestBody  Packaging pac ){
        return ResponseEntity.ok( service.createPackaging(pac));
    }


    @GetMapping
    public ResponseEntity<List<Packaging>> list(){ return ResponseEntity.ok(service.listAll()); }

    @PostMapping("/deliver/{qr}")
    public ResponseEntity<?> deliver(@PathVariable String qr, @RequestParam String customerId){
        Packaging p = service.markDelivered(qr, customerId);
        if(p==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    @PostMapping("/return-to-drop/{qr}")
    public ResponseEntity<?> returnToDrop(@PathVariable String qr, @RequestParam String dropCode){
        Packaging p = service.markReturnedToDrop(qr, dropCode);
        if(p==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }
}

