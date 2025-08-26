package com.example.smartreturn.repository;

import com.example.smartreturn.model.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PackagingRepository extends JpaRepository<Packaging, Long> {
    Optional<Packaging> findByQrCode(String qrCode);
}

