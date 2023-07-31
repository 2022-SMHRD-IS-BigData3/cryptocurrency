package com.test.chart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ethRepository extends JpaRepository<ethusdtdata, Long> {
    // Custom method to fetch all candle data
    List<ethusdtdata> findAll(); // Make sure tblminute5 is the correct entity class name

}
