package com.example.smartreturn.service;

import com.example.smartreturn.model.DropPoint;
import com.example.smartreturn.repository.DropPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DropPointService {
    private final DropPointRepository repo;

    public DropPointService(DropPointRepository repo){this.repo=repo;}

    public DropPoint register(DropPoint dp){ return repo.save(dp); }

    public List<DropPoint> list(){ return repo.findAll(); }
}

