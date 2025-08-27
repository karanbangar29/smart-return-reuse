package com.example.smartreturn;

import com.example.smartreturn.model.Packaging;
import com.example.smartreturn.repository.PackagingRepository;
import com.example.smartreturn.service.PackagingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//public class PackagingServiceTest {
//    @Autowired
//    PackagingRepository repo;
//
//    @Test
//    void contextLoads() {
//        PackagingService svc = new PackagingService(repo);
//        Packaging p = svc.createPackaging(Packaging );
//        assertThat(p.getQrCode()).isNotNull();
//    }
//}

