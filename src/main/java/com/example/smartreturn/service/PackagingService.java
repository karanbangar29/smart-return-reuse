package com.example.smartreturn.service;

import com.example.smartreturn.model.Packaging;
import com.example.smartreturn.repository.PackagingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PackagingService {
    private final PackagingRepository repo;

    public PackagingService(PackagingRepository repo){this.repo=repo;}

    public Packaging createPackaging(Packaging pac){
        Packaging p = new Packaging();
        p.setQrCode("QR-"+ UUID.randomUUID().toString());
        p.setStatus(pac.getStatus());
        repo.save(p);
        return p;
    }

    public List<Packaging> listAll(){ return repo.findAll(); }

    public Packaging findByQr(String qr){ return repo.findByQrCode(qr).orElse(null); }

    public Packaging markDelivered(String qr, String customerId){
        Packaging p = findByQr(qr);
        if(p==null) return null;
        p.setStatus("DELIVERED");
        p.setCurrentHolder(customerId);
        repo.save(p);
        return p;
    }

    public Packaging markReturnedToDrop(String qr, String dropPointCode){
        Packaging p = findByQr(qr);
        if(p==null) return null;
        p.setStatus("RETURNED");
        p.setCurrentHolder("DROP:"+dropPointCode);
        repo.save(p);
        return p;
    }
}

