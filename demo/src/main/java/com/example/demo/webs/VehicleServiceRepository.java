package com.example.demo.webs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleServiceRepository extends JpaRepository<VehicleService, Long> {
    List<VehicleService> findByUsername(String username);
    
}

