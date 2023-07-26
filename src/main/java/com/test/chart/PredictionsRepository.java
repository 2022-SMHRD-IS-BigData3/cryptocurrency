package com.test.chart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionsRepository extends JpaRepository<PredictionEntity, Long> {
    // Custom method to fetch all predictions data for PredictionEntity
    List<PredictionEntity> findAll();
}
