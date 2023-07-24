package com.test.chart;

import java.sql.Timestamp; // Correct import for Timestamp

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblminute5")
@Entity
public class tblminute5 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    private Integer m5_start;
    private Integer m5_max;
    private Integer m5_min;
    private Integer m5_end;
    private Integer volume;

}
