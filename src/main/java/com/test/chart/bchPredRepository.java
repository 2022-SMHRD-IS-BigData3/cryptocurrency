package com.test.chart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface bchPredRepository extends JpaRepository<bchpredictions, Long> {
    // Custom method to fetch all predictions data for PredictionEntity
    List<bchpredictions> findAll();
}
