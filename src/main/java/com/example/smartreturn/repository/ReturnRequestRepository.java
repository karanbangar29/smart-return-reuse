package com.example.smartreturn.repository;

import com.example.smartreturn.model.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
}

