package com.example.smartreturn.controller;

import com.example.smartreturn.model.DropPoint;
import com.example.smartreturn.service.DropPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/droppoints")
public class DropPointController {
    private final DropPointService service;
    public DropPointController(DropPointService service){this.service=service;}

    @PostMapping("/register")
    public ResponseEntity<DropPoint> register(@RequestBody DropPoint dp){ return ResponseEntity.ok(service.register(dp)); }

    @GetMapping
    public ResponseEntity<List<DropPoint>> list(){ return ResponseEntity.ok(service.list()); }
}

