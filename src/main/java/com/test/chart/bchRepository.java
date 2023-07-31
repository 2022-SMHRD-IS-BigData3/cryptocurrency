package com.test.chart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface bchRepository extends JpaRepository<bchusdtdata, Long> {
    // Custom method to fetch all candle data
    List<bchusdtdata> findAll(); // Make sure tblminute5 is the correct entity class name

}
